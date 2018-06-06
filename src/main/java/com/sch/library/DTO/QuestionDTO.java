package com.sch.library.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionDTO implements Serializable{
    private String id;//图书编号
    private String question_topic;//问题
    private String answer_A;//答案A
    private String answer_B;//答案B
    private String answer_C;//答案C
    private String right_answer;//正确答案
    private int difficulty;//难度 1——5 正确率低于20%的是level5
    private int answer_number;//回答次数
    private int right_number;//正确次数
}
