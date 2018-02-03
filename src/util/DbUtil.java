package util;

import java.sql.*;

public class DbUtil {
    /**
     * 获取mysql数据库连接
     * @return Connection conn
     */
    public static Connection getConnection(){
        String driverClassName = "com.mysql.jdbc.Driver";
        /**
         * 数据库路径，端口：3306 ，数据库名：algorithm，字符编码：utf8
         */
        String url = "jdbc:mysql://localhost:3306/algorithm?useUnicode=true&characterEncoding=utf8";
        /**
         * user 数据库连接用户名，使用时修改成自己本机数据库的用户名
         */
        String user = "root";
        /**
         * password 数据库连接密码，使用时修改成自己本机数据库的密码
         */
        String password = "123";
        Connection conn = null;
        try{
            Class.forName(driverClassName).newInstance();
            conn = DriverManager.getConnection(url,user,password);
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 释放单个连接
     * @param conn Connection对象
     */
    public static void freeAll(Connection conn){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放使用PreparedStatement，但未使用结果集的数据库连接
     * @param conn Connection 对象
     * @param pstmt PreparedStatement 对象
     */
    public static void freeAll(Connection conn,PreparedStatement pstmt){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(pstmt != null){
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放使用PreparedStatement，并使用ResultSet结果集对象的数据库连接
     * @param conn Connection 对象
     * @param pstmt PreparedStatement 对象
     * @param rs ResultSet 结果集
     */
    public static void freeAll(Connection conn, PreparedStatement pstmt,ResultSet rs){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(pstmt != null){
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放使用Statement，但未使用结果集的数据库连接
     * @param conn Connection 对象
     * @param stmt Statement 对象
     */
    public static void freeAll(Connection conn,Statement stmt){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放使用Statement，并使用ResultSet结果集对象的数据库连接
     * @param conn Connection 对象
     * @param stmt PreparedStatement 对象
     * @param rs ResultSet 结果集
     */
    public static void freeAll(Connection conn, Statement stmt,ResultSet rs){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
