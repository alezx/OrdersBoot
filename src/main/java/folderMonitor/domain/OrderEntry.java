package folderMonitor.domain;

import java.io.Serializable;
import java.text.ParseException;
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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "order_entries")
@NamedQueries({
	@NamedQuery(name = "OrderEntry.findAll", query = "SELECT m FROM OrderEntry m"),
	@NamedQuery(name = "OrderEntry.findById", query = "SELECT m FROM OrderEntry m WHERE m.id = :id")
})

public class OrderEntry implements Serializable, Mappable {
	
	public static final String QUERY_ALL_BY_ORDER = "select * from order_entries oe where oe.order_id = :orderId";
	public static final String QUERY_ALL_BY_ARTICLE = "select * from order_entries oe where oe.article = :article"; 
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;

	@JoinColumn(name = "article", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Article article;
	
	@Column(name = "quantity")
	private long quantity;

	@JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Order order;

	
	public OrderEntry() {
		super();
	}



	public OrderEntry(Integer id, Article article, long quantity, Order order) {
		super();
		this.id = id;
		this.article = article;
		this.quantity = quantity;
		this.order=order;
	}

	public OrderEntry(Article article, long quantity, Order order) {
		super();
		this.article = article;
		this.quantity = quantity;
		this.order=order;
	}

	
	
	

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Article getArticle() {
		return article;
	}



	public void setArticle(Article article) {
		this.article = article;
	}



	public long getQuantity() {
		return quantity;
	}



	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Order getOrder() {
		return order;
	}



	public void setOrder(Order order) {
		this.order = order;
	}



	@Override
	public boolean equals(Object object) {
		if (!(object instanceof OrderEntry)) {
			return false;
		}
		OrderEntry other = (OrderEntry) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}



	@Override
	public String toString() {
		return "OrderEntry [id=" + id + ", article=" + article.getCode() + ", quantity="
				+ quantity + ", orderCode=" + order.getCode() + "]";
	}

	
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ID", getId());
		map.put("ARTICLE", getArticle()!= null ? getArticle().getId() : null);
		map.put("ORDER", getOrder()!= null ? getOrder().getId() : null);
		map.put("ORDER_CODE", getOrder()!= null ? getOrder().getCode() : null);
		map.put("ORDER_SYSTEM_CODE", getOrder()!= null ? getOrder().getSystemCode() : null);
		map.put("ARTICLE_CODE", getArticle()!= null ? getArticle().getCode() : null);
		map.put("QUANTITY",getQuantity());
		map.put("NEW_QUANTITY",getQuantity());
		return map;
	}

	
	public static List<Map<String, Object>> toMapList(List<OrderEntry> orders) {
		List<Map<String, Object>> map = Lists.transform(orders, new OrderEntry.TransformToMap());
		return map;
	}
	
	public static class TransformToMap implements Function<OrderEntry, Map<String, Object>> {
	    @Override
	    public Map<String, Object> apply(OrderEntry o) {
	        return o.toMap();
	    }
	}
	
}
