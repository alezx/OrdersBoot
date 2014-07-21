package folderMonitor.processor;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ale.HandshakeToOrders.JsonOrder;
import folderMonitor.dao.ArticleRepository;
import folderMonitor.domain.Article;
import folderMonitor.domain.Order;
import folderMonitor.reader.Reader;
import folderMonitor.reader.ReaderFactory;
import folderMonitor.services.ImportArticlesService;
import folderMonitor.services.OrderService;

@Component(value = "processorService")
public class ProcessorService {

	static Logger logger = LoggerFactory.getLogger(ProcessorService.class);

	public static final String FORMAT = "yyyy-MM-dd_HH-mm-ss";
	private static final ThreadLocal<DateFormat> SDF = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(FORMAT);
		}
	};

	@Autowired
	@Qualifier("orderService")
	protected OrderService orderService;

	@Autowired
	protected ArticleRepository articleRepository;

	@Autowired
	@Qualifier("readerFactory")
	private ReaderFactory readerFactory;

	@Autowired
	@Qualifier("importArticleService")
	protected ImportArticlesService importArticleService;

	void processFile(File file) {
		try {
			Reader reader = readerFactory.getReader(file.getName());
			Object object = reader.read(file);
			if (object instanceof Order) {
				throw new RuntimeException("not implemented");
				// Order order = (Order) object;
				// orderService.process(order);
			} else if (Collection.class.isAssignableFrom(object.getClass())) {
				Collection collection = (Collection) object;
				Object first = collection.iterator().next();
				if (first instanceof Article) {
					importArticleService
							.addPaperlessArticle((Collection<Article>) collection);
					importArticleService
							.saveOnDB((Collection<Article>) collection);
				} else if (first instanceof JsonOrder) {
					orderService
							.importOrders((Collection<JsonOrder>) collection);
				}
			}

		} catch (Throwable e) {
			logger.error("ERROR", e);
		} finally {
			renameFile(file);
		}
	}

	public void renameFile(File file) {
		String prepend = SDF.get().format(new Date());
		String newName = "_" + prepend + "_" + file.getName();
		File newParentDir = new File(file.getParent() + File.separator
				+ "_processed");
		if (!newParentDir.exists()) {
			newParentDir.mkdirs();
		}
		File newFile = new File(newParentDir.getAbsolutePath() + File.separator
				+ newName);
		boolean result = file.renameTo(newFile);
		if (!result) {
			logger.error("unable to rename file {}", newName);
		}
	}

}
