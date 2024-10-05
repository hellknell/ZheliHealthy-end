package com.Yu.his.generator.help;

public class DbField {
    private String dbName;
    private String propertyName;
    private String jdbcType;
    private String javaType;

    public DbField(String dbName, String propertyName, String jdbcType, String javaType) {
        this.dbName = dbName;
        this.propertyName = propertyName;
        this.jdbcType = jdbcType;
        this.javaType = javaType;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
}