package dao;

import util.DbUtil;
import vo.Rank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RankDao {

    /**
     * 判断难度等级是否存在
     * @param r_name 需要判断的来源名
     * @return 存在返回true，不存在返回false
     */
    public boolean isExist(String r_name){
        Connection conn = DbUtil.getConnection();
        if(conn != null) {
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT COUNT(*) FROM t_rank WHERE r_name = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,r_name);
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
     * 增加一个难度等级（Rank）
     * @param rank 需要增加的难度等级Rank对象
     * @return 成功返回true，失败返回false
     */
    public boolean add(Rank rank){
        Connection conn = DbUtil.getConnection();
        if(conn != null ){
            PreparedStatement pstmt = null;
            String sql = "INSERT INTO t_rank(r_name) VALUES ( ? )";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,rank.getR_name());
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
     * 通过id删除难度等级，级联删除该难度等级的所有分类及题目,谨慎使用
     * @param r_id 需要删除的难度等级id
     * @return 成功返回true ，失败返回 false
     */
    public boolean deleteById(int r_id){
        Connection conn = DbUtil.getConnection();
        if(conn != null ){
            PreparedStatement pstmt = null;
            String sql = "DELETE FROM t_rank WHERE r_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,r_id);
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
     * 保存修改之后的难度等级对象
     * @param rank 修改之后的难度等级对象
     * @return 成功返回true ，失败返回false
     */
    public boolean save(Rank rank){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "UPDATE t_rank SET r_name = ? WHERE r_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,rank.getR_name());
                pstmt.setInt(2,rank.getR_id());
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
     * 通过id获取难度等级信息
     * @param r_id 需要获取的难度等级的id
     * @return 成功返回Rank对象，失败返回null
     */
    public Rank getRankById(int r_id){
        Rank rank = null;
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_rank WHERE r_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,r_id);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    rank = new Rank();
                    rank.setR_id(rs.getInt("r_id"));
                    rank.setR_name(rs.getString("r_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }

        }
        return rank;
    }

    /**
     * 获取难度等级列表
     * @return 返回难度等级List<Rank>
     */
    public List<Rank> getRankList(){
        List<Rank> list = new ArrayList<>();
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_rank ORDER BY r_name";
            try {
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                while (rs.next()){
                    Rank rank = new Rank();
                    rank.setR_id(rs.getInt("r_id"));
                    rank.setR_name(rs.getString("r_name"));
                    list.add(rank);
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
