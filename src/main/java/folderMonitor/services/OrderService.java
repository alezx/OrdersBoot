package folderMonitor.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ale.HandshakeToOrders.JsonOrder;
import ale.HandshakeToOrders.JsonOrderEntry;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

import folderMonitor.dao.ArticleRepository;
import folderMonitor.dao.Dao;
import folderMonitor.dao.OrderEntryRepository;
import folderMonitor.dao.OrderRepository;
import folderMonitor.domain.Article;
import folderMonitor.domain.Order;
import folderMonitor.domain.OrderEntry;

@Component(value = "orderService")
public class OrderService {

	private static final String DP_12 = "2015 DP 12";

	static Logger logger = LoggerFactory.getLogger(OrderService.class);

	private static SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yy");

	@Autowired
	@Qualifier("folderMonitorDao")
	private Dao dao;

	@Autowired
	protected ArticleRepository articleRepository;

	@Autowired
	protected OrderRepository orderRepository;

	@Autowired
	protected OrderEntryRepository orderEntryRepository;

	// public void process(Order order) {
	// List<Order> ordersFromDb = orderRepository.findByCodeAndCustomer(
	// order.getCode(), order.getCustomer());
	// if (!ordersFromDb.isEmpty()) {
	// Order orderFromDb = ordersFromDb.get(0);
	// deleteOrderEntries(orderFromDb);
	// changeOrderToOrderEntries(order.getOrderEntryes(), orderFromDb);
	// orderFromDb.setOrderEntryes(order.getOrderEntryes());
	// incrementRequested(orderFromDb);
	// orderRepository.save(orderFromDb);
	// } else {
	// incrementRequested(order);
	// orderRepository.save(order);
	// }
	// }

	// private void changeOrderToOrderEntries(List<OrderEntry> orderEntryes,
	// Order orderFromDb) {
	// for (OrderEntry oe : orderEntryes) {
	// oe.setOrder(orderFromDb);
	// }
	// }

	// /**
	// * decrement the requested quantities, decrement the total, deletes the order entries
	// *
	// */
	// @Transactional
	// public void deleteOrderEntries(Order o) {
	// logger.info("updating order {}", o);
	//
	// if (o.getOrderEntryes() != null) {
	// for (OrderEntry oe : o.getOrderEntryes()) {
	// Article a = oe.getArticle();
	// logger.info(
	// "decrementing requested quantity - {} for article {}",
	// new Object[] { oe.getQuantity(), a });
	// Long rq = a.getRequestedQuantity();
	// a.setRequestedQuantity(rq - oe.getQuantity());
	// recalculateBalances(a);
	// articleRepository.save(a);
	// logger.info("article {} requested quantity now is {}",
	// a.getId(), a.getRequestedQuantity());
	// }
	// o.setTotal(0);
	// o.getOrderEntryes().clear();
	// }
	// }
	//
	// private void recalculateBalances(Article a) {
	// a.setBalanceProd(a.getProductionQuantity() - a.getRequestedQuantity());
	// a.setBalanceWarehouse(a.getWarehouseQuantity()
	// - a.getRequestedQuantity());
	// a.setBalanceProdPrice(a.getPrice() * a.getBalanceProd());
	// a.setBalanceWarehousePrice(a.getPrice() * a.getBalanceWarehouse());
	// }
	//
	// @Transactional
	// private void incrementRequested(Order order) {
	//
	// List<OrderEntry> orderEntryes = order.getOrderEntryes();
	// if (orderEntryes != null) {
	// for (OrderEntry oe : orderEntryes) {
	// Article a = reloadArticleFromDb(oe);
	// if (a.getPrice() == null) {
	// throw new RuntimeException("Could not process order "
	// + order.getCode() + " because article " + a.getId()
	// + " doesn't have price");
	// }
	// logger.info(
	// "incrementing requested quantity + {} for article {}",
	// new Object[] { oe.getQuantity(), a });
	// Long rq = a.getRequestedQuantity();
	// a.setRequestedQuantity(rq + oe.getQuantity());
	// recalculateBalances(a);
	// order.setTotal(order.getTotal() + a.getPrice()
	// * oe.getQuantity());
	// articleRepository.save(a);
	// logger.info("article {} requested quantity now is {}",
	// a.getId(), a.getRequestedQuantity());
	// }
	// }
	// // order.setLastUpdate(new Date());
	//
	// }
	//
	// private Article reloadArticleFromDb(OrderEntry oe) {
	// Article a = oe.getArticle();
	// a = articleRepository.findByCode(a.getCode()); // refreshed from db
	// oe.setArticle(a);
	// return a;
	// }

