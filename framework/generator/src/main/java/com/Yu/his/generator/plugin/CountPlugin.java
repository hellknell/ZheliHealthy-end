package com.Yu.his.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

public class CountPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 修改Mapper类
     */
//    @Override
//    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
//        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
//        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
//
//        Method method = new Method("count");
//        // 1.设置方法可见性
//        method.setVisibility(JavaVisibility.PUBLIC);
//        method.setAbstract(true);
//        // 2.设置返回值类型
//        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("java.lang.Long");
//
//        method.setReturnType(returnType);
//
//        // 4.设置参数列表
//        FullyQualifiedJavaType paramType = new FullyQualifiedJavaType("com.bage.generator.mybatishelp.MyBatisWrapper");
////        method.addParameter(new Parameter(paramType, "example", "@Param(\"example\")"));
//        method.addParameter(new Parameter(paramType, "example"));
//        importedTypes.add(paramType);
//
//        // 设置需要导入的类
//        interfaze.addImportedTypes(importedTypes);
//        interfaze.addMethod(method);
//        return super.clientGenerated(interfaze, introspectedTable);
//    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement rootElement = document.getRootElement();
        rootElement.addElement(replaceCondition(introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()));
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    private TextElement replaceCondition(String tableName) {
        String node = "<select id=\"count\" parameterType=\"com.Yu.his.generator.help.MyBatisWrapper\" resultType=\"java.lang.Integer\">\n" +
                "        <include refid=\"countSql\"/>\n" +
                "    </select>\n" +
                "    <sql id=\"countSql\">\n" +
                "        select count(*) total_count from " + tableName + "\n" +
                "        <if test=\"_parameter != null\">\n" +
                "            <include refid=\"Example_Where_Clause\"/>\n" +
                "        </if>\n" +
                "    </sql>";
        // 将条件替换为自己的逻辑
        return new TextElement(node);
    }
}