package com.garden.mybatis.generator.config;

import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.garden.mybatis.generator.bean.Bean;
import com.garden.mybatis.generator.bean.GeneratorConfig;
import com.garden.mybatis.generator.bean.JdbcType;
import com.garden.mybatis.generator.bean.MapperClass;
import com.garden.mybatis.generator.bean.MapperXml;
import com.garden.mybatis.generator.bean.Schema;
import com.garden.mybatis.generator.bean.ServiceClass;
import com.garden.mybatis.generator.bean.Table;
import com.garden.mybatis.generator.util.GeneratorCollectionUtils;
import com.garden.mybatis.generator.util.GeneratorStringUtils;

/**
 * xml配置解析
 * @author chengguang
 *
 */
public class ConfigParser {
	
	private static final String CONFIG_NAME="generator.xml";
	private GeneratorConfig generatorConfig;
	private Schema schema;
	
	private ConfigParser() {
		generatorConfig = new GeneratorConfig();
		schema = new Schema();
		config();
	}
	
	@SuppressWarnings("unchecked")
	private void config() {
		InputStream is = ConfigParser.class.getClassLoader().getResourceAsStream(CONFIG_NAME);
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(is);
		} catch (DocumentException e) {
			e.printStackTrace();
			return ;
		}
		
		Element root = doc.getRootElement();
		
		Element jdbcTypeElement = root.element("jdbcType");
		JdbcType jdbcType = new JdbcType();
		jdbcType.setDbType(jdbcTypeElement.attributeValue("dbType"));
		jdbcType.setDriverClass(jdbcTypeElement.attributeValue("driverClass"));
		jdbcType.setUrl(jdbcTypeElement.attributeValue("url"));
		jdbcType.setUserName(jdbcTypeElement.attributeValue("userName"));
		jdbcType.setPassword(jdbcTypeElement.attributeValue("password"));
		generatorConfig.setJdbcType(jdbcType);
		
		Element packageElement = root.element("package");
		
		//bean参数获取
		Bean bean = new Bean();
		Element beanElement = packageElement.element("bean");
		bean.setPackageName(beanElement.attributeValue("packageName"));
		generatorConfig.setBean(bean);
		
		MapperXml mapperXml = new MapperXml();
		Element mapperXmlElement = packageElement.element("mapperXml");
		mapperXml.setPackageName(mapperXmlElement.attributeValue("packageName"));
		generatorConfig.setMapperXml(mapperXml);
		
		MapperClass mapperClass = new MapperClass();
		Element mapperClassElement = packageElement.element("mapperClass");
		mapperClass.setPackageName(mapperClassElement.attributeValue("packageName"));
		generatorConfig.setMapperClass(mapperClass);
		
		ServiceClass serviceClass = new ServiceClass();
		Element serviceClassElement = packageElement.element("serviceClass");
		serviceClass.setPackageName(serviceClassElement.attributeValue("packageName"));
		generatorConfig.setServiceClass(serviceClass);
		
		Element schemaElement = root.element("schema");
		String schemaName = schemaElement.attributeValue("name");
		if(StringUtils.isNotBlank(schemaName)) {
			schema.setName(schemaName);
		}
		List<Element> tableElements = schemaElement.elements("table");
		if(GeneratorCollectionUtils.isEmpty(tableElements)) {
			return ;
		}
		List<Table> tables = new LinkedList<>();
		for(Element element : tableElements) {
			Table table = new Table();
			table.setDbName(element.attributeValue("dbName"));
			String className = element.attributeValue("className");
			String exculdeUpdateColumns = element.attributeValue("exculdeUpdateColumns");
			String exculdeInsertColumns = element.attributeValue("exculdeInsertColumns");
			if(StringUtils.isBlank(className)) {
				table.setClassName(GeneratorStringUtils.underlineToCamel(table.getDbName()));
			}else {
				table.setClassName(className);
			}
			if(StringUtils.isNotBlank(exculdeUpdateColumns)) {
				table.setExculdeUpdateColumns(Arrays.asList(exculdeUpdateColumns.split(",")));
			}
			if(StringUtils.isNotBlank(exculdeInsertColumns)) {
				table.setExculdeInsertColumns(Arrays.asList(exculdeInsertColumns.split(",")));
			}
			table.setLowClassName(GeneratorStringUtils.lowStartWord(table.getClassName()));
			tables.add(table);
		}
		schema.setTables(tables);
	}
	
	public GeneratorConfig getGeneratorConfig() {
		return generatorConfig;
	}

	public Schema getSchema() {
		return schema;
	}

	public static ConfigParser getInstance() {
		return innerClass.INSTANCE;
	}
	
	private static class innerClass{
		private static final ConfigParser INSTANCE = new ConfigParser();
	}
}