	// public Order getOrder(String code, String customer) {
	// return dao.findByNamedQuery(Order.class, "Order.findByCodeAndCustomer", ImmutableMap.<String, Object> of("code", code, "customer", customer));
	// }

	// public Article getArticle(String code) {
	// return dao.findByNamedQuery(Article.class, "Article.findByCode", ImmutableMap.<String, Object> of("code", code));
	// }

	// public void updateTotal(Order o) {
	// o.setTotal(0);
	// if (o.getOrderEntryes() != null) {
	// for (OrderEntry oe : o.getOrderEntryes()) {
	// Article a = oe.getArticle();
	// o.setTotal(o.getTotal() + a.getPrice() * oe.getQuantity());
	// }
	// }
	// }

	//
	//
	//
	//
	//
	// solo i metodi da qui in poi sono usati
	// quelli prima sono vecchi
	//
	//
	//

	public boolean increasePriority(Order o, int p) {
		if (p > o.getPriority()) {
			throw new RuntimeException("Priority " + p
					+ "is greater than current priority " + o.getPriority());
		}
		String query = "select * from orders o where o.priority < "
				+ o.getPriority() + " and o.priority >= " + p
				+ " order by o.priority asc";
		List<Order> orders = new ArrayList<Order>(dao.findListBySqlQuery(
				Order.class, query, null, null));
		o.setPriority(-1);
		orderRepository.save(o);
		for (Order oo : Lists.reverse(orders)) {
			oo.setPriority(oo.getPriority() + 1);
			orderRepository.save(oo);
		}
		o.setPriority(p);
		orderRepository.save(o);
		return true;
	}

	public boolean decreasePriority(Order o, int p) {
		String query = "select * from orders o where o.priority > "
				+ o.getPriority() + " and o.priority <= " + p
				+ " order by o.priority asc ";
		List<Order> orders = dao.findListBySqlQuery(Order.class, query, null,
				null);
		o.setPriority(-1);
		orderRepository.save(o);
		for (Order oo : orders) {
			oo.setPriority(oo.getPriority() - 1);
			orderRepository.save(oo);
		}
		o.setPriority(p);
		orderRepository.save(o);
		return true;
	}

	public void importOrders(Collection<JsonOrder> collection)
			throws ParseException {

		List<Order> ordersToSave = Lists.newArrayList();

		int priority = getMaxPriority() + 1;

		List<Article> addedArticles = Lists.newArrayList();

		for (JsonOrder jo : collection) {
			Order o = new Order();
			o.setCode(String.valueOf(jo.getId()));
			o.setCustomer(jo.getCustomerName());
			o.setCustomerCode(jo.getCustomerId());
			o.setOrderDate(SDF.parse(jo.getOrderDate()));
			o.setPriority(priority++);

			float total = 0;

			for (JsonOrderEntry joe : jo.getEntries()) {
				OrderEntry oe = new OrderEntry();
				Article a = articleRepository.findByCode(joe.getArticleCode());
				// Preconditions.checkState(a != null, "Order [%s] refers to an article not present in db [%s]", o.getCode(), joe.getArticleCode());
				if (a == null) {
					logger.warn("article [{}] not present. creating now..",
							joe.getArticleCode());
					a = new Article(joe.getArticleCode(), 0l, 0l);
					a.setPrice(0f);
					addedArticles.add(a);
					articleRepository.save(a);
				}
				oe.setArticle(a);
				oe.setOrder(o);
				oe.setQuantity(joe.getQuantity());
				oe.setNewQuantity(joe.getQuantity());
				total += a.getPrice() != null ? joe.getQuantity()
						* a.getPrice() : 0;

				o.addOrderEntry(oe);
			}

			o.setTotal(total);

			ordersToSave.add(o);

		}

		for (Order o : ordersToSave) {
			orderRepository.save(o);
		}

		logger.info("imported [{}] orders", ordersToSave.size());
		logger.info("created [{}] new articles", addedArticles.size());
	}

