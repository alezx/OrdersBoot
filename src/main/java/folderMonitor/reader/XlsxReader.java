package folderMonitor.reader;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import folderMonitor.domain.Article;
import folderMonitor.reader.Reader;
import folderMonitor.services.ImportArticlesService;

@Component(value = "stockListReader")
public abstract class XlsxReader implements Reader {

	static Logger logger = LoggerFactory.getLogger(XlsxReader.class);

	
	@Override
	public Object read(File f) throws Exception {

		FileInputStream file = new FileInputStream(f);
		OPCPackage pkg = OPCPackage.open(file);
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(pkg);
			Map<String, Article> articlesMap = retrieveArticles(workbook);

			return articlesMap.values();

		} catch (Exception e) {
			logger.error("ERROR", e);
			throw e;
		} finally {
			pkg.close(); // gracefully closes the underlying zip file
		}
	}

	protected abstract Map<String, Article> retrieveArticles(XSSFWorkbook workbook) throws Exception;
	
}
