package folderMonitor.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "articles")
@NamedQueries({
		@NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a"),
		@NamedQuery(name = "Article.findById", query = "SELECT a FROM Article a WHERE a.id = :id") })
public class Article implements Serializable {

	public static final String QUERY_ALL = "select * from articles a where 1=1";

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;

	@Column(name = "SERIES")
	private String series;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "FORMAT")
	private String format;

	@Column(name = "INTERIOR")
	private String interior;

	@Column(name = "production_Quantity")
	private Long productionQuantity;

	@Column(name = "warehouse_Quantity")
	private Long warehouseQuantity;

	@Column(name = "REQUESTED_QUANTITY")
	private Long requestedQuantity;

	@Column(name = "BALANCE_PROD")
	private Long balanceProd;

	@Column(name = "BALANCE_WAREHOUSE")
	private Long balanceWarehouse;

	@Column(name = "BALANCE_PROD_PRICE")
	private Float balanceProdPrice;

	@Column(name = "BALANCE_WAREHOUSE_PRICE")
	private Float balanceWarehousePrice;

	@Column(name = "PRICE")
	private Float price;

	@Column(name = "CODE", unique = true)
	private String code;

	public Article() {
		super();
	}

	public Article(String code, Long productionQuantity, Long warehouseQuantity) {
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

	public Long getBalanceProd() {
		return balanceProd;
	}

	public void setBalanceProd(Long balanceProd) {
		this.balanceProd = balanceProd;
	}

	public Long getBalanceWarehouse() {
		return balanceWarehouse;
	}

	public void setBalanceWarehouse(Long balanceWarehouse) {
		this.balanceWarehouse = balanceWarehouse;
	}

	public Float getBalanceProdPrice() {
		return balanceProdPrice;
	}

	public void setBalanceProdPrice(Float balanceProdPrice) {
		this.balanceProdPrice = balanceProdPrice;
	}

	public Float getBalanceWarehousePrice() {
		return balanceWarehousePrice;
	}

	public void setBalanceWarehousePrice(Float balanceWarehousePrice) {
		this.balanceWarehousePrice = balanceWarehousePrice;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (code != null ? code.hashCode() : 0);
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
		return "Article [id=" + id + ", series=" + series + ", title=" + title + ", format="
				+ format + ", interior=" + interior + ", productionQuantity=" + productionQuantity
				+ ", warehouseQuantity=" + warehouseQuantity + ", requestedQuantity="
				+ requestedQuantity + ", balanceProd=" + balanceProd + ", balanceWarehouse="
				+ balanceWarehouse + ", balanceProdPrice=" + balanceProdPrice
				+ ", balanceWarehousePrice=" + balanceWarehousePrice + ", price=" + price
				+ ", code=" + code + "]";
	}
 


	// @Override
	// public Map<String, Object> toMap() {
	// Map<String, Object> map = new HashMap<String, Object>();
	//
	// map.put("ID", getId());
	// map.put("CODE", getCode());
	// map.put("PRD_Q", getProductionQuantity());
	// map.put("WAREHOUSE_Q", getWarehouseQuantity());
	// map.put("REQUESTED_Q", getRequestedQuantity());
	// map.put("PRICE", getPrice());
	//
	// map.put("SERIES", getSeries());
	// map.put("TITLE", getTitle());
	// map.put("FORMAT", getFormat());
	// map.put("INTERIOR", getInterior());
	//
	// map.put("AVAILABLEFORSALE_Q", getAvailableforSale());
	// map.put("RESERVEDSTOCK_Q", getReservedStock());
	// map.put("AVAILABLEONHAND_Q", getAvailableonHand());
	// map.put("RESERVATIONSTATUS", getReservationStatus());
	// map.put("CASE_Q", getCaseQuantity());
	//
	// map.put("BALANCE_PROD", getBalanceProd());
	// map.put("BALANCE_WAREHOUSE", getBalanceWarehouse());
	// map.put("BALANCE_PROD_PRICE", getBalanceProdPrice());
	// map.put("BALANCE_WAREHOUSE_PRICE", getBalanceWarehousePrice());
	//
	// return map;
	// }

	// public static Article fromMap(Map<String, Object> map) throws ParseException {
	// Article o = new Article();
	// o.setId((Integer) (map.get("ID")));
	// o.setCode((String) map.get("CODE"));
	// o.setProductionQuantity(Long.valueOf(map.get("PRD_Q").toString()));
	// o.setWarehouseQuantity(Long.valueOf(map.get("WAREHOUSE_Q").toString()));
	// o.setRequestedQuantity(Long.valueOf(map.get("REQUESTED_Q").toString()));
	// o.setPrice(Float.valueOf(map.get("PRICE").toString()));
	//
	// o.setSeries((String) map.get("SERIES"));
	// o.setTitle((String) map.get("TITLE"));
	// o.setFormat((String) map.get("FORMAT"));
	// o.setInterior((String) map.get("INTERIOR"));
	//
	// o.setAvailableforSale(Long.valueOf(map.get("AVAILABLEFORSALE_Q").toString()));
	// o.setReservedStock(Long.valueOf(map.get("RESERVEDSTOCK_Q").toString()));
	// o.setAvailableonHand(Long.valueOf(map.get("AVAILABLEONHAND_Q").toString()));
	// o.setReservationStatus((String) map.get("RESERVATIONSTATUS"));
	// o.setCaseQuantity(Long.valueOf(map.get("CASE_Q").toString()));
	//
	// o.setBalanceProd(Long.valueOf(map.get("BALANCE_PROD").toString()));
	// o.setBalanceWarehouse(Long.valueOf(map.get("BALANCE_WAREHOUSE").toString()));
	// o.setBalanceProdPrice(Float.valueOf(map.get("BALANCE_PROD_PRICE").toString()));
	// o.setBalanceWarehousePrice(Float.valueOf(map.get("BALANCE_WAREHOUSE_PRICE").toString()));
	//
	// return o;
	// }
	//
	// public static List<Map<String, Object>> toMapList(List<Article> orders) {
	// List<Map<String, Object>> map = Lists.transform(orders, new Article.TransformToMap());
	// return map;
	// }
	//
	// public static class TransformToMap implements Function<Article, Map<String, Object>> {
	// @Override
	// public Map<String, Object> apply(Article o) {
	// return o.toMap();
	// }
	// }

}
