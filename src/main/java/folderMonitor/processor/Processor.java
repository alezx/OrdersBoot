package folderMonitor.processor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

@Component(value = "processor")
public class Processor {

	@Autowired
	private ApplicationContext applicationContext;

	@Value("${folder}")
	private String folderPath;

	@Autowired
	@Qualifier("processorThread")
	private PollerThread pollerThread;

	private ScheduledExecutorService scheduler;

	public void start() {
		pollerThread.setFolderPath(folderPath);
		scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("ProcessorThread").build());
		scheduler.scheduleWithFixedDelay(pollerThread, 0, 5, TimeUnit.SECONDS);
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

}
