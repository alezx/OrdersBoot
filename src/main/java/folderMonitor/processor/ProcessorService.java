package folderMonitor.processor;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import folderMonitor.dao.Dao;
import folderMonitor.domain.Article;
import folderMonitor.domain.Order;
import folderMonitor.domain.OrderEntry;

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
	@Qualifier("folderMonitorDao")
	private Dao dao;

	public void process(Order order) {
		incrementRequested(order);
		dao.merge(order);
	}

	@Transactional
	private void incrementRequested(Order order) {
		
		List<OrderEntry> orderEntryes = order.getOrderEntryes();
		if(orderEntryes!=null){
			for(OrderEntry oe: orderEntryes){
				Article a = oe.getArticle();
				logger.info("incrementing requested quantity + {} for article {}", new Object[]{oe.getQuantity(), a});
				long rq = a.getRequestedQuantity();
				a.setRequestedQuantity(rq + oe.getQuantity());
				order.setTotal(order.getTotal()+a.getPrice()*oe.getQuantity());
				dao.merge(a);
				logger.info("quantity decremented for {}", a);
			}
		}
		order.setLastUpdate(new Date());
		
	}

	public void renameFile(File file) {
		String prepend = SDF.get().format(new Date());
		String newName = "_"+prepend+"_"+file.getName();
		File newParentDir = new File(file.getParent()+File.separator+"_processed");
		if(!newParentDir.exists()){
			newParentDir.mkdirs();
		}
		File newFile = new File(newParentDir.getAbsolutePath()+File.separator+newName);
		boolean result = file.renameTo(newFile);
		if(!result){
			logger.error("unable to rename file {}", newName);
		}
	}
	
}
