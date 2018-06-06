package com.sch.library.Service;

import com.sch.library.base.Result;

public interface QuestionService {
    Result selectQuestion_category(String user_id,int question_category);//通过用户选择的题目类型生成题目
    //Result selectQuestionInfo_category(String user_id,String question_category);//通过用户选择的题目类型生成题目
    Result setQuestionInfo(int category,int id,int isRight);//设置（修改）问题的回答是否正确
}
