package folderMonitor.controllers.parameters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import folderMonitor.domain.Mappable;

public class FilterEntry implements Mappable{
	
	public static final String FORMAT = "MM/dd/yyyy";
	private static final ThreadLocal<DateFormat> SDF = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(FORMAT);
		}
	};
	
	private String type;
	private String value;
	private String field;
	private String comparison;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getComparison() {
		return comparison;
	}
	public void setComparison(String comparison) {
		this.comparison = comparison;
	}
	
	public Object getObjectValue() {
		String type = this.getType();
		if(type.equalsIgnoreCase("string")){
			return this.getValue();			
		}
		else if(type.equalsIgnoreCase("numeric")){
			return Integer.parseInt(this.getValue());
		}
		else if (type.equalsIgnoreCase("date")){
			try {
				return SDF.get().parse(this.getValue());
			} catch (ParseException e) {
				return null;
			}
		}
		else return null;
	}
	
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		Object value = (getComparison() == null || getComparison().equalsIgnoreCase("eq")) && 
				getType().equalsIgnoreCase("string") ? getObjectValue() + "%" : getObjectValue();
		map.put(getField()+"_"+getComparison(), value);
		return map;
	}
	
	
	
}
