package folderMonitor.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import folderMonitor.controllers.parameters.Filters;
import folderMonitor.controllers.parameters.PagingInfo;
import folderMonitor.dao.ArticleRepository;
import folderMonitor.dao.Dao;
import folderMonitor.dao.OrderEntryRepository;
import folderMonitor.dao.OrderRepository;
import folderMonitor.domain.Article;
import folderMonitor.domain.Order;
import folderMonitor.domain.OrderEntry;
import folderMonitor.services.ParameterService;
import folderMonitor.utils.JacksonObjectMapperFactory;

@RestController
@RequestMapping(value = "order")
public class OrderController {

	static Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	@Qualifier("folderMonitorDao")
	private Dao dao;

	@Autowired
	@Qualifier("parameterService")
	private ParameterService parameterService;

	@Autowired
	private JacksonObjectMapperFactory jacksonObjectMapperFactory;

	@Autowired
	protected ArticleRepository articleRepository;

	@Autowired
	protected OrderRepository orderRepository;

	@Autowired
	protected OrderEntryRepository orderEntryRepository;

	@RequestMapping(value = "/orderList")
	@ResponseBody
	public String orderList(PagingInfo info) throws Exception {

		String filter = info.getFilter();
		String sqlQuery = Order.QUERY_ALL;
		Map<String, Object> queryParameters = new HashMap<String, Object>();

		if (filter != null) {
			Filters filters = parameterService.fromJson(filter);
			sqlQuery = parameterService.getSqlQuery(sqlQuery, filters);
			queryParameters = filters.toMap();
		}

		List<Order> orders = dao.findListBySqlQuery(Order.class, sqlQuery, info, queryParameters);
		int total = dao.findListBySqlQuery(Order.class, sqlQuery, null, queryParameters).size();

		List<Map<String, Object>> list = Order.toMapList(orders);

		return jacksonObjectMapperFactory.getObject().writeValueAsString(ImmutableMap.of("success", true, "orders", list, "total", total));
	}

	@RequestMapping(value = "/saveOrder")
	@ResponseBody
	public String saveOrder(@RequestBody Map<String, Object> map) throws Exception {

		System.out.println(map);
		Order o = Order.fromMap(map);

		orderRepository.save(o);

		return jacksonObjectMapperFactory.getObject().writeValueAsString(ImmutableMap.of("success", true));
	}

	@RequestMapping(value = "/orderEntryList")
	@ResponseBody
	public String orderEntryList(PagingInfo info, int orderId) throws Exception {

		ImmutableMap<String, Object> parameters = ImmutableMap.<String, Object> of("orderId", orderId);

		List<OrderEntry> orderEntries = dao.findListBySqlQuery(OrderEntry.class, OrderEntry.QUERY_ALL_BY_ORDER, info, parameters);

		int total = dao.findListBySqlQuery(OrderEntry.class, OrderEntry.QUERY_ALL_BY_ORDER, null, parameters).size();

		List<Map<String, Object>> list = OrderEntry.toMapList(orderEntries);

		return jacksonObjectMapperFactory.getObject().writeValueAsString(ImmutableMap.of("success", true, "orderentries", list, "total", total));
	}

	@RequestMapping(value = "/saveOrderEntry")
	@ResponseBody
	@Transactional
	public String saveOrderEntry(@RequestBody Map<String, Object> map) throws Exception {

		System.out.println(map);

		OrderEntry oe = dao.find(OrderEntry.class, (Integer) map.get("ID"));
		Integer newQuantity = (Integer) map.get("NEW_QUANTITY");

		Order o = oe.getOrder();
		Article a = oe.getArticle();

		// cancelling last order entry
		a.setRequestedQuantity(a.getRequestedQuantity() - oe.getQuantity());
		o.setTotal(o.getTotal() - a.getPrice() * oe.getQuantity());

		// using last quantity
		oe.setQuantity(newQuantity);
		a.setRequestedQuantity(a.getRequestedQuantity() + oe.getQuantity());
		o.setTotal(o.getTotal() + a.getPrice() * oe.getQuantity());

		articleRepository.save(a);
		orderEntryRepository.save(oe);
		orderRepository.save(o);

		return jacksonObjectMapperFactory.getObject().writeValueAsString(ImmutableMap.of("success", true));
	}

}
