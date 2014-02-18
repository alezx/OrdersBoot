package folderMonitor.controllers.parameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import folderMonitor.domain.Mappable;



public class Filters implements Mappable{
	private List<FilterEntry> filters;

	public List<FilterEntry> getFilters() {
		return filters;
	}

	public void setFilters(List<FilterEntry> filters) {
		this.filters = filters;
	}

	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		for(FilterEntry f : filters){
			map.putAll(f.toMap()); // actually f.toMap has only one entry
		}
		return map;
	}
	
	
}
