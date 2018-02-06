package dao;

import util.DbUtil;
import vo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao {
    /**
     * 判断问题在相同来源中是否存在
     * @param s_id 来源id
     * @param q_title 问题标题（这里只对问题标题进行完全匹配）
     * @return 存在返回true，不存在返回false
     */
    public boolean isExist(int s_id,String q_title){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT COUNT(*) FROM t_question WHERE s_id = ? AND q_title = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,s_id);
                pstmt.setString(2,q_title);
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
     * 增加一个问题(不包含code 及 sample)
     * @param question 增加的问题
     * @return 成功返回true，失败返回false
     */
    public boolean add(Question question){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "INSERT INTO t_question(r_id, t_id, tag, q_title, q_description, q_author, q_time_limit, q_space_limit, q_input, q_output) "+
                    " VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,question.getRank().getR_id());
                pstmt.setInt(2,question.getType().getT_id());
                pstmt.setString(3,convertTagsToBlob(question.getTags()));
                pstmt.setString(4,question.getQ_title());
                pstmt.setString(5,question.getQ_description());
                pstmt.setString(6,question.getQ_author());
                pstmt.setInt(7,question.getQ_time_limit());
                pstmt.setInt(8,question.getQ_space_limit());
                pstmt.setString(9,question.getQ_input());
                pstmt.setString(10,question.getQ_output());
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
     * 将标签数组转化成存入数据库的二进制索引字符串
     * @param tags 传入的问题标签数组
     * @return String 标签索引组合的字符串
     */
    private String convertTagsToBlob(ArrayList<Tag> tags) {
        long tagIndex = 0;
        for (Tag tag : tags) {
            tagIndex += Long.parseLong(tag.getTag_index());
        }
        return tagIndex+"";
    }

    /**
     * 通过id删除问题，级联删除其下属的示例及code
     * @param q_id 需要删除的问题id
     * @return 成功返回true，失败返回false
     */
    public boolean deleteById(int q_id){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "DELETE FROM t_question WHERE q_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,q_id);
                int rowCount = pstmt.executeUpdate();
                return rowCount > 0 ;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt);
            }
        }
        return false;
    }

    /**
     * 保存一个Question对象
     * @param question 需要保存的question对象
     * @return 成功返回true，失败返回false
     */
    public boolean save(Question question){
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            String sql = "UPDATE t_question SET r_id = ? ,SET t_id = ? ,SET tag = ? ,SET q_title = ? "+
                    ",SET q_description = ? ,SET q_author = ? ,SET q_time_limit = ? ,SET q_space_limit = ? "+
                    ",SET q_input = ? ,SET q_output = ? WHERE q_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,question.getRank().getR_id());
                pstmt.setInt(2,question.getType().getT_id());
                pstmt.setString(3,convertTagsToBlob(question.getTags()));
                pstmt.setString(4,question.getQ_title());
                pstmt.setString(5,question.getQ_description());
                pstmt.setString(6,question.getQ_author());
                pstmt.setInt(7,question.getQ_time_limit());
                pstmt.setInt(8,question.getQ_space_limit());
                pstmt.setString(9,question.getQ_input());
                pstmt.setString(10,question.getQ_output());
                pstmt.setInt(11,question.getQ_id());
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
     * 通过问题id获取问题信息
     * @param q_id 问题id
     * @return 成功返回问题对象，失败返回null
     */
    public Question getQuestionById(int q_id){
        Question question = null;
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "SELECT t_question.*,t_rank.r_name,t_type.t_name,t_source.s_name FROM t_question " +
                    "RIGHT JOIN t_rank ON t_question.r_id = t_rank.r_id " +
                    "RIGHT JOIN t_type ON t_question.t_id = t_type.t_id " +
                    "RIGHT JOIN t_source on t_type.s_id = t_source.s_id " +
                    "WHERE q_id = ? ";
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,q_id);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    question = new Question();
                    Rank rank = new Rank();
                    rank.setR_id(rs.getInt("r_id"));
                    rank.setR_name(rs.getString("r_name"));
                    Source source = new Source();
                    source.setS_id(rs.getInt("s_id"));
                    source.setS_name(rs.getString("s_name"));
                    Type type = new Type();
                    type.setT_id(rs.getInt("t_id"));
                    type.setT_name(rs.getString("t_name"));
                    type.setSource(source);
                    question.setQ_id(rs.getInt("q_id"));
                    question.setRank(rank);
                    question.setType(type);
                    question.setTags(convertBlobToTags(rs.getString("tag")));
                    question.setQ_title(rs.getString("q_title"));
                    question.setQ_description(rs.getString("q_description"));
                    question.setQ_author(rs.getString("q_author"));
                    question.setQ_time_limit(rs.getInt("q_time_limit"));
                    question.setQ_space_limit(rs.getInt("q_space_limit"));
                    question.setQ_input("q_input");
                    question.setQ_output("q_output");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return question;
    }

    /**
     * 将索引串转换成标签列表
     * @param tagIndex 标签索引串
     * @return 返回标签列表，空索引则返回空列表
     */
    private ArrayList<Tag> convertBlobToTags(String tagIndex) {
        ArrayList<Tag> tags = new ArrayList<>();
        if(tagIndex == null || "0".equals(tagIndex)|| "null".equals(tagIndex) || "".equals(tagIndex) ){
            return tags;
        }else {
            long indexs;
            try {
                indexs = Long.parseLong(tagIndex);
            }catch (Exception e){
                indexs = 0;
            }
            long maxPub = 0;
            long maxindex = 0;
            do {
                maxPub = (long)(Math.log(indexs) / Math.log(2)) ;
                maxindex = (long) Math.pow(2,maxPub);
                TagDao dao = new TagDao();
                tags.add(dao.getTagByIndex(maxindex+""));

                indexs -= maxindex;
            }while (indexs > 0);
            return tags;
        }
    }

    /**
     * 按条件检索问题列表,并分页
     * @param curr_page 当前的页号（页号1开始），若不进行分页，请传入0
     * @param page_size 每页显示几条记录
     * @param r_id 需要查询的难度id，全部难度传入0
     * @param t_id 需要查询的类型id，全部类型传入0
     * @param s_id 需要查询的来源id，全部来源传入0
     * @param tag 需要查询的标签对象，不区分标签则保持为null，或传入标签id为 0 的Tag对象
     * @param search_title 需要对标题模糊查询的关键字 ,获取全部标题保持关键字为null或""
     * @return 返回搜索结果标题列表，List<Question> ，只返回id ,难度等级，类型（包含来源）,标签列表 及 title
     */
    public List<Question> getQuestionTitleList(int curr_page,int page_size, int r_id,int t_id,int s_id,Tag tag,String search_title){
        List<Question> list = new ArrayList<>();
        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "CALL get_question_title_list( ? , ? , ? , ? , ? , ? , ? );";
            String titleTmp = "";
            if(search_title == null || "".equals(search_title)){
                titleTmp = "";
            }else {
                titleTmp = search_title;
            }
            String tag_index = "0";
            if(tag == null || tag.getTag_id() == 0){
                tag_index = "0";
            }else {
                tag_index = tag.getTag_index();
            }
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,curr_page);
                pstmt.setInt(2,page_size);
                pstmt.setInt(3,r_id);
                pstmt.setInt(4,t_id);
                pstmt.setInt(5,s_id);
                pstmt.setString(6,tag_index);
                pstmt.setString(7,titleTmp);
                rs = pstmt.executeQuery();
                while(rs.next()){
                    Question question = new Question();
                    Rank rank = new Rank();
                    rank.setR_id(rs.getInt("r_id"));
                    rank.setR_name(rs.getString("r_name"));

                    Source source = new Source();
                    source.setS_id(rs.getInt("s_id"));
                    source.setS_name(rs.getString("s_name"));

                    Type type = new Type();
                    type.setT_id(rs.getInt("t_id"));
                    type.setT_name(rs.getString("t_name"));
                    type.setSource(source);

                    question.setQ_id(rs.getInt("q_id"));
                    question.setRank(rank);
                    question.setType(type);
                    question.setTags(convertBlobToTags(rs.getString("tag")));
                    question.setQ_title(rs.getString("q_title"));

                    list.add(question);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return list;
    }

    /**
     * 获取当前条件下记录的总条数
     * @param r_id 需要查询的难度id，全部难度传入0
     * @param t_id 需要查询的类型id，全部类型传入0
     * @param s_id 需要查询的来源id，全部来源传入0
     * @param tag 需要查询的标签对象，不区分标签则保持为null，或传入标签id为 0 的Tag对象
     * @param search_title 需要对标题模糊查询的关键字 ,获取全部标题保持关键字为null或""
     * @return int
     */
    public int getQuestionTitleListCount(int r_id,int t_id,int s_id,Tag tag,String search_title){
        int count = 0;

        Connection conn = DbUtil.getConnection();
        if(conn != null){
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = "CALL get_question_title_list_count( ? , ? , ? , ? , ? );";
            String titleTmp = "";
            if(search_title == null || "".equals(search_title)){
                titleTmp = "";
            }else {
                titleTmp = search_title;
            }
            String tag_index = "0";
            if(tag == null || tag.getTag_id() == 0){
                tag_index = "0";
            }else {
                tag_index = tag.getTag_index();
            }
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,r_id);
                pstmt.setInt(2,t_id);
                pstmt.setInt(3,s_id);
                pstmt.setString(4,tag_index);
                pstmt.setString(5,titleTmp);
                rs = pstmt.executeQuery();
                if(rs.next()){
                    count = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DbUtil.freeAll(conn,pstmt,rs);
            }
        }
        return count;
    }
}
