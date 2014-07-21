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

@Component(value = "priceListReader")
public class PriceListReader extends XlsxReader {

	static Logger logger = LoggerFactory.getLogger(PriceListReader.class);

	@Value("${pricelist.articles.sheet.position}")
	private Integer sheetPosition;

	@Value("${pricelist.articles.id}")
	private String idColumn;

	// @Value("${pricelist.articles.series}")
	// private String seriesColumn;

	@Value("${pricelist.articles.title}")
	private String titleColumn;

	@Value("${pricelist.articles.format}")
	private String formatColumn;

	@Value("${pricelist.articles.interior}")
	private String interiorColumn;

	// @Value("${pricelist.articles.case_q}")
	// private String quantityColumn;

	@Value("${pricelist.articles.netPrice}")
	private String netPriceColumn;

	// @Value("${pricelist.articles.rrp}")
	// private String rrpColumn;

	protected Map<String, Article> retrieveArticles(XSSFWorkbook workbook)
			throws Exception {
		XSSFSheet sheet = workbook.getSheetAt(sheetPosition);
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next(); // skipping first line becase is the HEADERS
		Map<String, Article> map = new HashMap<String, Article>();
		Article article;
		boolean started = false;
		while (rowIterator.hasNext()) {

			String code = "unknow";
			int line = 0;
			article = new Article();
			try {
				Row row = rowIterator.next();
				line = row.getRowNum();
				started = true;
				setValues(article, row);
				code = getValue(row, idColumn);
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
		article.setCode(getValue(row, idColumn));
		// article.setSeries(getValue(row, seriesColumn));
		article.setTitle(getValue(row, titleColumn));
		article.setFormat(getValue(row, formatColumn));
		article.setInterior(getValue(row, interiorColumn));
		// article.setProductionQuantity(getLongValue(row, quantityColumn)); nn e' la prod quantity. viena da un altro file
		float price = (float) row.getCell(getAlphabetPos(netPriceColumn))
				.getNumericCellValue();
		article.setPrice(price);

	}

	private String getValue(Row row, String column) throws Exception {
		return row.getCell(getAlphabetPos(column)).getStringCellValue();
	}

}
