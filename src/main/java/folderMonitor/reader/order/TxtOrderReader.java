package folderMonitor.reader.order;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.hibernate.cfg.IndexOrUniqueKeySecondPass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import folderMonitor.dao.Dao;
import folderMonitor.domain.Order;
import folderMonitor.domain.OrderEntry;
import folderMonitor.processor.ProcessorThread;
import folderMonitor.reader.Reader;

@Component(value = "txtReader")
public class TxtOrderReader extends OrderReader implements Reader {
	
	
	private final static String SPLIT = "\\|";
	
	@Value("${txt.article.position}")
	private String articlePosition;
	
	@Value("${txt.quantity.position}")
	private String quantityPosition;
	


	OrderEntry getOrderEntry(Order o, String line, String orderCode) {
		try {
			String[] split = line.split(SPLIT);
			String articleCode = split[Integer.valueOf(articlePosition)];
			long quantity = Long.valueOf(split[Integer.valueOf(quantityPosition)]);
			
			return orderService.getOrderEntry(o, articleCode, quantity);
			
		}catch(Exception e){
			//logger.error("in getOrderEntry()", e);
			return null;
		}
	}
	
	@Override
	String getCustomerName(List<String> lines) {
		return lines.get(0);
	}

	public String getArticlePosition() {
		return articlePosition;
	}

	public void setArticlePosition(String articlePosition) {
		this.articlePosition = articlePosition;
	}

	public String getQuantityPosition() {
		return quantityPosition;
	}

	public void setQuantityPosition(String quantityPosition) {
		this.quantityPosition = quantityPosition;
	}


	

}
