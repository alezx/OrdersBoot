package folderMonitor.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Function;
import com.google.common.collect.Lists;



@Entity
@Table(name = "articles")
@NamedQueries({
		@NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a"),
		@NamedQuery(name = "Article.findById", query = "SELECT a FROM Article a WHERE a.id = :id"),
		@NamedQuery(name = "Article.findByCode", query = "SELECT a FROM Article a WHERE a.code = :code") })
public class Article implements Serializable, Mappable {

	public static final String QUERY_ALL = "select * from articles a where 1=1";

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;

	@Basic(optional = false)
	@Column(name = "code")
	private String code;

	@Basic(optional = false)
	@Column(name = "series")
	private String series;

	@Basic(optional = false)
	@Column(name = "title")
	private String title;

	@Basic(optional = false)
	@Column(name = "format")
	private String format;

	@Basic(optional = false)
	@Column(name = "interior")
	private String interior;

	@Column(name = "availableforSale_q")
	private Long availableforSale;

	@Column(name = "reservedStock_q")
	private Long reservedStock;
	
	@Column(name = "availableonHand_q")
	private Long availableonHand;
	
	@Column(name = "reservationStatus")
	private String reservationStatus;
	
	@Column(name = "case_q")
	private Long caseQuantity;

	@Column(name = "prd_q")
	private Long productionQuantity;

	@Column(name = "warehouse_q")
	private Long warehouseQuantity;

	@Column(name = "requested_q")
	private Long requestedQuantity;

	@Column(name = "price")
	private Float price;

	public Article() {
		super();
	}

	public Article(Integer id, String code, long productionQuantity,
			long warehouseQuantity) {
		super();
		this.id = id;
		this.code = code;
		this.productionQuantity = productionQuantity;
		this.warehouseQuantity = warehouseQuantity;
	}

	public Article(String code, long productionQuantity, long warehouseQuantity) {
		super();
		this.code = code;
		this.productionQuantity = productionQuantity;
		this.warehouseQuantity = warehouseQuantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getProductionQuantity() {
		return productionQuantity;
	}

	public void setProductionQuantity(Long productionQuantity) {
		this.productionQuantity = productionQuantity;
	}

	public Long getWarehouseQuantity() {
		return warehouseQuantity;
	}

	public void setWarehouseQuantity(Long warehouseQuantity) {
		this.warehouseQuantity = warehouseQuantity;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Long getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(Long requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getInterior() {
		return interior;
	}

	public void setInterior(String interior) {
		this.interior = interior;
	}

	public Long getAvailableforSale() {
		return availableforSale;
	}

	public void setAvailableforSale(Long availableforSale) {
		this.availableforSale = availableforSale;
	}

	public Long getReservedStock() {
		return reservedStock;
	}

	public void setReservedStock(Long reservedStock) {
		this.reservedStock = reservedStock;
	}

	public Long getAvailableonHand() {
		return availableonHand;
	}

	public void setAvailableonHand(Long availableonHand) {
		this.availableonHand = availableonHand;
	}

	public String getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	public Long getCaseQuantity() {
		return caseQuantity;
	}

	public void setCaseQuantity(Long caseQuantity) {
		this.caseQuantity = caseQuantity;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Article)) {
			return false;
		}
		Article other = (Article) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", code=" + code + ", productionQuantity="
				+ productionQuantity + ", warehouseQuantity="
				+ warehouseQuantity + "]";
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("ID", getId());
		map.put("CODE", getCode());
		map.put("PRD_Q", getProductionQuantity());
		map.put("WAREHOUSE_Q", getWarehouseQuantity());
		map.put("REQUESTED_Q", getRequestedQuantity());
		map.put("PRICE", getPrice());
		
		map.put("SERIES", getSeries());
		map.put("TITLE", getTitle());
		map.put("FORMAT", getFormat());
		map.put("INTERIOR", getInterior());
		
		
		map.put("AVAILABLEFORSALE_Q", getAvailableforSale());
		map.put("RESERVEDSTOCK_Q", getReservedStock());
		map.put("AVAILABLEONHAND_Q", getAvailableonHand());
		map.put("RESERVATIONSTATUS", getReservationStatus());
		map.put("CASE_Q", getCaseQuantity());
		
		
		return map;
	}

	public static Article fromMap(Map<String, Object> map) throws ParseException {
		Article o = new Article();
		o.setId((Integer) (map.get("ID")));
		o.setCode((String) map.get("CODE"));
		o.setProductionQuantity(Long.valueOf(map.get("PRD_Q").toString()));
		o.setWarehouseQuantity(Long.valueOf(map.get("WAREHOUSE_Q").toString()));
		o.setRequestedQuantity(Long.valueOf(map.get("REQUESTED_Q").toString()));
		o.setPrice(Float.valueOf(map.get("PRICE").toString()));
		
		o.setSeries((String) map.get("SERIES"));
		o.setTitle((String) map.get("TITLE"));
		o.setFormat((String) map.get("FORMAT"));
		o.setInterior((String) map.get("INTERIOR"));
		
		o.setAvailableforSale(Long.valueOf(map.get("AVAILABLEFORSALE_Q").toString()));
		o.setReservedStock(Long.valueOf(map.get("RESERVEDSTOCK_Q").toString()));
		o.setAvailableonHand(Long.valueOf(map.get("AVAILABLEONHAND_Q").toString()));
		o.setReservationStatus((String) map.get("RESERVATIONSTATUS"));
		o.setCaseQuantity(Long.valueOf(map.get("CASE_Q").toString()));
		
		return o;
	}

	public static List<Map<String, Object>> toMapList(List<Article> orders) {
		List<Map<String, Object>> map = Lists.transform(orders,
				new Article.TransformToMap());
		return map;
	}

	public static class TransformToMap implements
			Function<Article, Map<String, Object>> {
		@Override
		public Map<String, Object> apply(Article o) {
			return o.toMap();
		}
	}

}
