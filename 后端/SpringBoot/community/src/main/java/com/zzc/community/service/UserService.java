package com.zzc.community.service;

import com.zzc.community.po.*;

import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-2-12 14:40
 * @Description:
 */
public interface UserService {
    //注册用户
    void reg(User user) throws Exception;

    //根据账号查询
    User findByAccount(String account);

    //根据账号查询角色信息
    User findRolesByAccount(String account);



    List<Menu> findMenuByRoleId(Integer roleId);


    //对用户软删除
    User removeUpdateByUserid(Integer userId);

    //删除用户
    boolean deleteById(Integer id) throws Exception;


    List<User> queryAllUser(String account, String userName, String userPhone);

    int resetPassword(String account);

    User findUserByAccount(String account);

    User editUser(User user);

    User editPwd(User user) throws Exception;

}
