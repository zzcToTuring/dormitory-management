package com.zzc.community.service.impl;

import com.zzc.community.mapper.CodeMapper;
import com.zzc.community.mapper.UserMapper;
import com.zzc.community.mapper.UserRoleMapper;
import com.zzc.community.po.*;
import com.zzc.community.service.UserService;
import com.zzc.community.utils.ConstantsUtils;
import com.zzc.community.utils.RegularExpression;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: zzc
 * @Date: 2023-2-12 14:41
 * @Description:
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {


    final UserMapper userMapper;

    final CodeMapper codeMapper;

    final UserRoleMapper userRoleMapper;




    public UserServiceImpl(UserRoleMapper userRoleMapper,
                           CodeMapper codeMapper,
                           UserMapper userMapper
                         ){
        this.userMapper=userMapper;
        this.codeMapper = codeMapper;
        this.userRoleMapper = userRoleMapper;

    }


        @Override
    public void reg(User user) throws Exception {
        //判断注册码信息
        Code code = codeMapper.selectBycodeText(user.getCodeText());
        User byAccount = userMapper.findByAccount(user.getAccount());
        if ( byAccount != null){
            throw new Exception("该账户名已被注册，请更换账户名字");
        }
        if(!RegularExpression.decideAccounNumber(user.getAccount())){
            throw new Exception("匹配帐号不合法合法(必须是字母开头，允许5-16字节，允许字母数字下划线)");
        }
        if(!RegularExpression.decideAccounNumberPassword(user.getPassword())){
            throw new Exception("匹配帐号密码不合法(必须5-16字节，允许字母数字下划线)");
        }
        if(!RegularExpression.decidePhone(user.getUserPhone())){
            throw new Exception("手机号码第一个数必须为1,[3，5，7，8]:手机号码第二个数必须为:括号内数字");
        }
        if(!RegularExpression.decideRealName(user.getUserName())){
            throw new Exception("匹配真实姓名不合法(只允许2-6汉字)");
        }
        if (code == null ){
            throw new Exception("注册码不能为空或者注册码信息错误，请联系管理员");
        }

        if (code.getStatus() == 0){
            //可以用
            //铭文密码 + 盐  + 散列
            Md5Hash md5Hash = new Md5Hash(user.getPassword(), user.getCodeText(),1024);
            user.setPassword(md5Hash.toHex());
            code.setStatus(1);
            userMapper.save(user);
            //添加角色和用户的关系
            userRoleMapper.add(user.getUserId(),code.getRoleId());
            //xuy
            codeMapper.updateByUser(user);

        }else if (code.getStatus() == 1){
            //不可用
            throw new Exception("注册码不可用或者已经被注册，请联系管理员");
        }



    }

    @Override
    public User findByAccount(String account)  {
        User user = userMapper.findByAccount(account);
        return user;
    }

    @Override
    public User findRolesByAccount(String account) {
        return userMapper.findRolesByAccount(account);
    }


    @Override
    public List<Menu> findMenuByRoleId(Integer roleId) {
        return userMapper.findMenuByRoleId(roleId);
    }



    @Override
    public User removeUpdateByUserid(Integer userId) {
        User user = userMapper.findByUserId(userId);
        if (user.getStatus() == ConstantsUtils.CODE_STATUS_0){
          user.setStatus(ConstantsUtils.CODE_STATUS_1);
        }
        else{
            user.setStatus(ConstantsUtils.CODE_STATUS_0);
        }
        userMapper.deleteUpdate(user);
        return user;
    }

    @Override
    public boolean deleteById(Integer id) throws Exception {
        //删除用户
        int i = userMapper.deleteById(id);
        if(i<0){
            throw  new Exception("删除失败");
        }
        //删除关系
        int a = userRoleMapper.deleteByuserId(id);
        if(a<0){
            throw  new Exception("删除失败");
        }

        return true;
    }

    @Override
    public List<User> queryAllUser(String account, String userName, String userPhone) {
        List<User> collect = this.userMapper.selectAll(account,userName,userPhone).stream()
                .filter(u -> account == null || account == "" || u.getAccount().equals(account))
                .filter(u -> userName == null || userName == "" || u.getUserName().equals(userName))
                .filter(u -> userPhone == null || userPhone == "" ||  u.getUserPhone().equals(userPhone))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public int resetPassword(String account) {
        User byAccount = userMapper.findByAccount(account);
        //铭文密码 + 盐  + 散列
        Md5Hash md5Hash = new Md5Hash(ConstantsUtils.CODE_STATUS_666666, byAccount.getCodeText(),1024);
//        byAccount.setPassword(md5Hash.toHex());
//        String password = byAccount.getPassword();
        return userMapper.updatePasswordByAccount(account,md5Hash.toHex());
    }

    @Override
    public User findUserByAccount(String account) {
        return userMapper.findUserByAccount(account);
    }

    @Override
    public User editUser(User user) {
        userMapper.updateUser(user);
        return userMapper.findByAccount(user.getAccount());
    }

    @Override
    public User editPwd(User user) throws Exception {
        //判断密码格式
        if(!RegularExpression.decideAccounNumberPassword(user.getPassword())){
            throw new Exception("匹配帐号密码不合法(必须5-16字节，允许字母数字下划线)");
        }
        if(!RegularExpression.decideAccounNumberPassword(user.getNewPassword())){
            throw new Exception("匹配帐号密码不合法(必须5-16字节，允许字母数字下划线)");
        }
        if(!RegularExpression.decideAccounNumberPassword(user.getConfirmPassword())){
            throw new Exception("匹配帐号密码不合法(必须5-16字节，允许字母数字下划线)");
        }
        User byAccount = userMapper.findByAccount(user.getAccount());
        if (!user.getNewPassword().equals(user.getConfirmPassword())){
            throw new Exception("新密码和确认密码不一致，请重新输入");
        }

        //判断旧密码是否真确正确
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), byAccount.getCodeText(),1024);
        String oldPassword = md5Hash.toHex();
        if(!byAccount.getPassword().equals(oldPassword)){
            throw new Exception("旧密码输入错误，请重新输入)");
        }

        //修改密码
        Md5Hash md5Hash2 = new Md5Hash(user.getNewPassword(), byAccount.getCodeText(),1024);
        userMapper.updatePasswordByAccount(user.getAccount(), md5Hash2.toHex());
        return byAccount;
    }
}
