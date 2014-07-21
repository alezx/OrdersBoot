package folderMonitor.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import folderMonitor.controllers.parameters.Filters;
import folderMonitor.controllers.parameters.PagingInfo;
import folderMonitor.dao.ArticleRepository;
import folderMonitor.dao.Dao;
import folderMonitor.domain.Article;
import folderMonitor.domain.OrderEntry;
import folderMonitor.services.ParameterService;
import folderMonitor.utils.JacksonObjectMapperFactory;

@RestController
@RequestMapping(value = "article")
public class ArticleController {

	static Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private JacksonObjectMapperFactory jacksonObjectMapperFactory;

	@Autowired
	@Qualifier("folderMonitorDao")
	private Dao dao;

	@Autowired
	@Qualifier("parameterService")
	private ParameterService parameterService;

	@Autowired
	protected ArticleRepository articleRepository;

	@RequestMapping(value = "/articleList")
	public Map<String, Object> articleList(PagingInfo info) throws Exception {

		String filter = info.getFilter();
		String sqlQuery = Article.QUERY_ALL;
		Map<String, Object> queryParameters = new HashMap<String, Object>();

		if (filter != null) {
			Filters filters = parameterService.fromJson(filter);
			sqlQuery = parameterService.getSqlQuery(sqlQuery, filters);
			queryParameters = filters.toMap();
		}

		List<Article> articles = dao.findListBySqlQuery(Article.class,
				sqlQuery, info, queryParameters);

		int total = dao.findListBySqlQuery(Article.class, sqlQuery, null,
				queryParameters).size();

		// List<Map<String, Object>> list = Article.toMapList(articles);

		// return jacksonObjectMapperFactory.getObject().writeValueAsString(ImmutableMap.of("success", true, "articles", articles, "total", total));

		return ImmutableMap.of("success", true, "articles", articles, "total",
				total);
	}

	@RequestMapping(value = "/saveArticle")
	public String saveArticle(@RequestBody Article article) throws Exception {

		Article fromDb = articleRepository.findByCode(article.getCode());

		fromDb.setProductionQuantity(article.getProductionQuantity());
		fromDb.setWarehouseQuantity(article.getWarehouseQuantity());

		articleRepository.save(fromDb);

		return jacksonObjectMapperFactory.getObject().writeValueAsString(
				ImmutableMap.of("success", true));
	}

	@RequestMapping(value = "/orderArticleList")
	public String orderArticleList(PagingInfo info, int article)
			throws Exception {

		ImmutableMap<String, Object> parameters = ImmutableMap
				.<String, Object> of("article", article);
		List<OrderEntry> orderEntries = dao.findListBySqlQuery(
				OrderEntry.class, OrderEntry.QUERY_ALL_BY_ARTICLE, info,
				parameters);
		int total = dao.findListBySqlQuery(OrderEntry.class,
				OrderEntry.QUERY_ALL_BY_ARTICLE, null, parameters).size();
		// List<Map<String, Object>> list = OrderEntry.toMapList(orderEntries);
		return jacksonObjectMapperFactory.getObject().writeValueAsString(
				ImmutableMap.of("success", true, "orderArticles", orderEntries,
						"total", total));
	}

}
