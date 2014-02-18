package folderMonitor.services;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;

import folderMonitor.dao.Dao;
import folderMonitor.domain.Article;
import folderMonitor.domain.Order;
import folderMonitor.domain.OrderEntry;

@Component(value = "importArticleService")
public class ImportArticlesService {
	
	@Autowired
	@Qualifier("folderMonitorDao")
	private Dao dao;
	
	@Autowired
	@Qualifier("orderService")
	protected OrderService orderService;

	@SuppressWarnings("unchecked")
	@Transactional
	public void saveOnDB(Collection<Article> collection) {
		
		for(Article article : collection){
			List<Article> articles = dao.findListByNamedQuery(Article.class, "Article.findByCode", null, ImmutableMap.<String,Object>of("code", article.getCode()));
			if(articles.isEmpty()){
				dao.save(article);
			}else{
				Article articleFromDB = articles.get(0);
				setNewQuanties(articleFromDB, article);
				dao.merge(articleFromDB);
				//updateOrderTotals(articleFromDB);
			}
		}
		
	}

	private void setNewQuanties(Article fromDb, Article article) {
		
		// only set when coming from pricelist
		if (article.getPrice() != null) fromDb.setPrice(article.getPrice());
		if (article.getProductionQuantity() != null) fromDb.setProductionQuantity(article.getProductionQuantity());

		
		// only set when coming from stocklist
		if (article.getSeries() != null) fromDb.setSeries(article.getSeries());
		if (article.getTitle() != null) fromDb.setTitle(article.getTitle());
		if (article.getFormat() != null) fromDb.setFormat(article.getFormat());
		if (article.getInterior() != null) fromDb.setInterior(article.getInterior());		
		if (article.getAvailableforSale() != null) fromDb.setAvailableforSale(article.getAvailableforSale());
		if (article.getReservedStock() != null) fromDb.setReservedStock(article.getReservedStock());
		if (article.getAvailableonHand() != null) fromDb.setAvailableonHand(article.getAvailableonHand());
		if (article.getReservationStatus() != null) fromDb.setReservationStatus(article.getReservationStatus());
		if (article.getCaseQuantity() != null) fromDb.setCaseQuantity(article.getCaseQuantity());
		
		// TODO to delete
		if (article.getWarehouseQuantity() != null) fromDb.setWarehouseQuantity(article.getWarehouseQuantity());
		
	}
	
	@SuppressWarnings("unchecked")
	private void updateOrderTotals(Article a) {
		
		List<OrderEntry> oes = dao.findListBySqlQuery(OrderEntry.class, OrderEntry.QUERY_ALL_BY_ARTICLE, null, ImmutableMap.<String,Object>of("article", a.getId()));
		for(OrderEntry oe : oes) {
			Order order = oe.getOrder();
			orderService.updateTotal(order);
			dao.merge(order);
		}
		
	}
	
	
}