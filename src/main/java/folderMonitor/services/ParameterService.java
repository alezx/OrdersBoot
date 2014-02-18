package folderMonitor.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import folderMonitor.controllers.parameters.FilterEntry;
import folderMonitor.controllers.parameters.Filters;
import folderMonitor.utils.JacksonObjectMapperFactory;

@Component(value = "parameterService")
public class ParameterService {

	static Logger logger = LoggerFactory.getLogger(ParameterService.class);

	@Autowired
	private JacksonObjectMapperFactory jacksonObjectMapperFactory;

	public Filters fromJson(String s) throws JsonParseException,
			JsonMappingException, IOException, Exception {
		String json = "{ \"filters\" : " + s + "}";
		return jacksonObjectMapperFactory.getObject().readValue(json,
				Filters.class);
	}

	public String getOperator(FilterEntry f) {
		if (f.getComparison() == null
				|| f.getComparison().equalsIgnoreCase("eq")) {
			return " like ";
		} else if (f.getComparison().equalsIgnoreCase("lt")) {
			return " <= ";
		} else if (f.getComparison().equalsIgnoreCase("gt")) {
			return " >= ";
		} else
			return null;
	}

	public String getSqlCondition(FilterEntry f) {
		StringBuilder sb = new StringBuilder(" and ");
		sb.append(f.getField());
		sb.append(getOperator(f));
		sb.append(" :").append(f.getField()).append("_").append(f.getComparison());
		return sb.toString();
	}

	public String getSqlQuery(String originalSql, Filters filters) {
		try {
			StringBuilder sb = new StringBuilder(originalSql);
			for (FilterEntry f : filters.getFilters()) {
				sb.append(getSqlCondition(f));
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error("error in converting filter to sql", e);
			return originalSql;
		}
	}
}
