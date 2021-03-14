package com.yunduomsg.controller;

import com.yunduomsg.common.BaseService;
import com.yunduomsg.entity.ResponseBase;
import com.yunduomsg.service.UserService;
import com.yunduomsg.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * 用户模块
 */
@RequestMapping("/user")
@RestController
public class UserController extends BaseService {

    @Autowired
    private UserService userService;

    /**
     * 生成并保存验证码
     */
    @PostMapping("/login/findSetVFCode")
    public ResponseBase findSetVFCode(@RequestBody UserVO userVO){
            return userService.findSetVFCode(userVO);
    }

    /**
     * 用户登录验证
     */
    @PostMapping("/login/userLogin")
    public ResponseBase userLogin(@RequestBody UserVO userVO) {
        return userService.userLogin(userVO);
    }


}



