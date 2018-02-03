package dao;

import util.DbUtil;
import vo.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    /**
     * 判断管理员账户是否存在
     * @param a_account 管理员账户
     * @return boolean 存在返回true，否则返回false
     */
    public boolean isExist(String a_account){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "select count(*) from t_admin where a_account = ?";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,a_account);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    return rs.getInt(1) > 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return false;
    }

    /**
     * 增加一位管理员
     * @param admin Admin(管理员对象)
     * @return boolean 成功返回true，否则返回false
     */
    public boolean add(Admin admin){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "insert into t_admin(a_account,a_password) values ( ? , ? )";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,admin.getA_account());
                pstmt.setString(2,admin.getA_password());
                int rowCount = pstmt.executeUpdate();
                return rowCount > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DbUtil.freeAll(conn,pstmt);
            }
        }
        return false;
    }

    /**
     * 保存编辑之后的管理员信息
     * @param admin 修改之后的Admin(管理员对象)
     * @return boolean 成功返回true，失败返回 false
     */
    public boolean save(Admin admin){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "UPDATE t_admin SET a_account = ? , a_password = ? WHERE a_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,admin.getA_account());
                pstmt.setString(2,admin.getA_password());
                pstmt.setInt(3,admin.getA_id());
                int rowCount = pstmt.executeUpdate();
                return rowCount > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DbUtil.freeAll(conn,pstmt);
            }
        }
        return false;
    }

    /**
     * 通过id删除一个管理员
     * @param a_id int 需要删除的管理员的id
     * @return boolean 成功返回true,失败返回false
     */
    public boolean deleteById(int a_id){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "DELETE FROM t_admin WHERE a_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,a_id);
                int rowCount = pstmt.executeUpdate();
                return rowCount > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DbUtil.freeAll(conn,pstmt);
            }
        }
        return false;
    }

    /**
     * 通过id获取管理员信息
     * @param a_id int 被获取管理员的id
     * @return Admin 成功返回管理员Admin对象，失败返回null
     */
    public Admin getAdminById(int a_id){
        Admin admin = null;
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_admin WHERE a_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,a_id);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    admin = new Admin();
                    admin.setA_id(rs.getInt("a_id"));
                    admin.setA_account(rs.getString("a_account"));
                    admin.setA_password(rs.getString("a_password"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return admin;
    }

    /**
     * 通过account获取管理员信息
     * @param a_account String 被获取管理员的帐号
     * @return Admin 成功返回管理员Admin对象，失败返回null
     */
    public Admin getAdminByAccount(String a_account){
        Admin admin = null;
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_admin WHERE a_account = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,a_account);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    admin = new Admin();
                    admin.setA_id(rs.getInt("a_id"));
                    admin.setA_account(rs.getString("a_account"));
                    admin.setA_password(rs.getString("a_password"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return admin;
    }

    /**
     * 获取管理员列表
     * @return List<Admin>列表 成功返回管理员列表，失败返回空列表
     */
    public List<Admin> getAdminList(){
        List<Admin> list = new ArrayList<>();
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_admin ORDER BY a_account";
            try {
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                while (rs.next()){
                    Admin admin = new Admin();
                    admin.setA_id(rs.getInt("a_id"));
                    admin.setA_account(rs.getString("a_account"));
                    admin.setA_password(rs.getString("a_password"));
                    list.add(admin);
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
