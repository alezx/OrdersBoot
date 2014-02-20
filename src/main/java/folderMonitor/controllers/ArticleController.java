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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import folderMonitor.controllers.parameters.Filters;
import folderMonitor.controllers.parameters.PagingInfo;
import folderMonitor.dao.ArticleRepository;
import folderMonitor.dao.Dao;
import folderMonitor.domain.Article;
import folderMonitor.domain.OrderEntry;
import folderMonitor.services.ParameterService;
import folderMonitor.utils.JacksonObjectMapperFactoryStatic;

@RestController
@RequestMapping(value = "article")
public class ArticleController {

	static Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	@Qualifier("folderMonitorDao")
	private Dao dao;

	@Autowired
	@Qualifier("parameterService")
	private ParameterService parameterService;

	@Autowired
	protected ArticleRepository articleRepository;

	@RequestMapping(value = "/articleList")
	@ResponseBody
	public String articleList(PagingInfo info) throws Exception {

		String filter = info.getFilter();
		String sqlQuery = Article.QUERY_ALL;
		Map<String, Object> queryParameters = new HashMap<String, Object>();

		if (filter != null) {
			Filters filters = parameterService.fromJson(filter);
			sqlQuery = parameterService.getSqlQuery(sqlQuery, filters);
			queryParameters = filters.toMap();
		}

		List<Article> articles = dao.findListBySqlQuery(Article.class, sqlQuery, info, queryParameters);

		int total = dao.findListBySqlQuery(Article.class, sqlQuery, null, queryParameters).size();

		List<Map<String, Object>> list = Article.toMapList(articles);

		return JacksonObjectMapperFactoryStatic.getObject().writeValueAsString(ImmutableMap.of("success", true, "articles", list, "total", total));
	}

	@RequestMapping(value = "/saveArticle")
	@ResponseBody
	public String saveArticle(@RequestBody Map<String, Object> map) throws Exception {

		System.out.println(map);
		Article a = Article.fromMap(map);
		a.setAvailableonHand(a.getAvailableforSale() + a.getReservedStock());
		articleRepository.save(a);

		return JacksonObjectMapperFactoryStatic.getObject().writeValueAsString(ImmutableMap.of("success", true));
	}

	@RequestMapping(value = "/orderArticleList")
	@ResponseBody
	public String orderArticleList(PagingInfo info, int article) throws Exception {

		ImmutableMap<String, Object> parameters = ImmutableMap.<String, Object> of("article", article);
		List<OrderEntry> orderEntries = dao.findListBySqlQuery(OrderEntry.class, OrderEntry.QUERY_ALL_BY_ARTICLE, info, parameters);
		int total = dao.findListBySqlQuery(OrderEntry.class, OrderEntry.QUERY_ALL_BY_ARTICLE, null, parameters).size();
		List<Map<String, Object>> list = OrderEntry.toMapList(orderEntries);
		return JacksonObjectMapperFactoryStatic.getObject().writeValueAsString(ImmutableMap.of("success", true, "orderArticles", list, "total", total));
	}

}
