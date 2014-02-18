package folderMonitor.utils;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.FactoryBean;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



/**
 * Used to configure an Object Mapper in spring.
 * 
 * Jackson 2 doesn't have any feature to configure an object mapper nicely in Spring, so this class is required. 
 * 
 * 3.0+ versions of Spring do have some support for this.
 * 
 * 
 */
public class JacksonObjectMapperFactory implements FactoryBean{

	/*
	 * Don't change the default behaviour of this class. Bugs will be introduced if the defaults changes.  
	 */
	private boolean failOnEmptyBeans = false;
	private boolean indentOutput = true;
	private boolean serializeNullsAsEmptyStrings = true;
	public static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	private boolean outputNulls = false;
	private boolean singleton = true;
	
	@Override
	public ObjectMapper getObject() throws Exception {
		
		ObjectMapper result = new ObjectMapper();
		
		
		result.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, isFailOnEmptyBeans());
		result.configure(SerializationFeature.INDENT_OUTPUT, isIndentOutput());
		
		configureDateTimeFormat(result);
		
		configureVisibility(result);
		configureOutputNulls(result);
		
		return result;
	}

	private void configureOutputNulls(ObjectMapper result) {
		if(!isOutputNulls()){
			result.setSerializationInclusion(Include.NON_NULL);
		}
	}

	private void configureDateTimeFormat(ObjectMapper result) {
		if(StringUtils.isNotBlank(dateTimeFormat)) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
			result.setDateFormat(dateFormat);
		}
	}

	
	/**
	 * Configures how Jackson will pull data out of objects and convert them into JSON.
	 * 
	 * Currently configured so that only fields are visible.
	 * 
	 * @param result
	 */
	private void configureVisibility(ObjectMapper result) {
		result.setVisibilityChecker(result.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(Visibility.ANY)
                .withGetterVisibility(Visibility.NONE) 
                .withSetterVisibility(Visibility.NONE)
                .withCreatorVisibility(Visibility.NONE)
                .withIsGetterVisibility(Visibility.NONE));
	}

	@Override
	public Class<ObjectMapper> getObjectType() {
		return ObjectMapper.class;
	}

	@Override
	public boolean isSingleton() {
		return singleton;
	}

	public boolean isFailOnEmptyBeans() {
		return failOnEmptyBeans;
	}
	
	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	public void setFailOnEmptyBeans(boolean failOnEmptyBeans) {
		this.failOnEmptyBeans = failOnEmptyBeans;
	}

	public boolean isIndentOutput() {
		return indentOutput;
	}

	public void setIndentOutput(boolean indentOutput) {
		this.indentOutput = indentOutput;
	}

	public boolean isSerializeNullsAsEmptyStrings() {
		return serializeNullsAsEmptyStrings;
	}

	public void setSerializeNullsAsEmptyStrings(boolean nullsAsEmptyStrings) {
		this.serializeNullsAsEmptyStrings = nullsAsEmptyStrings;
	}

	public String getDateTimeFormat() {
		return dateTimeFormat;
	}

	public void setDateTimeFormat(String dateFormat) {
		this.dateTimeFormat = dateFormat;
	}

	public boolean isOutputNulls() {
		return outputNulls;
	}

	public void setOutputNulls(boolean outputNulls) {
		this.outputNulls = outputNulls;
	}
}
