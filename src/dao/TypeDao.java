package dao;

import util.DbUtil;
import vo.Source;
import vo.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeDao {

    /**
     * 判断类型对象是否已经存在（完全匹配相同来源之间的类型名）
     * @param type 需要判断的类型对象
     * @return 存在返回 true，失败返回false
     */
    public boolean isExist(Type type){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT COUNT(*) FROM t_type WHERE t_name = ? AND s_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,type.getT_name());
                pstmt.setInt(2,type.getSource().getS_id());
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
     * 为某个来源增加一个分类
     * @param type 需要增加的分类对象（Type）
     * @return 成功返回true，失败返回false
     */
    public boolean add(Type type){
        Connection conn = DbUtil.getConnection();
        if(conn != null ){
            PreparedStatement pstmt = null;
            String sql = "INSERT INTO t_type(s_id,t_name) VALUES ( ? , ? )";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,type.getSource().getS_id());
                pstmt.setString(2,type.getT_name());
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
     * 根据id删除一个分类，级联删除分类下的所有题目信息
     * @param t_id 需要删除的分类id
     * @return 成功返回true ，失败返回false
     */
    public boolean deleteById(int t_id){
        Connection conn = DbUtil.getConnection();
        if(conn != null ){
            PreparedStatement pstmt = null;
            String sql = "DELETE FROM t_type WHERE t_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,t_id);
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
     * 保存修改之后的分类对象
     * @param type 修改之后的分类对象
     * @return 成功返回true，失败返回false
     */
    public boolean save(Type type){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "UPDATE t_type SET s_id = ? , t_name = ? WHERE t_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,type.getSource().getS_id());
                pstmt.setString(2,type.getT_name());
                pstmt.setInt(3,type.getT_id());
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
     * 通过id获取分类信息
     * @param t_id 需要获取的分类id
     * @return 成功返回Source对象，失败返回null
     */
    public Type getTypeById(int t_id){
        Type type = null;
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT t_type.t_id,t_source.s_id,t_source.s_name,t_type.t_name "+
                    "FROM t_type RIGHT JOIN t_source "+
                    "ON t_source.s_id = t_type.s_id WHERE t_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,t_id);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    Source source = new Source();
                    source.setS_id(rs.getInt("s_id"));
                    source.setS_name(rs.getString("s_name"));
                    type = new Type();
                    type.setT_id(rs.getInt("t_id"));
                    type.setT_name(rs.getString("t_name"));
                    type.setSource(source);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }

        }
        return type;
    }

    /**
     * 通过来源id获取相应来源的分类列表
     * @param s_id 来源的id ，为0 则获取全部来源的分类
     * @return List<Type> 来源的分类列表
     */
    public List<Type> getTypeBySourceId(int s_id){
        List<Type> list = new ArrayList<>();
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "CALL get_type_by_source_id( ? )";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,s_id);
                rs = pstmt.executeQuery();
                while(rs.next()){
                    Source source = new Source();
                    source.setS_id(rs.getInt("s_id"));
                    source.setS_name(rs.getString("s_name"));
                    Type type = new Type();
                    type.setT_id(rs.getInt("t_id"));
                    type.setT_name(rs.getString("t_name"));
                    type.setSource(source);
                    list.add(type);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return  list;
    }
}
