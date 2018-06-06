package com.sch.library.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionInfoDTO implements Serializable{
    private String id;//图书编号
    private String question_topic;//问题
    private String answer_A;//答案A
    private String answer_B;//答案B
    private String answer_C;//答案C
    private String right_answer;//正确答案
}