package com.garden.mybatis.generator.bean;

public class GeneratorConfig {
	/**
	 * 数据库连接
	 */
	private JdbcType jdbcType;
	/**
	 * 实体bean
	 */
	private Bean bean;
	/**
	 * 映射的java查询文件
	 */
	private MapperClass mapperClass;
	/**
	 * mybatis的xml配置文件
	 */
	private MapperXml mapperXml;
	
	private ServiceClass serviceClass;
	public JdbcType getJdbcType() {
		return jdbcType;
	}
	public void setJdbcType(JdbcType jdbcType) {
		this.jdbcType = jdbcType;
	}
	public Bean getBean() {
		return bean;
	}
	public void setBean(Bean bean) {
		this.bean = bean;
	}
	public MapperClass getMapperClass() {
		return mapperClass;
	}
	public void setMapperClass(MapperClass mapperClass) {
		this.mapperClass = mapperClass;
	}
	public MapperXml getMapperXml() {
		return mapperXml;
	}
	public void setMapperXml(MapperXml mapperXml) {
		this.mapperXml = mapperXml;
	}
	public ServiceClass getServiceClass() {
		return serviceClass;
	}
	public void setServiceClass(ServiceClass serviceClass) {
		this.serviceClass = serviceClass;
	}
}
