package com.yunduomsg.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.yunduomsg.common.BaseService;
import com.yunduomsg.common.Constants;
import com.yunduomsg.mapper.UserMapper;
import com.yunduomsg.entity.ResponseBase;
import com.yunduomsg.entity.User;
import com.yunduomsg.service.UserService;
import com.yunduomsg.utils.AliMsgServer;
import com.yunduomsg.utils.NumberUtils;
import com.yunduomsg.utils.RedisUtils;
import com.yunduomsg.utils.TokenUtils;
import com.yunduomsg.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;

@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private NumberUtils numberUtils;
    @Autowired
    private AliMsgServer aliMsgServer;


    /**
     * 登录验证码发送
     * @param userVO
     * @return
     */
    @Override
    public ResponseBase findSetVFCode(UserVO userVO) {
        // 手机号非空校验
        if (StringUtils.isEmpty(userVO.getMobile())) {
            return setResultError(Constants.ERROR_LOGIN__NOPHONE);
        }
        // 查询手机号是否已经注册
        User user = selectUserByMobile(userVO);
        if (!StringUtils.isEmpty(user)) {
            String mobile = userVO.getMobile();
            try {
                // 生成验证码存入Redis
                String vfCode = numberUtils.randomCode();
                redisUtils.setKeyS(mobile, vfCode, 60);
                System.err.println(vfCode);

                /*调用aliyun发送短信验证码*/
                UserVO sendMsgVO = new UserVO();
                sendMsgVO.setMobile(mobile);
                sendMsgVO.setVfCode(vfCode);
                aliMsgServer.sendMsg(sendMsgVO);
            } catch (ClientException e) {
                return setResultError(Constants.ERROR_SEND_QCORE);
            }
        } else {
            return setResult(Constants.CUS_ERROR, Constants.NOT_USER, null);
        }
        return setResultSuccessMsg(Constants.SUCCESS_SEND_QCORE);
    }

    /**
     * 用户登录验证
     * @param userVO
     * @return
     */
    @Override
    public ResponseBase userLogin(UserVO userVO) {
        User user = selectUserByMobile(userVO);
        HashMap<String, Object> resultParams = new HashMap<>();
        if (user.getIsDelete().equals(Constants.PERMISSION_TWO)) {
            return setResultError(Constants.ERROR_LOGIN_USER);
        }
        //账号不存在
        if (StringUtils.isEmpty(user)) {
            return setResultError(Constants.ERROR_LOGIN_USER_NAME);
        }
        //验证码登录
        if (!StringUtils.isEmpty(userVO.getVfCode())) {
            String key = redisUtils.getKey(userVO.getMobile());
            if (key.equals(userVO.getVfCode())) {
                try {
                    //生成Token
                    String token = tokenUtils.token(user.getMobile(), user.getPassword());
                    resultParams.put(Constants.SEARCH_LOGIN_TOKEN, token);
                    resultParams.put(Constants.SEARCH_USER_ID, user.getId());
                    resultParams.put(Constants.SEARCH_USER_PERMISSION, user.getPermission());
                    resultParams.put(Constants.LOGIN_AVATAR, user.getAvatar());
                    //修改最后登陆时间
                    user.setLastTime(new Date());
                    userMapper.updateUserById(user);
                    return setResult(Constants.SUCCESS, Constants.SUCCESS_LOGIN, resultParams);
                } catch (Exception e) {
                    return setResultError(Constants.ERROR_TRY_CATCH);
                }
            }
            return setResultError(Constants.ERROR_QCORE_);
        }
        //密码登录
        if (!StringUtils.isEmpty(userVO.getPassword())) {
            //账号存在 密码错误
            if (!StringUtils.isEmpty(user) && !user.getPassword().equals(userVO.getPassword())) {
                return setResultError(Constants.ERROR_LOGIN_PWD);
            }
            //密码正确
            if (user.getPassword().equals(userVO.getPassword())) {
                try {
                    //生成Token
                    String token = tokenUtils.token(user.getMobile(), user.getPassword());
                    resultParams.put(Constants.SEARCH_LOGIN_TOKEN, token);
                    resultParams.put(Constants.SEARCH_USER_ID, user.getId());
                    resultParams.put(Constants.SEARCH_USER_PERMISSION, user.getPermission());
                    resultParams.put(Constants.LOGIN_AVATAR, user.getAvatar());
                    //修改最后登陆时间
                    user.setLastTime(new Date());
                    userMapper.updateUserById(user);
                    return setResult(Constants.SUCCESS, Constants.SUCCESS_LOGIN, resultParams);
                } catch (Exception e) {
                    return setResultError(Constants.ERROR_TRY_CATCH);
                }
            }
        }
        return setResultError(Constants.ERROR_LOGIN_ERROR);
    }

    /**
     * 根据手机号查询用户
     * @param userVO
     * @return
     */
    @Override
    public User selectUserByMobile(UserVO userVO) {
        HashMap<String, Object> requestmap = new HashMap<>();
        String mobile = userVO.getMobile();
        if (!StringUtils.isEmpty(mobile)) {
            requestmap.put(Constants.SEARCH_MOBILE, mobile);
        }
        return userMapper.selectUserByMobile(requestmap);
    }

}
