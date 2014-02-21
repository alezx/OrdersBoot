package folderMonitor.processor;

import java.io.File;
import java.io.FileFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(value = "processorThread")
public class PollerThread implements Runnable {

	static Logger logger = LoggerFactory.getLogger(PollerThread.class);

	private String folderPath;

	@Autowired
	@Qualifier("processorService")
	private ProcessorService processorService;

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public void run() {

		File folder = new File(folderPath);
		if (folder.isDirectory()) {
			File[] files = folder.listFiles(new MyFilter());
			if (files != null) {
				processFiles(files);
			}
		}
	}

	private void processFiles(File[] files) {
		for (File file : files) {
			processorService.processFile(file);
		}
	}

	static class MyFilter implements FileFilter {

		@Override
		public boolean accept(File pathname) {
			return !pathname.getName().startsWith("_") && !pathname.getName().endsWith("DS_Store");
		}

	}

}
