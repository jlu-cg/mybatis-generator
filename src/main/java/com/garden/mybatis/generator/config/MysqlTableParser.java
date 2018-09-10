package com.garden.mybatis.generator.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.garden.mybatis.generator.bean.Column;
import com.garden.mybatis.generator.bean.JdbcType;
import com.garden.mybatis.generator.bean.Schema;
import com.garden.mybatis.generator.bean.Table;
import com.garden.mybatis.generator.db.DbManager;
import com.garden.mybatis.generator.enums.TrueFalseEnums;
import com.garden.mybatis.generator.util.GeneratorStringUtils;

/**
 * 解析表数据
 * 
 * @author chengguang
 *
 */
public class MysqlTableParser implements ITableParser{

	private static final String MYSQL_COLUMN_SQL = "select * from information_schema.columns where table_name = '%s' %s ";
	private static final String MYSQL_COLUMN_SCHEMA_SQL = " and table_schema = '%s' ";
	private static final Map<String, String> COLUMN_TRANSFER = new HashMap<>();
	private static final Map<String, String> CLASS_TRANSFER = new HashMap<>();
	static {
		COLUMN_TRANSFER.put("int", "INTEGER");
		COLUMN_TRANSFER.put("bigint", "BIGINT");
		COLUMN_TRANSFER.put("varchar", "VARCHAR");
		COLUMN_TRANSFER.put("datetime", "TIMESTAMP");
		COLUMN_TRANSFER.put("decimal", "DECIMAL");
		COLUMN_TRANSFER.put("text", "VARCHAR");
		COLUMN_TRANSFER.put("char", "CHAR");
		COLUMN_TRANSFER.put("timestamp", "TIMESTAMP");
		
		CLASS_TRANSFER.put("int", "Integer");
		CLASS_TRANSFER.put("bigint", "Long");
		CLASS_TRANSFER.put("varchar", "String");
		CLASS_TRANSFER.put("datetime", "Date");
		CLASS_TRANSFER.put("decimal", "BigDecimal");
		CLASS_TRANSFER.put("text", "String");
		CLASS_TRANSFER.put("char", "String");
		CLASS_TRANSFER.put("timestamp", "Date");
	}

	private MysqlTableParser() {
	}

	public List<Column> dbColumns(Schema schema, Table table, JdbcType jdbcType) {
		Map<String, Boolean> colMap = new HashMap<>();
		String schemaSql = "";
		if (StringUtils.isNotBlank(schema.getName())) {
			schemaSql = String.format(MYSQL_COLUMN_SCHEMA_SQL, schema.getName());
		}
		String sql = String.format(MYSQL_COLUMN_SQL, table.getDbName(), schemaSql);
		Connection connection = DbManager.getInstance().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Column column = null;
		List<Column> columns = new LinkedList<>();
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			String dataType = null;
			while(rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				if(colMap.containsKey(columnName)) {
					continue;
				}
				colMap.put(columnName, true);
				column = new Column();
				column.setDbColumnName(columnName);
				dataType = rs.getString("DATA_TYPE");
				column.setDataType(COLUMN_TRANSFER.get(dataType.toLowerCase()));
				column.setClassDataType(CLASS_TRANSFER.get(dataType.toLowerCase()));
				column.setBeanColumnName(GeneratorStringUtils.underlineToCamelLow(column.getDbColumnName()));
				column.setBeanMethodColumnName(GeneratorStringUtils.underlineToCamel(column.getDbColumnName()));
				if(table.getExculdeUpdateColumns() != null && table.getExculdeUpdateColumns().contains(columnName.toUpperCase())) {
					column.setExcludeUpdate(TrueFalseEnums.TRUE.getCode());
				}else {
					column.setExcludeUpdate(TrueFalseEnums.FALSE.getCode());
				}
				if(table.getExculdeInsertColumns() != null && table.getExculdeInsertColumns().contains(columnName.toUpperCase())) {
					column.setExcludeInsert(TrueFalseEnums.TRUE.getCode());
				}else {
					column.setExcludeInsert(TrueFalseEnums.FALSE.getCode());
				}
				columns.add(column);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
//				if (connection != null) {
//					connection.close();
//				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
//		DbManager.getInstance().close();
		return columns;
	}

	public static MysqlTableParser getInstance() {
		return innerClass.INSTANCE;
	}

	private static class innerClass {
		private static final MysqlTableParser INSTANCE = new MysqlTableParser();
	}
}
