package folderMonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import folderMonitor.controllers.HomeController;
import folderMonitor.processor.Processor;

/*
spring standard component scan
spring boot autoconfiguration will bootstrap your Spring
application while attempting to configure the beans you need
it will also bootstrap an in-memory database if a driver is found
in the classpath and no datasource is defined
*/
@ComponentScan 
@EnableAutoConfiguration
@EnableJpaRepositories
public class App implements ApplicationListener<ContextRefreshedEvent> {

	static Logger logger = LoggerFactory.getLogger(App.class);
	
	@Autowired
	@Qualifier("processor")
	private Processor processor;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		processor.start();
		logger.info("Monitor Started");
	}
	
	
	public static void main(String[] args) throws Exception {
		SpringApplication springApplication = new SpringApplication(App.class, "classpath:/application-context.xml");
		springApplication.run(args);
	}
	
}
