package dao;

import util.DbUtil;
import vo.Source;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SourceDao {

    /**
     * 判断来源是否存在
     * @param s_name 需要判断的来源名
     * @return 存在返回true，不存在返回false
     */
    public boolean isExist(String s_name){
        Connection conn = DbUtil.getConnection();
        if(conn != null) {
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT COUNT(*) FROM t_source WHERE s_name = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,s_name);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    return rs.getInt(1) > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return false;
    }

    /**
     * 增加一个来源（Source）
     * @param source 需要增加的来源Source对象
     * @return 成功返回true，失败返回false
     */
    public boolean add(Source source){
        Connection conn = DbUtil.getConnection();
        if(conn != null ){
            PreparedStatement pstmt = null;
            String sql = "INSERT INTO t_source(s_name) VALUES ( ? )";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,source.getS_name());
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
     * 通过id删除来源，级联删除该来源的所有分类及题目,谨慎使用
     * @param s_id 需要删除的来源id
     * @return 成功返回true ，失败返回 false
     */
    public boolean deleteById(int s_id){
        Connection conn = DbUtil.getConnection();
        if(conn != null ){
            PreparedStatement pstmt = null;
            String sql = "DELETE FROM t_source WHERE s_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,s_id);
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
     * 保存修改之后的来源对象
     * @param source 修改之后的来源对象
     * @return 成功返回true ，失败返回false
     */
    public boolean save(Source source){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "UPDATE t_source SET s_name = ? WHERE s_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,source.getS_name());
                pstmt.setInt(2,source.getS_id());
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
     * 通过id获取来源信息
     * @param s_id 需要获取的来源的id
     * @return 成功返回Source对象，失败返回null
     */
    public Source getSourceById(int s_id){
        Source source = null;
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_source WHERE s_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,s_id);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    source = new Source();
                    source.setS_id(rs.getInt("s_id"));
                    source.setS_name(rs.getString("s_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }

        }
        return source;
    }

    /**
     * 获取来源列表
     * @return List<Source>
     */
    public List<Source> getSourceList(){
        List<Source> list = new ArrayList<>();
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_source ORDER BY s_name";
            try {
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                while (rs.next()){
                    Source source = new Source();
                    source.setS_id(rs.getInt("s_id"));
                    source.setS_name(rs.getString("s_name"));
                    list.add(source);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return list;
    }
}
