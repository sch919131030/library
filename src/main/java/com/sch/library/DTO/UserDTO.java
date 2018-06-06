package com.sch.library.DTO;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private String user_id;//用户编号
    private int life_answer_number;//生活类题目答题数
    private int life_right_number;//生活类题目正确数
    private int literature_answer_number;//文学类题目答题数
    private int literature_right_number;//文学类题目正确数
    private int video_answer_number;//影视娱乐类题目答题数
    private int video_right_number;//影视娱乐类题目正确数
    private int score;//积分


}
