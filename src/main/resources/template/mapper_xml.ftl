<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${generatorConfig.mapperClass.packageName}.${table.className}Mapper">
  <resultMap id="BaseResultMap" type="${generatorConfig.bean.packageName}.${table.className}">
  <#list table.columns as column>  
    <result column="${column.dbColumnName}" jdbcType="${column.dataType}" property="${column.beanColumnName}" />
  </#list>
  </resultMap>
  
  <sql id="Where_Clause">
    <trim prefix="WHERE" prefixOverrides="AND|OR">
      <#list table.columns as column>  
      <if test="condition.${column.beanColumnName} != null and condition.${column.beanColumnName} != ''">
        AND ${column.dbColumnName}=${r'#{'}condition.${column.beanColumnName}${r'}'}
      </if>
      </#list>
    </trim>
  </sql>
  
  <sql id="Base_Column_List">
    ${baseCols}
  </sql>
  
  <select id="selectByCondition" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List"/>
    from ${table.dbName}
    <include refid="Where_Clause"/>
    <if test="orderBy != null and orderBy != ''">
    order by ${r'${orderBy}'}
    </if>
  </select>
  
  <select id="selectPageByCondition" resultMap="BaseResultMap">
    SELECT 
    <include refid="Base_Column_List"/>
    FROM ${table.dbName}
    <include refid="Where_Clause"/>
    <if test="orderBy != null and orderBy != ''">
      ORDER BY ${r'${orderBy}'}
    </if>
    LIMIT ${r'#{limit}'} OFFSET ${r'#{offset}'}
  </select>
  
  <select id="selectCountByCondition" resultType="int">
    SELECT COUNT(*) FROM ${table.dbName}
    <include refid="Where_Clause"/>
  </select>
  
  <insert id="insert">
    INSERT INTO ${table.dbName}
    (${colStr})
    VALUES
    <trim prefix="(" suffixOverrides="," suffix=")">
    <#list table.columns as column>
      <#if column.excludeInsert = "2">
      ${r'#{'}${table.lowClassName}.${column.beanColumnName}${r'}'},
      </#if>
    </#list>
    </trim>
  </insert>
  
  <insert id="batchInsert">
    INSERT INTO ${table.dbName}
    (${colStr})
    VALUES
    <foreach item="item" collection="list" separator="," index="">
      <trim prefix="(" suffixOverrides="," suffix=")">
        <#list table.columns as column>
          <#if column.excludeInsert = "2">
          ${r'#{'}${table.lowClassName}.${column.beanColumnName}${r'}'},
          </#if>
        </#list>
      </trim>
    </foreach>
  </insert>
  
  <insert id="insertSelective">
    INSERT INTO ${table.dbName}
    <trim prefix="(" suffixOverrides="," suffix=")">
    <#list table.columns as column>
      <#if column.excludeInsert = "2">
      <if test="${table.lowClassName}.${column.beanColumnName} != null and ${table.lowClassName}.${column.beanColumnName} != ''">
      ${column.dbColumnName},
      </if>
      </#if>
    </#list>
    </trim>
    VALUES
    <trim prefix="(" suffixOverrides="," suffix=")">
    <#list table.columns as column>  
      <#if column.excludeInsert = "2">
      <if test="${table.lowClassName}.${column.beanColumnName} != null and ${table.lowClassName}.${column.beanColumnName} != ''">
      ${r'#{'}${table.lowClassName}.${column.beanColumnName}${r'}'},
      </if>
      </#if>
    </#list>
    </trim>
  </insert>
  
  <insert id="insertAndGetIdSelective" useGeneratedKeys="true" keyProperty="id" parameterType="${generatorConfig.bean.packageName}.${table.className}">
    INSERT INTO ${table.dbName}
    <trim prefix="(" suffixOverrides="," suffix=")">
    <#list table.columns as column>
      <#if column.excludeInsert = "2">
      <if test="${column.beanColumnName} != null and ${column.beanColumnName} != ''">
      ${column.dbColumnName},
      </if>
      </#if>
    </#list>
    </trim>
    VALUES
    <trim prefix="(" suffixOverrides="," suffix=")">
    <#list table.columns as column>  
      <#if column.excludeInsert = "2">
      <if test="${column.beanColumnName} != null and ${column.beanColumnName} != ''">
      ${r'#{'}${column.beanColumnName}${r'}'},
      </if>
      </#if>
    </#list>
    </trim>
  </insert>
  
  <delete id="deleteByCondition">
    DELETE FROM ${table.dbName}
    <include refid="Where_Clause" />
  </delete>
  
  <update id="update">
    update CONFIG
    <trim prefix="SET" suffixOverrides=",">
    <#list table.columns as column>
      <#if column.excludeUpdate = "2">
      ${column.dbColumnName}=${r'#{'}${table.lowClassName}.${column.beanColumnName}${r'}'},
      </#if>
    </#list>
    </trim>
    <include refid="Where_Clause" />
  </update>
  
  <update id="updateSelective">
    update CONFIG
    <trim prefix="SET" suffixOverrides=",">
    <#list table.columns as column>
      <#if column.excludeUpdate = "2">
      <if test="${table.lowClassName}.${column.beanColumnName} != null and ${table.lowClassName}.${column.beanColumnName} != ''">
        ${column.dbColumnName}=${r'#{'}${table.lowClassName}.${column.beanColumnName}${r'}'},
      </if>
      </#if>
    </#list>
    </trim>
    <include refid="Where_Clause" />
  </update>
</mapper>