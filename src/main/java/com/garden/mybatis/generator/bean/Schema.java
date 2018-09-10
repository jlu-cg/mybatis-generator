package com.garden.mybatis.generator.bean;

import java.util.List;

public class Schema {
	private String name;
	private List<Table> tables;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
}
