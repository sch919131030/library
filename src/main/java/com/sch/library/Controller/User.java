package com.sch.library.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sch.library.base.Result;
import com.sch.library.DTO.*;
import com.sch.library.Service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
@RequestMapping(value = "/user")
@RestController
public class User {
    @Autowired
    UserService userService;

    //设置用户信息接口(答完十道题之后调用此方法)
    @RequestMapping(value = "setuserInfo",method = RequestMethod.POST)
    public Result setUserInfo(HttpSession session,int category,int answer_number,int right_number){
        return userService.setUserInfo(session.getAttribute("user_id")+"",category,answer_number,right_number);
    }
//    @RequestMapping(value = "setuserInfo",method = RequestMethod.GET)
//    public Result setUserInfo(){
//        return userService.setUserInfo("1",1,3,2);
//    }

     //获取用户信息
    @RequestMapping(value = "getuserInfo",method = RequestMethod.GET)
    public Result getUserInfo(HttpSession session){
        return userService.getUserInfo(session.getAttribute("user_id")+"");
    }
}
