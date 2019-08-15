package com.zcd.accumulation.framework.spring.jdbc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * @description
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);

    public DynamicDataSource(DataSource dataSource, DataSource mallSlaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceAddr.DEFAULT, dataSource);
        targetDataSources.put(DataSourceAddr.MALL_SLAVE, mallSlaveDataSource);

        super.setDefaultTargetDataSource(dataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceAddr dataSourceAddr = DataSourceContext.getCurrentDataSource();
        log.info(dataSourceAddr.name());
        return dataSourceAddr;
    }
}
