package ${generatorConfig.serviceClass.packageName};

import ${generatorConfig.bean.packageName}.${table.className};
import ${generatorConfig.mapperClass.packageName}.${table.className}Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ${table.className}Service {

    @Autowired
    private ${table.className}Mapper ${table.lowClassName}Mapper;
    
    public List<${table.className}> selectByCondition(${table.className} condition, String orderBy){
        return ${table.lowClassName}Mapper.selectByCondition(condition, orderBy);
    }
    
    public List<${table.className}> selectPageByCondition(${table.className} condition){
        return ${table.lowClassName}Mapper.selectPageByCondition(condition);
    }

    public long selectCountByCondition(${table.className} condition){
        return ${table.lowClassName}Mapper.selectCountByCondition(condition);
    }

    public int insert(${table.className} ${table.lowClassName}){
        return ${table.lowClassName}Mapper.insert(${table.lowClassName});
    }

    public int insertSelective(${table.className} ${table.lowClassName}){
        return ${table.lowClassName}Mapper.insertSelective(${table.lowClassName});
    }

    public int deleteByCondition(${table.className} condition){
        return ${table.lowClassName}Mapper.deleteByCondition(condition);
    }

    public int update(${table.className} record, ${table.className} condition){
        return ${table.lowClassName}Mapper.update(record, condition);
    }

    public int updateSelective(${table.className} record, ${table.className} condition){
        return ${table.lowClassName}Mapper.updateSelective(record, condition);
    }
    
    public void batchInsert(List<${table.className}> records){
        ${table.lowClassName}Mapper.batchInsert(records);
    }
}