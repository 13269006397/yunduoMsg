package com.yunduomsg.service;


import com.yunduomsg.entity.ResponseBase;
import com.yunduomsg.entity.User;
import com.yunduomsg.vo.UserVO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    // 发送验证码
    ResponseBase findSetVFCode(UserVO userVO);

    // 用户登录
    ResponseBase userLogin(UserVO userVO);

    // 根据手机号查用户信息
    User selectUserByMobile(UserVO userVO);


}
