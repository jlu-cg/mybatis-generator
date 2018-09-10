package com.garden.mybatis.generator.config;

import java.util.List;

import com.garden.mybatis.generator.bean.Column;
import com.garden.mybatis.generator.bean.JdbcType;
import com.garden.mybatis.generator.bean.Schema;
import com.garden.mybatis.generator.bean.Table;

public interface ITableParser {

	/**
	 * 查询表结构列描述
	 * @date 2018年3月26日 上午11:15:31
	 * @param schema
	 * @param table
	 * @param jdbcType
	 * @return
	 */
	public List<Column> dbColumns(Schema schema, Table table, JdbcType jdbcType);
}
