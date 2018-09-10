package com.garden.mybatis.generator.bean;

public class Column {
	private String dbColumnName;
	private String beanColumnName;
	private String beanMethodColumnName;
	private String dataType;
	private String classDataType;
	private String excludeUpdate;
	private String excludeInsert;
	public String getDbColumnName() {
		return dbColumnName;
	}
	public void setDbColumnName(String dbColumnName) {
		this.dbColumnName = dbColumnName;
	}
	public String getBeanColumnName() {
		return beanColumnName;
	}
	public void setBeanColumnName(String beanColumnName) {
		this.beanColumnName = beanColumnName;
	}
	public String getBeanMethodColumnName() {
		return beanMethodColumnName;
	}
	public void setBeanMethodColumnName(String beanMethodColumnName) {
		this.beanMethodColumnName = beanMethodColumnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getClassDataType() {
		return classDataType;
	}
	public void setClassDataType(String classDataType) {
		this.classDataType = classDataType;
	}
	public String getExcludeUpdate() {
		return excludeUpdate;
	}
	public void setExcludeUpdate(String excludeUpdate) {
		this.excludeUpdate = excludeUpdate;
	}
	public String getExcludeInsert() {
		return excludeInsert;
	}
	public void setExcludeInsert(String excludeInsert) {
		this.excludeInsert = excludeInsert;
	}
}
