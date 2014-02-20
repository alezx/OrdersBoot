package folderMonitor.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;

import folderMonitor.dao.ArticleRepository;
import folderMonitor.dao.Dao;
import folderMonitor.domain.Article;
import folderMonitor.domain.Order;
import folderMonitor.domain.OrderEntry;

@Component(value = "orderService")
public class OrderService {

	static Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	@Qualifier("folderMonitorDao")
	private Dao dao;

	@Autowired
	protected ArticleRepository articleRepository;

	public OrderEntry getOrderEntry(Order o, String articleCode, long quantity) {
		Article a = getArticle(articleCode);
		return new OrderEntry(a, quantity, o);
	}

	public Order getOrder(String code, String customer) {
		Order o;
		try {
			o = dao.findByNamedQuery(Order.class, "Order.findByCodeAndCustomer", ImmutableMap.<String, Object> of("code", code, "customer", customer));
		} catch (javax.persistence.NoResultException nre) {
			o = new Order();
			o.setCode(code);
			o.setCustomer(customer);
			o.setSystemCode(code);
			o.setFirstInsert(new Date());
		}
		return o;
	}

	public Article getArticle(String code) {
		return dao.findByNamedQuery(Article.class, "Article.findByCode", ImmutableMap.<String, Object> of("code", code));
	}

	/**
	 * - decrement the requested quanities - decrement the total - deletes the orderentries
	 * 
	 */

	@Transactional
	public void cleanOrderEntries(Order o) {
		logger.info("updating order {}", o);

		if (o.getOrderEntryes() != null) {
			for (OrderEntry oe : o.getOrderEntryes()) {
				Article a = oe.getArticle();
				logger.info("decrementing requested quantity - {} for article {}", new Object[] { oe.getQuantity(), a });
				long rq = a.getRequestedQuantity();
				a.setRequestedQuantity(rq - oe.getQuantity());
				articleRepository.save(a);
				logger.info("article update {}", a);
				// o.setTotal(o.getTotal()-a.getPrice()*oe.getQuantity()); should be the same as o.setTotal(0)
				dao.remove(oe, oe.getId());
			}
			o.setTotal(0);
			o.getOrderEntryes().clear();
		}
	}

	public void updateTotal(Order o) {
		o.setTotal(0);
		if (o.getOrderEntryes() != null) {
			for (OrderEntry oe : o.getOrderEntryes()) {
				Article a = oe.getArticle();
				o.setTotal(o.getTotal() + a.getPrice() * oe.getQuantity());
			}
		}
	}

}
