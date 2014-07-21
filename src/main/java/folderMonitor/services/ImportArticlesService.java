package folderMonitor.services;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import folderMonitor.dao.ArticleRepository;
import folderMonitor.dao.Dao;
import folderMonitor.dao.OrderRepository;
import folderMonitor.domain.Article;

@Component(value = "importArticleService")
public class ImportArticlesService {

	static Logger logger = LoggerFactory.getLogger(ImportArticlesService.class);

	private final static String PAPERLESS = "PAPERLESS";
	private final static Long MILION = 1000000L;

	@Autowired
	@Qualifier("folderMonitorDao")
	private Dao dao;

	@Autowired
	@Qualifier("orderService")
	protected OrderService orderService;

	@Autowired
	protected ArticleRepository articleRepository;

	@Autowired
	protected OrderRepository orderRepository;

	@SuppressWarnings("unchecked")
	@Transactional
	public void saveOnDB(Collection<Article> collection) {

		int added = 0, updated = 0;

		for (Article article : collection) {
			Article articleFromDB = articleRepository.findByCode(article
					.getCode());
			if (articleFromDB == null) {
				articleRepository.save(article);
				added++;
			} else {
				setNewQuanties(articleFromDB, article);
				articleRepository.save(articleFromDB); // there was a merge here
				// updateOrderTotals(articleFromDB);
				updated++;
			}
		}
		logger.info("Added: {}, updated: {}", added, updated);

	}

	private void setNewQuanties(Article fromDb, Article article) {

		// only set when saving the pricelist
		if (article.getPrice() != null)
			fromDb.setPrice(article.getPrice());
		if (article.getProductionQuantity() != null)
			fromDb.setProductionQuantity(article.getProductionQuantity());

		// only set when saving the stocklist
		if (article.getSeries() != null)
			fromDb.setSeries(article.getSeries());
		if (article.getTitle() != null)
			fromDb.setTitle(article.getTitle());
		if (article.getFormat() != null)
			fromDb.setFormat(article.getFormat());
		if (article.getInterior() != null)
			fromDb.setInterior(article.getInterior());
		// if (article.getAvailableforSale() != null)
		// fromDb.setAvailableforSale(article.getAvailableforSale());
		// if (article.getReservedStock() != null)
		// fromDb.setReservedStock(article.getReservedStock());
		// if (article.getAvailableonHand() != null)
		// fromDb.setAvailableonHand(article.getAvailableonHand());
		// if (article.getReservationStatus() != null)
		// fromDb.setReservationStatus(article.getReservationStatus());
		// if (article.getCaseQuantity() != null)
		// fromDb.setCaseQuantity(article.getCaseQuantity());

		// TODO to delete
		if (article.getWarehouseQuantity() != null)
			fromDb.setWarehouseQuantity(article.getWarehouseQuantity());

	}

	public void addPaperlessArticle(Collection<Article> collection) {
		Article article = new Article(PAPERLESS, MILION, MILION);
		article.setPrice(0F);
		collection.add(article);
	}

	// @SuppressWarnings("unchecked")
	// private void updateOrderTotals(Article a) {
	//
	// List<OrderEntry> oes = dao.findListBySqlQuery(OrderEntry.class, OrderEntry.QUERY_ALL_BY_ARTICLE, null,
	// ImmutableMap.<String, Object> of("article", a.getId()));
	// for (OrderEntry oe : oes) {
	// Order order = oe.getOrder();
	// orderService.updateTotal(order);
	// orderRepository.save(order);
	// }
	//
	// }

}
