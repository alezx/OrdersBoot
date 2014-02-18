package folderMonitor.controllers;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import folderMonitor.processor.Processor;
import folderMonitor.utils.JacksonObjectMapperFactory;



@RestController
@RequestMapping(value = "home")
public class HomeController {
	
	static Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	@Autowired
//	@Qualifier("processor")
//	private Processor processor;
//	
//	@Autowired
//	private JacksonObjectMapperFactory jacksonObjectMapperFactory;
//	
//	@Autowired
//    ServletContext context;
	
	@RequestMapping(value = "/startFolderMonitor")
	@ResponseBody
	public String startFolderMonitor() throws Exception {
		
		logger.info("do nothing");
		
//		String message;
//		
//		Object started = context.getAttribute("monitorStarted");
//		
//		if (started==null){
//			processor.start();
//			message = "Monitor Started";
//			logger.info("Monitor Started");
//			context.setAttribute("monitorStarted",true);
//		}else{
//			message = "Running";
//		}
		
		return "dismissed";//jacksonObjectMapperFactory.getObject().writeValueAsString(ImmutableMap.of("started", message));		
	}

}
