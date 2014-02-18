package folderMonitor.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Function;
import com.google.common.collect.Lists;



/**
 * 
 * @author porfirial
 */
@Entity
@Table(name = "orders")
@NamedQueries({
		@NamedQuery(name = "Order.findAll", query = "SELECT m FROM Order m"),
		@NamedQuery(name = "Order.findById", query = "SELECT m FROM Order m WHERE m.id = :id"),
		@NamedQuery(name = "Order.findByCode", query = "SELECT a FROM Order a WHERE a.code = :parameter"),
		@NamedQuery(name = "Order.findByCodeAndCustomer", query = "SELECT a FROM Order a WHERE a.code = :code and a.customer = :customer")
})
public class Order implements Serializable, Mappable {
	
	public static final String QUERY_ALL = "select * from orders o where 1=1"; 
	
	public static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	
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
	@Column(name = "system_code")
	private String systemCode;
	
	@Basic(optional = false)
	@Column(name = "customer")
	private String customer;
	
	@Basic(optional = false)
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
	
	@Basic(optional = false)
    @Column(name = "first_insert")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstInsert;

	@Column(name = "total")
	private float total;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch=FetchType.EAGER)
    private List<OrderEntry> orderEntryes;
	
	public Order() {
		super();
	}

	public Order(Integer id, String code, String customer, List<OrderEntry> orderEntryes) {
		super();
		this.id = id;
		this.code = code;
		this.orderEntryes=orderEntryes;
		this.customer=customer;
	}
	
	public Order(String code,  String customer, List<OrderEntry> orderEntryes) {
		super();
		this.code = code;
		this.orderEntryes=orderEntryes;
		this.customer=customer;
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

	public List<OrderEntry> getOrderEntryes() {
		return orderEntryes;
	}

	public void setOrderEntryes(List<OrderEntry> orderEntryes) {
		this.orderEntryes = orderEntryes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Date getFirstInsert() {
		return firstInsert;
	}

	public void setFirstInsert(Date firstInsert) {
		this.firstInsert = firstInsert;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public void addOrderEntry(OrderEntry oe){
		if (orderEntryes == null){
			orderEntryes= new ArrayList<OrderEntry>();
		}
		orderEntryes.add(oe);
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


	
	@Override
	public String toString() {
		return "Order [id=" + id + ", code=" + code + ", systemCode=" + systemCode + ", customer=" + 
				customer + ", lastUpdate=" + lastUpdate + ", firstInsert="
				+ firstInsert + "]";
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", getId());
		map.put("CODE",getCode());
		map.put("CUSTOMER",getCustomer());
		map.put("LAST_UPDATE", getLastUpdate());
		map.put("FIRST_INSERT", getFirstInsert());
		map.put("SYSTEM_CODE", getSystemCode());
		map.put("TOTAL", getTotal());
		return map;
	}
	
	public static Order fromMap(Map<String, Object> map) throws ParseException {
		Order o = new Order();
		o.setId((Integer)(map.get("ID")));
		o.setCode((String)map.get("CODE"));
		o.setCustomer((String)map.get("CUSTOMER"));
		o.setSystemCode((String)map.get("SYSTEM_CODE"));
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		o.setLastUpdate(sdf.parse((String)map.get("LAST_UPDATE")));
		o.setFirstInsert(sdf.parse((String)map.get("FIRST_INSERT")));
		o.setTotal((Float.valueOf(map.get("TOTAL").toString())));
		return o;
	}
	
	public static List<Map<String, Object>> toMapList(List<Order> orders) {
		List<Map<String, Object>> map = Lists.transform(orders, new Order.TransformToMap());
		return map;
	}
	
	public static class TransformToMap implements Function<Order, Map<String, Object>> {
	    @Override
	    public Map<String, Object> apply(Order o) {
	        return o.toMap();
	    }
	}
}
