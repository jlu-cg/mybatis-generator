package ${generatorConfig.bean.packageName};

<#list importClass as aClass>  
${aClass}
</#list>

public class ${table.className}{
    <#list table.columns as column>
    private ${column.classDataType} ${column.beanColumnName};
    </#list>
    
    <#list table.columns as column>
    public ${column.classDataType} get${column.beanMethodColumnName}(){
        return ${column.beanColumnName};
    }
    public void set${column.beanMethodColumnName}(${column.classDataType} ${column.beanColumnName}){
        this.${column.beanColumnName} = ${column.beanColumnName};
    }
    </#list>
}