package com.sch.library.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sch.library.base.Result;
import com.sch.library.DTO.UserDTO;
import com.sch.library.Service.*;

import javax.servlet.http.HttpSession;

@RequestMapping(value = "/common")
@RestController
public class Common {
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;

    //注册接口
    @RequestMapping(value = "registered",method = RequestMethod.POST)
    public Result registered(HttpSession session, String user_id){
        return userService.registered(user_id);
    }

    //登录接口
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Result login(HttpSession session,String user_id){
        return userService.login(session,user_id);
    }

    //用户是否存在接口
    @RequestMapping(value = "exists",method = RequestMethod.GET)
    public Result exists(String user_id){
        System.out.println("1111111111111");
        return userService.exists(user_id);
    }

//    //获取题目
    @RequestMapping(value = "getquestion",method = RequestMethod.GET)
    public Result getQuestionInfo(HttpSession session,int category){
        return questionService.selectQuestion_category(session.getAttribute("user_id")+"",category);
    }
    //获取题目
//    @RequestMapping(value = "getquestion",method = RequestMethod.GET)
//    public Result getQuestionInfo(String user_id,int category){
//        return questionService.selectQuestion_category(user_id,category);
//    }
//    @RequestMapping(value = "getquestion",method = RequestMethod.GET)
//    public Result getQuestionInfo(){
//        String user_id = "1";
//        int category = 1;
//        return questionService.selectQuestion_category(user_id,category);
//    }

    //设置题目信息接口(答完一道题调用此方法传回一次)
    @RequestMapping(value = "setquestionInfo",method = RequestMethod.POST)
    public Result setUserInfo(int category,int id,int isRight){
        return questionService.setQuestionInfo(category,id,isRight);
    }

}
