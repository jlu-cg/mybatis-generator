package com.garden.mybatis.generator.enums;

public enum TrueFalseEnums {

	TRUE("1", "是"),
	FALSE("2", "否");
	private final String code;
	private final String description;
	
	private TrueFalseEnums(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
