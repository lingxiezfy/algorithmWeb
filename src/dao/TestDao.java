package dao;

import vo.Test;

import java.util.List;

public class TestDao {

    /**
     * 通过id查询test数据
     * @param id 需要查询的id
     * @return 成功查询返回Test对象，失败返回null
     */
    public Test getTestById(int id){
        Test test  = null;
        test = new Test();
        return test;
    }
}
