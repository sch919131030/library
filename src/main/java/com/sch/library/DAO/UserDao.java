package com.sch.library.DAO;

import org.springframework.stereotype.Repository;
import com.sch.library.DTO.UserDTO;
import org.apache.ibatis.annotations.Param;
@Repository
public interface UserDao {
    int exists(String user_id);//查询该id是否存在
    int insertUser(String user_id);//注册时添加用户信息
    int setUserInfo(@Param("user_id")String user_id,@Param("category")int category,@Param("answer_number")int answer_number,@Param("right_number")int right_number);//修改用户信息(积分/正确题目个数)
    int setUserInfo_life(@Param("user_id")String user_id,@Param("answer_number")int answer_number,@Param("right_number")int right_number);//修改用户信息(积分/正确题目个数)
    int setUserInfo_literature(@Param("user_id")String user_id,@Param("answer_number")int answer_number,@Param("right_number")int right_number);//修改用户信息(积分/正确题目个数)
    int setUserInfo_video(@Param("user_id")String user_id,@Param("answer_number")int answer_number,@Param("right_number")int right_number);//修改用户信息(积分/正确题目个数)
    //UserDTO selectOne(String user_id);
    UserDTO selectUserInfo(String user_id);//查询用户信息
    double rightRate(@Param("user_id")String user_id,@Param("category")int category);//该类型题目的正确率
    //double searchRank(String user_id);//查询排名
    //int insertRank(String user_id);//把排名插入到排行榜(每次一注册就插到排行榜的最后面)
}
