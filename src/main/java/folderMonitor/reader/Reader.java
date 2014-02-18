package folderMonitor.reader;

import java.io.File;

import folderMonitor.domain.Order;

public interface Reader {
	
	Object read(File f) throws Exception;
	
}
