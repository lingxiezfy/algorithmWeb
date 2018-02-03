package dao;

import util.DbUtil;
import vo.Sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SampleDao {
    /**
     * 判断示例对象是否已经存在（完全匹配相同题目的示例）
     * @param sample 需要判断的示例对象
     * @return 存在返回 true，失败返回false
     */
    public boolean isExist(Sample sample){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT COUNT(*) FROM t_sample WHERE sample_input = ? AND q_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1,sample.getSample_input());
                pstmt.setInt(2,sample.getQ_id());
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
     * 增加一个示例
     * @param sample 示例对象
     * @return 成功返回true，失败返回false
     */
    public boolean add(Sample sample){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "INSERT INTO t_sample(q_id,sample_input,sample_output) "+
                    " VALUES( ? , ? , ? )";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,sample.getQ_id());
                pstmt.setString(2,sample.getSample_input());
                pstmt.setString(3,sample.getSample_output());
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
     * 根据id删除一个示例
     * @param sample_id 需要删除的示例id
     * @return 成功返回true ，失败返回false
     */
    public boolean deleteById(int sample_id){
        Connection conn = DbUtil.getConnection();
        if(conn != null ){
            PreparedStatement pstmt = null;
            String sql = "DELETE FROM t_sample WHERE sample_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,sample_id);
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
     * 保存修改之后的示例对象
     * @param sample 修改之后的示例对象
     * @return 成功返回true，失败返回false
     */
    public boolean save(Sample sample){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "UPDATE t_sample SET q_id = ? , sample_input = ? ,sample_output = ? "+
                    " WHERE sample_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,sample.getQ_id());
                pstmt.setString(2,sample.getSample_input());
                pstmt.setString(3,sample.getSample_output());
                pstmt.setInt(4,sample.getSample_id());
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
     * 返回某问题的示例列表
     * @param q_id 问题的id
     * @return 成功返回列表，失败返回空列表
     */
    public List<Sample> getSampleListByQuestionId(int q_id){
        List<Sample> list = new ArrayList<>();
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT * FROM t_sample WHERE q_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,q_id);
                rs = pstmt.executeQuery();
                while(rs.next()){
                    Sample sample = new Sample();
                    sample.setSample_id(rs.getInt("sample_id"));
                    sample.setQ_id(rs.getInt("q_id"));
                    sample.setSample_input(rs.getString("sample_input"));
                    sample.setSample_output(rs.getString("sample_output"));
                    list.add(sample);
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
