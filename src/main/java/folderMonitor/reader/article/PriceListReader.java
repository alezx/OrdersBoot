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

	@Value("${pricelist.articles.case_q}")
	private String quantityColumn;

	@Value("${pricelist.articles.netPrice}")
	private String netPriceColumn;

	@Value("${pricelist.articles.rrp}")
	private String rrpColumn;

	protected Map<String, Article> retrieveArticles(XSSFWorkbook workbook) throws Exception {
		XSSFSheet sheet = workbook.getSheetAt(sheetPosition);
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
				started = true;
				setValues(article, row);
				code = getValue(row, idColumn);
				map.put(code, article);
			} catch (Exception e) {
				if (started) {
					logger.error("error importing article " + code + " at line " + line, e);
				}
			}

		}
		return map;
	}

	private void setValues(Article article, Row row) throws Exception {
		article.setCode(getValue(row, idColumn));
		article.setProductionQuantity(getLongValue(row, quantityColumn));
		article.setPrice((float) row.getCell(getAlphabetPos(netPriceColumn)).getNumericCellValue());

	}

	private String getValue(Row row, String column) throws Exception {
		return row.getCell(getAlphabetPos(column)).getStringCellValue();
	}

	public void setSheetPosition(Integer sheetPosition) {
		this.sheetPosition = sheetPosition;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	public void setQuantityColumn(String quantityColumn) {
		this.quantityColumn = quantityColumn;
	}

	public void setNetPriceColumn(String netPriceColumn) {
		this.netPriceColumn = netPriceColumn;
	}

	public void setRrpColumn(String rrpColumn) {
		this.rrpColumn = rrpColumn;
	}

}
