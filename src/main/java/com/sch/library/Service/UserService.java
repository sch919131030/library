package com.sch.library.Service;

import org.springframework.web.multipart.MultipartFile;
import com.sch.library.DTO.UserDTO;
import com.sch.library.base.Result;
import javax.servlet.http.HttpSession;

//提供给用户的服务（个人信息相关）
public interface UserService {
    Result registered(String user_id);//注册

    Result login(HttpSession session, String user_id);//登录

    Result exists(String user_id);//判断id是否存在

    Result setUserInfo(String user_id,int category,int answer_number,int right_number);//修改用户信息(积分/正确题目个数)score也要修改,不同题目抵不同分数

    Result getUserInfo(String user_id);//获取个人信息

}
