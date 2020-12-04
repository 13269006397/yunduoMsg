package com.yunduomsg.mapper;

import com.yunduomsg.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface UserMapper {

    /**
     * 根据手机号获取用户
     * @param map
     * @return
     */
    User selectUserByMobile(HashMap<String,Object> map);

    /**
     * 修改用户
     * @param record
     * @return
     */
    int updateUserById(User record);


}