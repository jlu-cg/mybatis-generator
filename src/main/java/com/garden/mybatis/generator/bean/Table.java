package com.garden.mybatis.generator.bean;

import java.util.List;

public class Table {
	private String dbName;
	private String className;
	private String lowClassName;
	private List<Column> columns;
	private List<String> exculdeUpdateColumns;
	private List<String> exculdeInsertColumns;
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getLowClassName() {
		return lowClassName;
	}
	public void setLowClassName(String lowClassName) {
		this.lowClassName = lowClassName;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public List<String> getExculdeUpdateColumns() {
		return exculdeUpdateColumns;
	}
	public void setExculdeUpdateColumns(List<String> exculdeUpdateColumns) {
		this.exculdeUpdateColumns = exculdeUpdateColumns;
	}
	public List<String> getExculdeInsertColumns() {
		return exculdeInsertColumns;
	}
	public void setExculdeInsertColumns(List<String> exculdeInsertColumns) {
		this.exculdeInsertColumns = exculdeInsertColumns;
	}
}
