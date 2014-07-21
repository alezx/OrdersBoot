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

@Component(value = "stockListReader")
public class StockListReader extends XlsxReader {

	static Logger logger = LoggerFactory.getLogger(StockListReader.class);

	@Value("${stocklist.articles.sheet.name}")
	private String sheetName;

	@Value("${stocklist.articles.id}")
	private String idColumn;

	@Value("${stocklist.articles.series}")
	private String seriesColumn;

	@Value("${stocklist.articles.title}")
	private String titleColumn;

	@Value("${stocklist.articles.format}")
	private String formatColumn;

	@Value("${stocklist.articles.interior}")
	private String interiorColumn;

	@Value("${stocklist.articles.availableforSale_q}")
	private String availableforSaleColumn;

	@Value("${stocklist.articles.reservedStock_q}")
	private String reservedStockColumn;

	@Value("${stocklist.articles.availableonHand_q}")
	private String availableonHandColumn;

	@Value("${stocklist.articles.reservationStatus_q}")
	private String reservationStatusColumn;

	@Value("${stocklist.articles.case_q}")
	private String caseColumn;

	@Value("${stocklist.articles.filter}")
	private String filter;

	protected Map<String, Article> retrieveArticles(XSSFWorkbook workbook)
			throws Exception {
		XSSFSheet sheet = workbook.getSheet(sheetName);
		Iterator<Row> rowIterator = sheet.iterator();
		Map<String, Article> map = new HashMap<String, Article>();
		Article article;
		boolean started = false;
		int line = 0;
		while (rowIterator.hasNext()) {

			String code = "unknow";
			article = new Article();
			try {
				// logger.info("line " + line);
				Row row = rowIterator.next();
				line = row.getRowNum();
				code = getStringValue(row, idColumn);
				String series = row.getCell(getAlphabetPos(seriesColumn))
						.toString();
				// if (equalsAnyFilter(series)) {
				started = true;
				setValues(article, row);
				map.put(code, article);
				// line++;
				// }
			} catch (Exception e) {
				if (started) {
					logger.error("error importing article " + code
							+ " at line " + line, e);
				}
			}

		}
		return map;
	}

	private boolean equalsAnyFilter(String series) {
		for (String f : filter.split(",")) {
			if (f.equals(series)) {
				return true;
			}
		}
		return false;
	}

	private void setValues(Article article, Row row) throws Exception {
		article.setCode(getStringValue(row, idColumn));

		try {
			article.setSeries(getStringValue(row, seriesColumn));
			article.setTitle(getStringValue(row, titleColumn));
			article.setFormat(getStringValue(row, formatColumn));
			article.setInterior(getStringValue(row, interiorColumn));

			// article.setAvailableforSale(getLongValue(row, availableforSaleColumn));
			// article.setReservationStatus(getStringValue(row, reservationStatusColumn));
			// article.setReservedStock(getLongValue(row, reservedStockColumn));
			// article.setAvailableonHand(getLongValue(row, availableonHandColumn));
			// article.setCaseQuantity(getLongValue(row, caseColumn));

			article.setWarehouseQuantity(getLongValue(row,
					availableonHandColumn));
		} catch (Exception e) {
			// ignored
		}
	}

}
