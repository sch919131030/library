package com.sch.library.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.sch.library.base.Result;
import com.sch.library.ResultCache;
import com.sch.library.DAO.UserDao;
import com.sch.library.DTO.UserDTO;
import com.sch.library.Service.UserService;
import com.sch.library.Success;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserDao userDao;

    @Override
    public Result registered(String user_id) {
        if(userDao.exists(user_id)!=0) {
            return ResultCache.FAILURE;
        }
        userDao.insertUser(user_id);
        //userDao.insertRank(user_id);

        return ResultCache.OK;
    }

    @Override
    public Result login(HttpSession session, String user_id) {
        if(userDao.exists(user_id)==0) {
            return ResultCache.PERMISSION_DENIED;
        }
        session.setAttribute("user_id", user_id);
        return ResultCache.OK;
    }

    @Override
    public Result exists(String user_id) {
        if(userDao.exists(user_id)!=0){
            return ResultCache.getDataOk(Success.SUCCESS);
        }
        return ResultCache.getDataOk(Success.UNSUCCESS);
    }

    @Override
    public Result setUserInfo(String user_id,int category,int answer_number,int right_number) {
        if(userDao.exists(user_id)==0) {
            return ResultCache.PERMISSION_DENIED;
        }
        if (category==1){
            int result = userDao.setUserInfo_life(user_id,answer_number,right_number);
            return ResultCache.getCache(result);
        }
        else if (category==2){
            int result = userDao.setUserInfo_literature(user_id,answer_number,right_number);
            return ResultCache.getCache(result);
        }
        else {
            int result = userDao.setUserInfo_video(user_id,answer_number,right_number);
            return ResultCache.getCache(result);
        }

    }

    @Override
    public Result getUserInfo(String user_id) {
        if(userDao.exists(user_id)==0) {
            return ResultCache.PERMISSION_DENIED;
        }
        UserDTO userinfo = userDao.selectUserInfo(user_id);
        return ResultCache.getDataOk(userinfo);
    }


}
