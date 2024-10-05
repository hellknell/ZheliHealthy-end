package com.Yu.his.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

public class TopOnePlugin extends PluginAdapter {

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
//        Method method = new Method("topOne");
//        // 1.设置方法可见性
//        method.setVisibility(JavaVisibility.PUBLIC);
//        method.setAbstract(true);
//        // 2.设置返回值类型
//        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
//
//        method.setReturnType(returnType);
//
//        // 4.设置参数列表
//        FullyQualifiedJavaType paramType = new FullyQualifiedJavaType("com.bage.mybatis.help.MyBatisWrapper");
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
        String selectNode = "  <select id=\"topOne\" parameterType=\"com.Yu.his.generator.help.MyBatisWrapper\" resultMap=\"BaseResultMap\">\n" +
                "    select\n" +
                "    <if test=\"selectBuilder != null\">\n" +
                "      <trim prefixOverrides=\",\" suffixOverrides=\",\">\n" +
                "         ${selectBuilder} \n" +
                "      </trim>\n" +
                "    </if>\n" +
                "    from " + tableName + "\n" +
                "    <if test=\"_parameter != null\">\n" +
                "      <include refid=\"Example_Where_Clause\" />\n" +
                "    </if>\n" +
                "    <if test=\"orderByClause != null\">\n" +
                "      order by ${orderByClause}\n" +
                "    </if>\n" +
                "    limit 1\n" +
                "  </select>\n";
        // 将条件替换为自己的逻辑
        return new TextElement(selectNode);
    }
}