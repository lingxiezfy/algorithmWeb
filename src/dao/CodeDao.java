package dao;

import util.DbUtil;
import vo.C_code;
import vo.Java_code;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CodeDao {
    /**
     * * * 本dao为c_code和java_code提供方法，为区分两者，c_code 代号为1，java_code 代号为2
     * * * 一个问题同类型的code只能存在一个
     */

    /**
     * 判断某个问题是否存在code ，根据存在情况返回不同的值进行标识
     * @param q_id 需要判断的问题id
     * @return  0 : 不存在任何一种code; 1 : 存在c_code; 2 : 存在java_code; 3 : 两种都存在。
     */
    public int isExist(int q_id){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "CALL is_exist_code( ? );";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,q_id);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    return rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return 0;
    }

    /**
     * 添加c_code
     * @param c_code C_code对象
     * @return 成功返回true，失败返回false
     */
    public boolean add(C_code c_code){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "INSERT INTO t_c_code(q_id,c_code_content) "+
                    " VALUES( ? , ? )";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,c_code.getQ_id());
                pstmt.setString(2,c_code.getC_code_content());
                int rowCount = pstmt.executeUpdate();
                return rowCount > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt);
            }
        }
        return false;
    }

    /**
     * 增加Java_code
     * @param java_code Java_code对象
     * @return 成功返回true,失败返回false
     */
    public boolean add(Java_code java_code){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "INSERT INTO t_java_code(q_id,java_code) "+
                    " VALUES( ? , ? )";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,java_code.getQ_id());
                pstmt.setString(2,java_code.getJava_code());
                int rowCount = pstmt.executeUpdate();
                return rowCount > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt);
            }
        }
        return false;
    }

    /**
     * 通过id删除一个_code
     * @param c_code_id c_code的id
     * @return 成功返回 true，失败返回false
     */
    public boolean deleteC_codeById(int c_code_id){
        Connection conn = DbUtil.getConnection();
        if(conn != null ){
            PreparedStatement pstmt = null;
            String sql = "DELETE FROM t_c_code WHERE c_code_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,c_code_id);
                int rowCount = pstmt.executeUpdate();
                return rowCount > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt);
            }
        }
        return false;
    }

    /**
     * 通过id删除一个Java_code
     * @param java_code_id java_code的id
     * @return 成功返回 true，失败返回false
     */
    public boolean deleteJava_codeById(int java_code_id){
        Connection conn = DbUtil.getConnection();
        if(conn != null ){
            PreparedStatement pstmt = null;
            String sql = "DELETE FROM t_java_code WHERE java_code_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,java_code_id);
                int rowCount = pstmt.executeUpdate();
                return rowCount > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt);
            }
        }
        return false;
    }

    /**
     * 保存修改之后的C_code对象
     * @param c_code 修改之后的示例对象
     * @return 成功返回true，失败返回false
     */
    public boolean save(C_code c_code){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "UPDATE t_c_code SET c_code_content = ? "+
                    " WHERE c_code_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,c_code.getC_code_content());
                pstmt.setInt(2,c_code.getC_code_id());
                int rowCount = pstmt.executeUpdate();
                return rowCount > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt);
            }
        }
        return false;
    }

    /**
     * 保存修改之后的Java_code对象
     * @param java_code 修改之后的示例对象
     * @return 成功返回true，失败返回false
     */
    public boolean save(Java_code java_code){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "UPDATE t_java_code SET java_code = ? "+
                    " WHERE java_code_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,java_code.getJava_code());
                pstmt.setInt(2,java_code.getJava_code_id());
                int rowCount = pstmt.executeUpdate();
                return rowCount > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt);
            }
        }
        return false;
    }

    /**
     * 通过问题的id获取其C_code
     * @param q_id 问题的id
     * @return 成功返回C_code对象，失败返回null
     */
    public C_code getC_codeByQuestionId(int q_id){
        C_code c_code = null;
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_c_code WHERE q_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,q_id);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    c_code = new C_code();
                    c_code.setC_code_id(rs.getInt("c_code_id"));
                    c_code.setC_code_content(rs.getString("c_code_content"));
                    c_code.setQ_id(rs.getInt("q_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return c_code;
    }

    /**
     * 通过问题的id获取其Java_code
     * @param q_id 问题的id
     * @return Java_code，失败返回null
     */
    public Java_code getJava_codeByQuestionId(int q_id){
        Java_code java_code = null;
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_java_code WHERE q_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,q_id);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    java_code = new Java_code();
                    java_code.setJava_code_id(rs.getInt("java_code_id"));
                    java_code.setJava_code(rs.getString("java_code"));
                    java_code.setQ_id(rs.getInt("q_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return java_code;
    }
    /**
     * 通过id获取其C_code
     * @param c_code_id c_code的id
     * @return 成功返回C_code对象，失败返回null
     */
    public C_code getC_codeById(int c_code_id){
        C_code c_code = null;
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_c_code WHERE c_code_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,c_code_id);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    c_code = new C_code();
                    c_code.setC_code_id(rs.getInt("c_code_id"));
                    c_code.setC_code_content(rs.getString("c_code_content"));
                    c_code.setQ_id(rs.getInt("q_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return c_code;
    }

    /**
     * 通过id获取其Java_code
     * @param java_code_id java_code的id
     * @return Java_code，失败返回null
     */
    public Java_code getJava_codeById(int java_code_id){
        Java_code java_code = null;
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_java_code WHERE java_code_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,java_code_id);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    java_code = new Java_code();
                    java_code.setJava_code_id(rs.getInt("java_code_id"));
                    java_code.setJava_code(rs.getString("java_code"));
                    java_code.setQ_id(rs.getInt("q_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return java_code;
    }
}
