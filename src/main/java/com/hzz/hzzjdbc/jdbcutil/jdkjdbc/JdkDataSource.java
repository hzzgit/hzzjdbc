package com.hzz.hzzjdbc.jdbcutil.jdkjdbc;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.hzz.hzzjdbc.jdbcutil.config.DataSource.DefaultConnectionsqlliteSourve;
import com.hzz.hzzjdbc.jdbcutil.config.DataSource.DefaultConntionhzzSource;
import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.dbmain.Mysqldb;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class JdkDataSource {

     public static MysqlDao mysqldb=null;

    private static final String ORACLE = "oracleDataSource.properties";
    private static final String MYSQL = "DataSource.properties";


    public static void jdksqllite() {
        if (mysqldb == null) {
            new JdkDataSource().createdateSourcebysqllite();
        }
    }

    public static void jdkmysql() {
        if (mysqldb == null) {
            creasource();
        }
    }


    private static synchronized void creasource() {
        if (mysqldb == null) {
            new JdkDataSource().createdateSource();
        }
    }




    // 创建连接池,连接sqllite
    private  void createdateSourcebysqllite() {
            mysqldb= new Mysqldb(null,new DefaultConnectionsqlliteSourve("testHelper.db"),"sqllite连接");
    }





    // 创建连接池
    private  void createdateSource() {

        Properties properties = new Properties();
        try {
            // properties.load(config.class.getResourceAsStream("/oracleDataSource.properties"));//这个是读取linux的class路径
            // properties.load(config.class.getResourceAsStream("/oracleDataSource.properties"));//这是读取src路径下
            try {
                String realPath = this.getClass().getClassLoader().getResource(MYSQL).getFile();
                System.out.println("读取到配置文件的地址为:"+realPath);
                properties.load(new FileInputStream(realPath));// 这个是web项目用的
            } catch (Exception e) {
                try {
                    properties.load(new FileInputStream(MYSQL));// 这是直接读取项目下面的
                } catch (IOException ex) {
                    String dir = SysPath();
                    System.out.println("jar包根路径是"+dir);
                    properties.load(new FileInputStream(dir+ File.separator+MYSQL));// 这是直接读取项目下面的
                }
            }
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            String rawJdbcUrl = ((DruidDataSource) dataSource).getRawJdbcUrl();
            mysqldb= new Mysqldb(dataSource,new DefaultConntionhzzSource(dataSource,"jdkjdbc"),rawJdbcUrl);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String SysPath()
    {
        String path=this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        if(path.toUpperCase().indexOf(".JAR")!=-1){
            try{
                //截取".JAR第一次出现前的字符串"
                String StrPath=path.substring(0, path.toUpperCase().indexOf(".jar".toUpperCase()));
                //获取“.jar”包的上一层文件夹
                path=StrPath.substring(0,StrPath.lastIndexOf("/")+1);
            }
            catch(Exception e){
                return "出错了:"+e.toString();
            }
        }
        //第一个字符可能是斜杠，在Windows系统中去掉
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name");
        System.out.println( "os name" + osName);
        if(path != null && !"".equals(path)){
            if(path.substring(0,1).equals("\\") || path.substring(0,1).equals("/")){
                path = path.substring(1);
            }
        }
        return path.replace("file:","");
    }

    public static void main(String[] args) {
        String sysPath = new JdkDataSource().SysPath();
        sysPath= sysPath.substring(0,sysPath.length()-1);
        String property = System.getProperty("user.dir");
        System.out.println(sysPath);
        System.out.println(property);
        ConcurrentMap    concurrentMap  =new ConcurrentHashMap();
        concurrentMap.put("s",1);
        Object s = concurrentMap.get("s");
    }


}
