package com.zcd.accumulation.framework.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/***
 * @ClassName: MyBatisGeneratorRun
 * @Auther: yanzhidong
 * @Date: 2019/5/15 11:00
 * @version : V1.0
 * @Description:
 *
 */
public class MyBatisGeneratorRun {

    public static void main(String[] args) throws Exception {
        MyBatisGeneratorRun app = new MyBatisGeneratorRun();
        app.generator();
    }

    public void generator() throws Exception {
        Properties properties = new Properties();
        InputStream propertiesStream = this.getClass().getClassLoader().getResourceAsStream("mine.properties");
        properties.load(propertiesStream);

        String connectionURL = properties.getProperty("connectionURL");
        String userId = properties.getProperty("userId");
        String password = properties.getProperty("password");
        String targetProject = properties.getProperty("targetProject");
        String daoPackage = properties.getProperty("daoPackage");
        String domainPackage = properties.getProperty("domainPackage");
        String mapperPath = properties.getProperty("mapperPath");
        String tables = properties.getProperty("tables");

        String[] tableArry = tables.split(",");

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("mybatis-generator.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(resourceAsStream);

        Context context = config.getContexts().get(0);

        context.getJdbcConnectionConfiguration().setConnectionURL(connectionURL);
        context.getJdbcConnectionConfiguration().setUserId(userId);
        context.getJdbcConnectionConfiguration().setPassword(password);

        context.getJavaModelGeneratorConfiguration().setTargetPackage(domainPackage);
        context.getJavaModelGeneratorConfiguration().setTargetProject(targetProject + "/src/main/java");

        context.getJavaClientGeneratorConfiguration().setTargetPackage(daoPackage);
        context.getJavaClientGeneratorConfiguration().setTargetProject(targetProject + "/src/main/java");

        context.getSqlMapGeneratorConfiguration().setTargetPackage(mapperPath);
        context.getSqlMapGeneratorConfiguration().setTargetProject(targetProject + "/src/main/resources");


        for (String tableName : tableArry) {

            context.getTableConfigurations().add(getTableConfigurationt(tableName, context));
        }


        context.getTableConfigurations().remove(0);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

        for (String warning : warnings) {
            System.out.println(warning);
        }
    }


    private TableConfiguration getTableConfigurationt(String tableName, Context context) {
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        tableConfiguration.setDomainObjectName(tableName2domainObjectName(tableName));
        tableConfiguration.setCountByExampleStatementEnabled(false);
        tableConfiguration.setUpdateByExampleStatementEnabled(false);
        tableConfiguration.setDeleteByExampleStatementEnabled(false);
        tableConfiguration.setSelectByExampleStatementEnabled(false);
        tableConfiguration.setSelectByExampleQueryId("false");
        return tableConfiguration;
    }

    private static String tableName2domainObjectName(String tableName) {
        int len = tableName.length();
        StringBuilder sb = new StringBuilder(len);
        sb.append(Character.toUpperCase(tableName.charAt(0)));
        for (int i = 1; i < len; i++) {
            char c = tableName.charAt(i);
            if (c == '_') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(tableName.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();

    }


}