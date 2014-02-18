package folderMonitor.reader;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import folderMonitor.reader.article.PriceListReader;
import folderMonitor.reader.article.StockListReader;
import folderMonitor.reader.order.TxtOrderReader;

@Component(value = "readerFactory")
public class ReaderFactory {
	
	@Autowired
	@Qualifier("txtReader")
	private TxtOrderReader txtReader;
	
	@Autowired
	@Qualifier("stockListReader")
	private StockListReader stockListReader;
	
	@Autowired
	@Qualifier("priceListReader")
	private PriceListReader priceListReader;
	
	public Reader getReader(String filename) throws Exception {
		String fileType = FilenameUtils.getExtension(filename);
		if (fileType.equals("txt")){
			return txtReader;
		}
		else if (filename.toUpperCase().contains("STOCK LIST") && fileType.equalsIgnoreCase("xlsx")){
			return stockListReader;
		}
		else if (filename.toUpperCase().contains("PRICE LIST") && fileType.equalsIgnoreCase("xlsx")){
			return priceListReader;
		}
		throw new Exception("Format not Supported: "+fileType);
	}
	
}
