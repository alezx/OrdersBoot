package folderMonitor.reader.article;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import folderMonitor.domain.Article;
import folderMonitor.reader.XlsxReader;

@Component(value = "productionListReader")
public class ProductionListReader extends XlsxReader {

	static Logger logger = LoggerFactory.getLogger(ProductionListReader.class);

	@Value("${productionlist.articles.sheet.position}")
	private Integer sheetPosition;

	@Value("${productionlist.articles.id}")
	private String idColumn;

	@Value("${productionlist.articles.production}")
	private String productionColumn;

	protected Map<String, Article> retrieveArticles(XSSFWorkbook workbook)
			throws Exception {
		XSSFSheet sheet = workbook.getSheetAt(sheetPosition);
		Iterator<Row> rowIterator = sheet.iterator();
		Map<String, Article> map = new HashMap<String, Article>();
		Article article;
		boolean started = false;
		int line = 0;
		while (rowIterator.hasNext()) {

			String code = "unknow";
			article = new Article();
			try {
				Row row = rowIterator.next();
				line = row.getRowNum();
				code = getStringValue(row, idColumn);
				started = true;
				setValues(article, row);
				map.put(code, article);
			} catch (Exception e) {
				if (started) {
					logger.error("error importing article " + code
							+ " at line " + line, e);
				}
			}

		}
		return map;
	}

	private void setValues(Article article, Row row) throws Exception {
		article.setCode(getStringValue(row, idColumn));

		try {
			article.setProductionQuantity(getLongValue(row, productionColumn));

		} catch (Exception e) {
			// ignored
		}
	}

}
