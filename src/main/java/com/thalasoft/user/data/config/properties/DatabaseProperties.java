package com.thalasoft.user.data.config.properties;

public interface DatabaseProperties {

    public String getHibernateDialect();

    public String getHibernateDriverClassName();
    
    public String getDataSourceUrl();
    
    public String getDataSourceUsername();
    
    public String getDataSourcePassword();
    
    public String isShowSql();

    public String getDdlAuto();

}
