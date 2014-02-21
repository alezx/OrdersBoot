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

	protected Map<String, Article> retrieveArticles(XSSFWorkbook workbook) throws Exception {
		XSSFSheet sheet = workbook.getSheet(sheetName);
		Iterator<Row> rowIterator = sheet.iterator();
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
				String series = row.getCell(getAlphabetPos(seriesColumn)).toString();
				if (series.equals(filter)) {
					started = true;
					setValues(article, row);
					code = getStringValue(row, idColumn);
					map.put(code, article);
				}
			} catch (Exception e) {
				if (started) {
					logger.error("error importing article " + code + " at line " + line, e);
				}
			}

		}
		return map;
	}

	private void setValues(Article article, Row row) throws Exception {
		article.setCode(getStringValue(row, idColumn));
		article.setSeries(getStringValue(row, seriesColumn));
		article.setTitle(getStringValue(row, titleColumn));
		article.setFormat(getStringValue(row, formatColumn));
		article.setInterior(getStringValue(row, interiorColumn));

		article.setAvailableforSale(getLongValue(row, availableforSaleColumn));
		article.setReservationStatus(getStringValue(row, reservationStatusColumn));
		article.setReservedStock(getLongValue(row, reservedStockColumn));
		article.setAvailableonHand(getLongValue(row, availableonHandColumn));
		article.setCaseQuantity(getLongValue(row, caseColumn));
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getIdColumn() {
		return idColumn;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	public String getSeriesColumn() {
		return seriesColumn;
	}

	public void setSeriesColumn(String seriesColumn) {
		this.seriesColumn = seriesColumn;
	}

	public String getTitleColumn() {
		return titleColumn;
	}

	public void setTitleColumn(String titleColumn) {
		this.titleColumn = titleColumn;
	}

	public String getFormatColumn() {
		return formatColumn;
	}

	public void setFormatColumn(String formatColumn) {
		this.formatColumn = formatColumn;
	}

	public String getInteriorColumn() {
		return interiorColumn;
	}

	public void setInteriorColumn(String interiorColumn) {
		this.interiorColumn = interiorColumn;
	}

	public String getAvailableforSaleColumn() {
		return availableforSaleColumn;
	}

	public void setAvailableforSaleColumn(String availableforSaleColumn) {
		this.availableforSaleColumn = availableforSaleColumn;
	}

	public String getReservedStockColumn() {
		return reservedStockColumn;
	}

	public void setReservedStockColumn(String reservedStockColumn) {
		this.reservedStockColumn = reservedStockColumn;
	}

	public String getAvailableonHandColumn() {
		return availableonHandColumn;
	}

	public void setAvailableonHandColumn(String availableonHandColumn) {
		this.availableonHandColumn = availableonHandColumn;
	}

	public String getReservationStatusColumn() {
		return reservationStatusColumn;
	}

	public void setReservationStatusColumn(String reservationStatusColumn) {
		this.reservationStatusColumn = reservationStatusColumn;
	}

	public String getCaseColumn() {
		return caseColumn;
	}

	public void setCaseColumn(String caseColumn) {
		this.caseColumn = caseColumn;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilters(String filter) {
		this.filter = filter;
	}

}
