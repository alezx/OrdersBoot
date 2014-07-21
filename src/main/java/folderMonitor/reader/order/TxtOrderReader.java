package folderMonitor.reader.order;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import folderMonitor.dao.ArticleRepository;
import folderMonitor.domain.Article;
import folderMonitor.domain.Order;
import folderMonitor.domain.OrderEntry;
import folderMonitor.reader.Reader;

@Component(value = "txtReader")
public class TxtOrderReader implements Reader {

	static Logger logger = LoggerFactory.getLogger(TxtOrderReader.class);

	private final static String SPLIT = "\\|";

	@Value("${txt.article.position}")
	private String articlePosition;

	@Value("${txt.quantity.position}")
	private String quantityPosition;

	@Autowired
	protected ArticleRepository articleRepository;

	@Override
	public Order read(File f) throws Exception {
		try {

			FileInputStream fileInputStream = new FileInputStream(f);
			List<String> lines = IOUtils.readLines(fileInputStream, "UTF-8");
			IOUtils.closeQuietly(fileInputStream);

			String orderCode = f.getName();
			String customer = lines.get(0); // customer is in the first line
			lines.remove(0);
			Order o = createOrder(orderCode, customer);
			// orderService.cleanOrderEntries(o);
			for (String line : lines) {
				if (line.isEmpty()) {
					continue;
				}
				OrderEntry oe = createOrderEntry(o, line, orderCode);
				if (oe != null) {
					o.addOrderEntry(oe);
				}
			}
			return o;
		} catch (Exception e) {
			logger.error("Could not process order " + f.getName(), e);
			throw e;
		}
	}

	public Order createOrder(String code, String customer) {
		Order o = new Order();
		o.setCode(code);
		o.setCustomer(customer);
		// o.setFirstInsert(new Date());
		return o;
	}

	OrderEntry createOrderEntry(Order o, String line, String orderCode)
			throws Exception {
		String[] split = line.split(SPLIT);
		String articleCode = split[Integer.valueOf(articlePosition)];
		Article article = articleRepository.findByCode((articleCode));
		if (article == null) {
			throw new Exception("Could not process order " + o.getCode()
					+ " because article " + articleCode + " does not exist");
		}
		long quantity = Long.valueOf(split[Integer.valueOf(quantityPosition)]);
		return new OrderEntry(article, quantity, o);
	}

}
