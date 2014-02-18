package folderMonitor.reader.order;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import folderMonitor.domain.Order;
import folderMonitor.domain.OrderEntry;
import folderMonitor.reader.Reader;
import folderMonitor.services.OrderService;

public abstract class OrderReader implements Reader {
	
	static Logger logger = LoggerFactory.getLogger(OrderReader.class);
	
	@Autowired
	@Qualifier("orderService")
	protected OrderService orderService;
	
	abstract OrderEntry getOrderEntry(Order o, String line, String orderCode);
	
	abstract String getCustomerName(List<String> lines);
	
	@Override
	public Order read(File f) throws Exception {
		try {
			
			FileInputStream fileInputStream = new FileInputStream(f);
			List<String> lines = IOUtils.readLines(fileInputStream, "UTF-8");
			IOUtils.closeQuietly(fileInputStream);
			
			String orderCode = f.getName();
			String customer = getCustomerName(lines);
			Order o = orderService.getOrder(orderCode, customer);
			orderService.cleanOrderEntries(o);
			for(String line:lines){
				OrderEntry oe = getOrderEntry(o, line, orderCode);
				if(oe!=null){
					o.addOrderEntry(oe);
				}
			}
			return o;
		} catch (Exception e) {
			logger.error("ERROR", e);
			throw e;
		}
	}

}
