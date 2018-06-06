package com.sch.library.DAO;

import com.sch.library.DTO.QuestionDTO;
import com.sch.library.DTO.UserDTO;
import com.sch.library.DTO.QuestionInfoDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

public interface QuestionDao {
    int exists_life(int id);//查询该id是否存在
    int exists_literature(int id);//查询该id是否存在
    int exists_video(int id);//查询该id是否存在
    List<QuestionDTO> selectQuestion_category(int question_category);//通过用户选择的题目类型生成题目
    List<QuestionDTO> selectQuestion_life();//获取表内所有题目
    List<QuestionDTO> selectQuestion_literature();
    List<QuestionDTO> selectQuestion_video();

    QuestionDTO selectQuestion_life_id(int id);//获取表内某一id对应的题目
    QuestionDTO selectQuestion_literature_id(int id);
    QuestionDTO selectQuestion_video_id(int id);
    //List<QuestionDTO> selectQuestion_difficulty(int question_category,int difficulty);//通过用户选择的题目类型/难度生成题目
    //List<QuestionInfoDTO> selectQuestionInfo_category(String question_category);//通过用户选择的题目类型生成返回给用户的题目
    int setQuestionInfo_life(@Param("id") int id,@Param("difficulty") int difficulty,@Param("right_number") int right_number);//设置（修改）问题的回答次数和正确回答的次数
    int setQuestionInfo_literature(@Param("id") int id,@Param("difficulty")int difficulty,@Param("right_number") int right_number);//设置（修改）问题的回答次数和正确回答的次数
    int setQuestionInfo_video(@Param("id") int id,@Param("difficulty")int difficulty,@Param("right_number") int right_number);//设置（修改）问题的回答次数和正确回答的次数

    //用户那边也得有一个类似的方法,返回用户的id和答题类别和是否正确
}