	private Integer getMaxPriority() {
		try {
			Query q = dao.getEntityManager().createQuery(
					"select max(priority) from Order o");
			Integer p = (Integer) q.getSingleResult();
			return p == null ? 0 : p;
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean calculateBalances() {

		List<Order> orders = dao.findListBySqlQuery(Order.class,
				"select * from orders order by priority asc", null, null);
		Map<String, Article> articlesMap = getArticlesMap();

		LoadingCache<String, List<String>> articlesOrdersList = CacheBuilder
				.newBuilder().build(new CacheLoader<String, List<String>>() {
					public List<String> load(String key) {
						return Lists.newArrayList();
					}
				});

		for (Order o : orders) {
			float totalForOrder = 0;
			float available = 0;
			float availableProd = 0;

			boolean twelveMonths = false;

			for (OrderEntry oe : o.getOrderEntryes()) {

				Article articleFromDb = oe.getArticle();
				Article a = articlesMap.get(articleFromDb.getCode());

				long residualQuantityWarehouse = a.getWarehouseQuantity() != null ? a
						.getWarehouseQuantity() : 0;
				long residualQuantityProduction = a.getProductionQuantity() != null ? a
						.getProductionQuantity() : 0;

				long differenceWarehouse = residualQuantityWarehouse
						- oe.getNewQuantity();
				long differenceProduction = residualQuantityProduction
						- oe.getNewQuantity();

				if (differenceWarehouse >= 0) {
					available += oe.getNewQuantity();
				} else {
					available += residualQuantityWarehouse > 0 ? residualQuantityWarehouse
							: 0;
				}

				if (differenceProduction >= 0) {
					availableProd += oe.getNewQuantity();
				} else {
					availableProd += residualQuantityProduction > 0 ? residualQuantityProduction
							: 0;
				}

				totalForOrder += oe.getNewQuantity();

				a.setWarehouseQuantity(differenceWarehouse);
				a.setProductionQuantity(differenceProduction);

				oe.setResidualWarehouseQuantity(differenceWarehouse);
				oe.setResidualProductionQuantity(differenceProduction);

				// orders so far
				List<String> orderCodes = updateOrdersPresentSoFar(
						articlesOrdersList, o, a);
				oe.setOrdersSoFar(orderCodes.toString());

				// 12 months
				twelveMonths |= a.getSeries() != null
						&& a.getSeries().contains(DP_12);

				// req quantity
				long requestedQuantity = (a.getRequestedQuantity() != null ? a
						.getRequestedQuantity() : 0) + oe.getNewQuantity();

				a.setRequestedQuantity(requestedQuantity);
				articleFromDb.setRequestedQuantity(requestedQuantity);

				orderEntryRepository.save(oe);
				articleRepository.save(articleFromDb);
			}

			float percAvailable = totalForOrder == 0 ? 100
					: (available / totalForOrder) * 100;
			float percAvailableProduction = totalForOrder == 0 ? 100
					: (availableProd / totalForOrder) * 100;

			o.setPercAvailableWare(percAvailable);
			o.setPercAvailableProd(percAvailableProduction);

			o.setTwelveMonths(twelveMonths);

			orderRepository.save(o);
			logger.info("order [{}] saved", o.getCode());

		}

		logger.info("percentages calculated");

		return true;

	}

	private List<String> updateOrdersPresentSoFar(
			LoadingCache<String, List<String>> articlesOrdersList, Order o,
			Article a) {
		List<String> orderCodes = null;
		try {
			orderCodes = articlesOrdersList.get(a.getCode());
			orderCodes.add(o.getCode());
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return orderCodes;
	}

	private Map<String, Article> getArticlesMap() {
		List<Article> articles = dao.findListBySqlQuery(Article.class,
				"select * from articles", null, null);
		Map<String, Article> articlesMap = new HashMap<String, Article>();
		for (Article a : articles) {

			Article aCopy = new Article(a.getCode(), a.getProductionQuantity(),
					a.getWarehouseQuantity());
			aCopy.setSeries(a.getSeries());
			aCopy.setRequestedQuantity(0L); // resetting req quantity

			articlesMap.put(a.getCode(), aCopy);
		}
		return articlesMap;
	}
}
