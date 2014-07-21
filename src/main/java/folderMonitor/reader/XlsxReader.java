package folderMonitor.reader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import folderMonitor.domain.Article;

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

			return new ArrayList<Article>(articlesMap.values());

		} catch (Exception e) {
			logger.error("ERROR", e);
			throw e;
		} finally {
			pkg.close(); // gracefully closes the underlying zip file
		}
	}

	protected abstract Map<String, Article> retrieveArticles(XSSFWorkbook workbook) throws Exception;

	protected String getStringValue(Row row, String idColumn) throws Exception {
		return row.getCell(getAlphabetPos(idColumn)).toString();
	}

	protected Long getLongValue(Row row, String column) throws Exception {
		Long l = null;
		Cell cell = row.getCell(getAlphabetPos(column));
		try {
			l = (long) cell.getNumericCellValue();
		} catch (Exception e) {
			try {
				l = Long.valueOf(cell.toString());
			} catch (NumberFormatException nfe) {
				logger.warn("Column: [{}]. Impossible to cast to long [{}]", column, cell.toString());
			}
		}
		return l;
	}

	protected int getAlphabetPos(String s) throws Exception {
		return s.toLowerCase().charAt(0) - 'a';
	}

}
