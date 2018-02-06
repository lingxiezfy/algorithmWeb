package dao;

import util.DbUtil;
import vo.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDao {

    /**
     * 判断标签是否存在
     * @param tag_neme 标签名
     * @return 存在返回true，不存在返回false
     */
    public boolean isExist(String tag_neme){
        Connection conn = DbUtil.getConnection();
        if(conn != null) {
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT COUNT(*) FROM t_tag WHERE tag_name = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,tag_neme);
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
     * 增加一个标签
     * @param tag_name 标签名
     * @return 成功返回true,失败返回 false
     */
    public boolean add(String tag_name){
        Connection conn = DbUtil.getConnection();
        if(conn != null ){
            PreparedStatement pstmt = null;
            String sql = "INSERT INTO t_tag(tag_name) VALUES ( ? )";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,tag_name);
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
     * 保存修改之后的标签
     * @param tag 需要保存的tag对象
     * @return 成功返回 true，失败返回false
     */
    public boolean save(Tag tag){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "UPDATE t_tag SET tag_name = ? WHERE tag_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,tag.getTag_name());
                pstmt.setInt(2,tag.getTag_id());
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
     * 通过id删除标签
     * @param tag_id 需要删除的标签id
     * @return 成功返回true ，失败返回 false
     */
    public boolean deleteById(int tag_id){
        Connection conn = DbUtil.getConnection();
        if(conn != null ){
            PreparedStatement pstmt = null;
            String sql = "DELETE FROM t_tag WHERE tag_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,tag_id);
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
     * 通过id获取标签
     * @param tag_id 标签的id
     * @return 标签对象
     */
    public Tag getTagById(int tag_id){
        Tag tag = null;
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_tag WHERE tag_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,tag_id);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    tag = new Tag();
                    tag.setTag_id(rs.getInt("tag_id"));
                    tag.setTag_name(rs.getString("tag_name"));
                    tag.setTag_index(rs.getString("tag_index"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return tag;
    }

    /**
     * 通过index获取标签
     * @param tag_index 标签的id
     * @return 标签对象
     */
    public Tag getTagByIndex(String tag_index){
        Tag tag = null;
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_tag WHERE tag_index = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,tag_index);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    tag = new Tag();
                    tag.setTag_id(rs.getInt("tag_id"));
                    tag.setTag_name(rs.getString("tag_name"));
                    tag.setTag_index(rs.getString("tag_index"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return tag;
    }

    public List<Tag> getTagList(){
        List<Tag> list = new ArrayList<>();
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_tag ORDER BY tag_name";
            try {
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                while (rs.next()){
                    Tag tag = new Tag();
                    tag.setTag_id(rs.getInt("tag_id"));
                    tag.setTag_name(rs.getString("tag_name"));
                    tag.setTag_index(rs.getString("tag_index"));
                    list.add(tag);
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
