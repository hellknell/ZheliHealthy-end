package com.Yu.his.generator.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/7/14 1:21
 */
public class DeleteField extends PluginAdapter {
    @Override
    public boolean validate(List<String> list) {
        return true;
    }
    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        XmlElement rootElement = document.getRootElement();
        rootElement.addElement(replaceCondition(introspectedTable.getFullyQualifiedTable().getIntrospectedTableName()));
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }
    private TextElement replaceCondition(String tableName) {
        String node = "<delete id=\"deleteField\" parameterType=\"com.Yu.his.generator.help.MyBatisWrapper\">\n" +
                "    delete from " + tableName + " \n" +
                "      <include refid=\"Update_By_Example_Where_Clause\" />\n" +
                "  </delete>";
        // 将条件替换为自己的逻辑
        return new TextElement(node);
    }
}
