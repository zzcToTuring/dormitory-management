package com.zzc.community.controller;

import com.zzc.community.po.Menu;
import com.zzc.community.po.User;
import com.zzc.community.service.MenuService;
import com.zzc.community.service.UserService;
import com.zzc.community.utils.APIResult;
import com.zzc.community.utils.ApplicationContextUtils;
import com.zzc.community.utils.ConstantsUtils;
import com.zzc.community.utils.TokenUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-3-9 14:34
 * @Description:
 */
@RequestMapping("/user")
@RestController
public class LoginController {

    final UserService userService;

    final MenuService menuService;

    public LoginController(UserService userService, MenuService menuService){
        this.userService = userService;
        this.menuService = menuService;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public APIResult login(@RequestBody User user){
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(user.getAccount(),user.getPassword()));
            // 获取当前登录用户
            User loginuser = userService.findRolesByAccount(user.getAccount());
            List<Menu> menuByRoleName = menuService.findMenuByRoleName(loginuser.getRoleName());
            loginuser.setMenus(menuByRoleName);
            if (loginuser.getStatus().equals(ConstantsUtils.CODE_STATUS_1)){
                return APIResult.notFound("该用户已被禁用或者拉入黑名单，请联系管理员");
            }
            loginuser.setPassword(null);
            loginuser.setCodeText(null);
            String token = TokenUtil.createToken(loginuser.getAccount());
            return new APIResult(ConstantsUtils.CODE_STATUS_200,token,1,loginuser);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return APIResult.notFound("用户名错误");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return APIResult.notFound("密码错误");
        }

    }
    @RequestMapping(value = "/loginout",method = RequestMethod.POST)
    public APIResult loginout(@RequestBody String account){
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForHash().delete("authenticationCache",account);
        return new APIResult(ConstantsUtils.CODE_STATUS_200,null,ConstantsUtils.CODE_STATUS_1,"退出成功");
    }


    @RequestMapping(value = "/reg",method = RequestMethod.POST)
    public APIResult reg(@RequestBody User user){

        try {
            userService.reg(user);
            return APIResult.created(user);
        } catch (Exception e) {
            e.printStackTrace();
            return APIResult.notFound(e.getMessage());
        }
    }
}
