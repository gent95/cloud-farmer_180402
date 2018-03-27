package com.jctl.cloud.utils.sqlServer;
import java.sql.*;

/**
 * Created by gent on 2017/5/25.
 */
public class ConnectionUtil {
    // 连接驱动
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    // 连接路径
    private static final String URL = "jdbc:sqlserver://61.149.204.178:1433;databaseName=data";
    // 用户名
    private static final String USERNAME = "sa";
    // 密码
    private static final String PASSWORD = "jctl!@#";

    //静态代码块
    static {
        try {
            // 加载驱动
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     * 获取数据库连接
     */
    public static Connection getConnection() {
        Connection conn = null;
        try{
            conn=DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    /*
     * 关闭数据库连接，注意关闭的顺序
     */
    public static void close(ResultSet rs, Statement statement, Connection conn) {
        if(rs!=null){
            try{
                rs.close();
                rs=null;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(statement!=null){
            try{
                statement.close();
                statement=null;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(conn!=null){
            try{
                conn.close();
                conn=null;
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
