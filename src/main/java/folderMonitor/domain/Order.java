package folderMonitor.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
@NamedQueries({
		@NamedQuery(name = "Order.findAll", query = "SELECT m FROM Order m"),
		@NamedQuery(name = "Order.findById", query = "SELECT m FROM Order m WHERE m.id = :id"),
		@NamedQuery(name = "Order.findByCode", query = "SELECT a FROM Order a WHERE a.code = :parameter") })
public class Order implements Serializable {

	public static final String QUERY_ALL = "select * from orders o where 1=1";

	public static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;

	@Basic(optional = false)
	@Column(name = "CODE")
	private String code;

	// @Basic(optional = false)
	// @Column(name = "SYSTEM_CODE")
	// private String systemCode;

	@Basic(optional = false)
	@Column(name = "CUSTOMER")
	private String customer;

	@Basic(optional = false)
	@Column(name = "CUSTOMER_CODE")
	private String customerCode;

	// @Basic(optional = false)
	// @Column(name = "LAST_UPDATE")
	// @Temporal(TemporalType.TIMESTAMP)
	// private Date lastUpdate;
	//
	// @Basic(optional = false)
	// @Column(name = "FIRST_INSERT")
	// @Temporal(TemporalType.TIMESTAMP)
	// private Date firstInsert;

	@Column(name = "PERC_AVAILABLE_PROD")
	private Float percAvailableProd;

	@Column(name = "PERC_AVAILABLE_WARE")
	private Float percAvailableWare;

	@Column(name = "READY")
	private String ready;

	@Column(name = "TOTAL")
	private float total;

	@Column(name = "PRIORITY", unique = true)
	private Integer priority;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<OrderEntry> orderEntryes;

	@Column(name = "TWELVE_MONTHS")
	private boolean twelveMonths;

	@Column(name = "ORDER_DATE")
	private Date orderDate;

	public Order() {
		super();
	}

	public Order(Integer id, String code, String customer,
			List<OrderEntry> orderEntryes) {
		super();
		this.id = id;
		this.code = code;
		this.orderEntryes = orderEntryes;
		this.customer = customer;
	}

	public Order(String code, String customer, List<OrderEntry> orderEntryes) {
		super();
		this.code = code;
		this.orderEntryes = orderEntryes;
		this.customer = customer;
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

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	@JsonIgnore
	public List<OrderEntry> getOrderEntryes() {
		return orderEntryes;
	}

	public void setOrderEntryes(List<OrderEntry> orderEntryes) {
		this.orderEntryes = orderEntryes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public float getTotal() {
		return total;
	}

	public Float getPercAvailableProd() {
		return percAvailableProd;
	}

	public void setPercAvailableProd(Float percAvailableProd) {
		this.percAvailableProd = percAvailableProd;
	}

	public Float getPercAvailableWare() {
		return percAvailableWare;
	}

	public void setPercAvailableWare(Float percAvailableWare) {
		this.percAvailableWare = percAvailableWare;
	}

	public String getReady() {
		return ready;
	}

	public void setReady(String ready) {
		this.ready = ready;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public void addOrderEntry(OrderEntry oe) {
		if (orderEntryes == null) {
			orderEntryes = new ArrayList<OrderEntry>();
		}
		orderEntryes.add(oe);
	}

	public boolean isTwelveMonths() {
		return twelveMonths;
	}

	public void setTwelveMonths(boolean twelveMonths) {
		this.twelveMonths = twelveMonths;
	}

	public Date isOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Order)) {
			return false;
		}
		Order other = (Order) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	// @Override
	// public Map<String, Object> toMap() {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("ID", getId());
	// map.put("CODE", getCode());
	// map.put("CUSTOMER", getCustomer());
	//
	// map.put("PERC_AVAILABLE_PROD", getPercAvailableProd());
	// map.put("PERC_AVAILABLE_WARE", getPercAvailableWare());
	// map.put("READY", getReady());
	//
	// map.put("TOTAL", getTotal());
	//
	// map.put("PRIORITY", getPriority());
	// return map;
	// }
	//
	// public static Order fromMap(Map<String, Object> map) throws ParseException {
	// Order o = new Order();
	// o.setId((Integer) (map.get("ID")));
	// o.setCode((String) map.get("CODE"));
	// o.setCustomer((String) map.get("CUSTOMER"));
	//
	// SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
	//
	// o.setPercAvailableProd((Float) map.get("PERC_AVAILABLE_PROD"));
	// o.setPercAvailableWare((Float) map.get("PERC_AVAILABLE_WARE"));
	// o.setReady((String) map.get("READY"));
	//
	// o.setTotal((Float.valueOf(map.get("TOTAL").toString())));
	//
	// o.setPriority((Integer) (map.get("PRIORITY")));
	// return o;
	// }
	//
	// public static List<Map<String, Object>> toMapList(List<Order> orders) {
	// List<Map<String, Object>> map = Lists.transform(orders, new Order.TransformToMap());
	// return map;
	// }
	//
	// public static class TransformToMap implements Function<Order, Map<String, Object>> {
	// @Override
	// public Map<String, Object> apply(Order o) {
	// return o.toMap();
	// }
	// }
}
