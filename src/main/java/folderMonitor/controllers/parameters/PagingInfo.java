package folderMonitor.controllers.parameters;

import java.util.HashMap;
import java.util.Map;


public class PagingInfo {
	
	private Integer page;
	private Integer start;
	private Integer limit;
	private String sort;
	private String dir;
	private String filter;
	
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", getPage());
		map.put("start",getStart());
		map.put("limit",getLimit());
		map.put("sort",getSort());
		map.put("dir",getDir());
		map.put("filter",getFilter());
		return map;
	}
	
}
