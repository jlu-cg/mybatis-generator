package com.garden.mybatis.generator.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.garden.mybatis.generator.bean.Column;
import com.garden.mybatis.generator.bean.GeneratorConfig;
import com.garden.mybatis.generator.bean.Schema;
import com.garden.mybatis.generator.bean.Table;
import com.garden.mybatis.generator.config.ConfigParser;
import com.garden.mybatis.generator.config.ITableParser;
import com.garden.mybatis.generator.config.MysqlTableParser;
import com.garden.mybatis.generator.factory.TableParserFactory;
import com.garden.mybatis.generator.util.GeneratorCollectionUtils;
import com.garden.mybatis.generator.util.GeneratorFileUtils;
import com.garden.mybatis.generator.util.GeneratorStringUtils;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 生成相关文件
 * @author chengguang
 * @date 2018年3月5日 下午4:32:52
 */
public class GenerateFile {
	
	public static final String ROOT_PATH = "src/main/java/";

	private Configuration configuration;
	private GeneratorConfig generatorConfig;

	private GenerateFile() {
		generatorConfig = ConfigParser.getInstance().getGeneratorConfig();
		configuration = new Configuration(Configuration.VERSION_2_3_23);
		try {
			configuration.setClassForTemplateLoading(GenerateFile.class, "/");
			FileTemplateLoader templateLoader = new FileTemplateLoader(new File("src/main/resources/template"));
			configuration.setTemplateLoader(templateLoader);
			configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_23));
			configuration.setDefaultEncoding("UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成mybatis相关文件
	 * @date 2018年3月26日 上午11:24:52
	 * @return
	 */
	public String generate() {
		Schema schema = ConfigParser.getInstance().getSchema();
		if(GeneratorCollectionUtils.isEmpty(schema.getTables())) {
			return "没有要生成的表";
		}
		ITableParser tableParser = TableParserFactory.getInstance().create(generatorConfig.getJdbcType().getDbType());
		if(tableParser == null) {
			return String.format("暂时不支持%s这种数据库", 
					generatorConfig.getJdbcType().getDbType() == null ? "" : generatorConfig.getJdbcType().getDbType());
		}
		for(Table table : schema.getTables()) {
			List<Column> columns = MysqlTableParser.getInstance().dbColumns(schema, table, generatorConfig.getJdbcType());
			if(GeneratorCollectionUtils.isEmpty(columns)) {
				continue;
			}
			table.setColumns(columns);
			generateBean(table);
			generateXml(table);
			generateMapper(table);
			generateService(table);
		}
		return "生成成功";
	}
	
	/**
	 * 生成java bean
	 * @date 2018年3月31日 下午9:28:23
	 * @param table
	 */
	private void generateBean(Table table) {
		Writer writer = null;
		try {
			Template template = configuration.getTemplate("mapper_bean.ftl");
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("generatorConfig", generatorConfig);
			paramMap.put("table", table);
			List<String> importClass = new LinkedList<>();
			for(Column column : table.getColumns()) {
				if("DECIMAL".equals(column.getDataType()) && !importClass.contains("import java.math.BigDecimal;")){
					importClass.add("import java.math.BigDecimal;");
				}else if("TIMESTAMP".equals(column.getDataType()) && !importClass.contains("import java.util.Date;")){
					importClass.add("import java.util.Date;");
				}
			}
			paramMap.put("importClass", importClass);
			
			GeneratorFileUtils.createFolder(ROOT_PATH + generatorConfig.getBean().getPackageName());
			String filePath = GeneratorStringUtils.packageFilePath(ROOT_PATH + generatorConfig.getBean().getPackageName(), table.getClassName() + ".java");
			writer = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
			template.process(paramMap, writer); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 生成xml文件
	 * @date 2018年3月26日 上午11:07:42
	 * @param schema
	 * @param table
	 */
	private void generateXml(Table table) {
		Writer writer = null;
		try {
			Template template = configuration.getTemplate("mapper_xml.ftl");
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("generatorConfig", generatorConfig);
			paramMap.put("table", table);
			StringBuilder cols = new StringBuilder();
			StringBuilder baseCols = new StringBuilder();
			for(Column column : table.getColumns()) {
				if(table.getExculdeInsertColumns() == null || !table.getExculdeInsertColumns().contains(column.getDbColumnName())){
					cols.append(column.getDbColumnName()).append(",");
				}
				baseCols.append(column.getDbColumnName()).append(",");
			}
			paramMap.put("colStr", cols.substring(0, cols.length() - 1));
			paramMap.put("baseCols", baseCols.substring(0, baseCols.length() - 1));
			
			GeneratorFileUtils.createFolder(ROOT_PATH + generatorConfig.getMapperXml().getPackageName());
			String filePath = GeneratorStringUtils.packageFilePath(ROOT_PATH + generatorConfig.getMapperXml().getPackageName(), table.getClassName() + "Mapper.xml");
			writer = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
			template.process(paramMap, writer); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 生成数据库查询类
	 * @date 2018年5月1日 下午8:46:52
	 * @param table
	 */
	private void generateMapper(Table table) {
		Writer writer = null;
		try {
			Template template = configuration.getTemplate("mapper_java.ftl");
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("generatorConfig", generatorConfig);
			paramMap.put("table", table);
			
			GeneratorFileUtils.createFolder(ROOT_PATH + generatorConfig.getMapperClass().getPackageName());
			String filePath = GeneratorStringUtils.packageFilePath(ROOT_PATH + generatorConfig.getMapperClass().getPackageName(), table.getClassName() + "Mapper.java");
			writer = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
			template.process(paramMap, writer); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 生成业务类
	 * @date 2018年5月1日 下午8:46:34
	 * @param table
	 */
	private void generateService(Table table) {
		Writer writer = null;
		try {
			Template template = configuration.getTemplate("service_java.ftl");
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("generatorConfig", generatorConfig);
			paramMap.put("table", table);
			
			GeneratorFileUtils.createFolder(ROOT_PATH + generatorConfig.getServiceClass().getPackageName());
			String filePath = GeneratorStringUtils.packageFilePath(ROOT_PATH + generatorConfig.getServiceClass().getPackageName(), table.getClassName() + "Service.java");
			writer = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
			template.process(paramMap, writer); 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static GenerateFile getInstance() {
		return innerClass.INSTANCE;
	}

	public static class innerClass {
		private static final GenerateFile INSTANCE = new GenerateFile();
	}
}
