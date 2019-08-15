package com.zcd.accumulation.framework.spring.jdbc;

/**
 * @description
 */
public class DataSourceContext {
    private static final ThreadLocal<DataSourceAddr> contextHolder = new ThreadLocal<DataSourceAddr>() {
        @Override
        public DataSourceAddr initialValue () {
            return DataSourceAddr.DEFAULT;
        }
    };

    public static void setCurrentDataSource(DataSourceAddr dataSource) {
        contextHolder.set(dataSource);
    }

    public static DataSourceAddr getCurrentDataSource() {
        return contextHolder.get();
    }

    public static void removeDataSource() {
        contextHolder.remove();
    }
}
