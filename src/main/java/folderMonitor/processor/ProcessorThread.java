package folderMonitor.processor;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import folderMonitor.domain.Article;
import folderMonitor.domain.Order;
import folderMonitor.reader.Reader;
import folderMonitor.reader.ReaderFactory;
import folderMonitor.services.ImportArticlesService;

@Component(value = "processorThread")
public class ProcessorThread implements Runnable{
	
	static Logger logger = LoggerFactory.getLogger(ProcessorThread.class);

	private String folderPath;
	
	@Autowired
	@Qualifier("readerFactory")
	private ReaderFactory readerFactory;	
	
	@Autowired
	@Qualifier("processorService")
	private ProcessorService processorService;
	
	@Autowired
	@Qualifier("importArticleService")
	protected ImportArticlesService importArticleService;
	
	public String getFolderPath() {
		return folderPath;
	}



	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}



	public void run() {
		
		File folder = new File(folderPath);
		if(folder.isDirectory()){
			File[] files = folder.listFiles(new MyFilter());
			if(files!=null){
				processFiles(files);
			}
		}
	}

	private void processFiles(File[] files) {
		for (File file : files) {
			processFile(file);
		}
	}

	private void processFile(File file) {
		try{
			Reader reader = readerFactory.getReader(file.getName());
			Object object = reader.read(file);
			if(object instanceof Order){
				Order order = (Order) object;
				processorService.process(order);
			}else if (Collection.class.isAssignableFrom( object.getClass()) ){
				Collection collection = (Collection) object;
				Object first = collection.iterator().next();
				if (first instanceof Article){
					importArticleService.saveOnDB((Collection<Article>) collection);
				}
			}
			
		}catch(Throwable e){
			logger.error("ERROR",e);
		}
		finally{
			processorService.renameFile(file);
		}
	}
	
	
	static class MyFilter implements FileFilter{

		@Override
		public boolean accept(File pathname) {
			return !pathname.getName().startsWith("_");
		}
		
	}

}
