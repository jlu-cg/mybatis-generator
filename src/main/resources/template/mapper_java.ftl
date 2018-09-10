package ${generatorConfig.mapperClass.packageName};

import ${generatorConfig.bean.packageName}.${table.className};
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ${table.className}Mapper {

    public List<${table.className}> selectByCondition(@Param("condition") ${table.className} condition, @Param("orderBy") String orderBy);
    
    public List<${table.className}> selectPageByCondition(@Param("condition") ${table.className} condition);

    public long selectCountByCondition(@Param("condition") ${table.className} condition);

    public int insert(@Param("${table.lowClassName}") ${table.className} ${table.lowClassName});

    public int insertSelective(@Param("${table.lowClassName}") ${table.className} ${table.lowClassName});
    
    public int insertAndGetIdSelective(${table.className} ${table.lowClassName});

    public int deleteByCondition(@Param("condition") ${table.className} condition);

    public int update(@Param("${table.lowClassName}") ${table.className} record, @Param("condition") ${table.className} condition);

    public int updateSelective(@Param("${table.lowClassName}") ${table.className} record, @Param("condition") ${table.className} condition);
    
    public void batchInsert(@Param("list") List<${table.className}> records);
}