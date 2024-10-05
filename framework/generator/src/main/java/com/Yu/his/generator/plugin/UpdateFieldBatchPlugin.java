package com.Yu.his.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

public class UpdateFieldBatchPlugin extends PluginAdapter {

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
//        importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
//
//        Method method = new Method("updateFieldBatch");
//        // 1.设置方法可见性
//        method.setVisibility(JavaVisibility.PUBLIC);
//        method.setAbstract(true);
//        // 2.设置返回值类型
//        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getIntInstance();
//
//        method.setReturnType(returnType);
//
//        // 4.设置参数列表
//        FullyQualifiedJavaType paramType = new FullyQualifiedJavaType("java.util.List<com.bage.common.mybatishelp.MyBatisWrapper>");
//        method.addParameter(new Parameter(paramType, "list", "@Param(\"list\")"));
////        method.addParameter(new Parameter(paramType, "example"));
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
        String node = " <update id=\"updateFieldBatch\" parameterType=\"java.util.List\">\n" +
                "           <foreach collection=\"list\" index=\"index\" item=\"example\" separator=\";\">\n" +
                "               update " + tableName + " \n" +
                "                   set \n" +
                "               <trim prefixOverrides=\",\" suffixOverrides=\",\">\n" +
                "                   ${example.updateSql} \n" +
                "               </trim>\n" +
                "               <if test=\"example != null\">\n" +
                "                   <include refid=\"Update_By_Example_Where_Clause\" />\n" +
                "               </if>" +
                "           </foreach>\n" +
                "  </update>";
        // 将条件替换为自己的逻辑
        return new TextElement(node);
    }
}