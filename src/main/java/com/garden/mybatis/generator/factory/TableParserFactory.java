package com.garden.mybatis.generator.factory;

import com.garden.mybatis.generator.config.ITableParser;
import com.garden.mybatis.generator.config.MysqlTableParser;

public class TableParserFactory {
	
	public static final String MYSQL_TYPE = "mysql";
	
	private TableParserFactory() {}
	
	public ITableParser create(String dbType) {
		if(MYSQL_TYPE.equalsIgnoreCase(dbType)) {
			return MysqlTableParser.getInstance();
		}
		return null;
	} 

	public static TableParserFactory getInstance() {
		return innerClass.INSTANCE;
	}

	private static class innerClass {
		private static final TableParserFactory INSTANCE = new TableParserFactory();
	}
}
