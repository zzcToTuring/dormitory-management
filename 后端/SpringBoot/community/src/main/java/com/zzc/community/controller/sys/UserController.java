package com.zzc.community.controller.sys;


import com.zzc.community.po.User;
import com.zzc.community.service.UserService;
import com.zzc.community.utils.APIResult;
import com.zzc.community.utils.ConstantsUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Auther: zzc
 * @Date: 2023-2-12 13:33
 * @Description:
 */
@RequestMapping("/user")
@RestController
public class UserController {

   final UserService userService;


   public UserController(UserService userService){
       this.userService =userService;
   }


   /**
    * 功能描述: 软删除
    * @param: [userId]
    * @return: com.zzc.community.utils.APIResult
    * @auther: wanzg
    * @date: 2023-3-9 14:36
    */
    @RequestMapping(value = "/editStatus/{id}",method = RequestMethod.POST)
    public APIResult remove(@PathVariable("id")  Integer id){
        User user = userService.removeUpdateByUserid(id);
        if (user != null){
            return new APIResult(ConstantsUtils.CODE_STATUS_201,null,1,user);
        }else {
            return new APIResult(ConstantsUtils.CODE_STATUS_404,"资源不存在",-1,null);
        }

    }

    //直接删除
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public APIResult delete(@PathVariable("id") Integer id){
        try {
            boolean aBoolean = userService.deleteById(id);
            APIResult.ok(aBoolean);
        } catch (Exception e) {
            APIResult.notFound(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public APIResult getAll(String account, String userName, String userPhone){
        List<User> users = this.userService.queryAllUser(account, userName, userPhone);
        APIResult apiResult = new APIResult(ConstantsUtils.CODE_STATUS_200,null,users.size(),users);
        return apiResult;
    }

    @RequestMapping(value = "/resetPwd/{account}",method = RequestMethod.POST)
    public APIResult resetPwd(@PathVariable("account") String account){
        userService.resetPassword(account);
        return new APIResult(ConstantsUtils.CODE_STATUS_201,null,ConstantsUtils.CODE_STATUS_1,null);
    }

    @RequestMapping(value = "/getUser/{account}",method = RequestMethod.GET)
    public APIResult getUser(@PathVariable("account") String account){
        User user = userService.findUserByAccount(account);
        user.setPassword(null);
        return new APIResult(ConstantsUtils.CODE_STATUS_200,null,1,user);
    }


    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public APIResult edit(@RequestBody User user){
        User user1 = userService.editUser(user);
        return new APIResult(ConstantsUtils.CODE_STATUS_201,null,1,user1);
    }

    @RequestMapping(value = "/editPwd",method = RequestMethod.POST)
    public APIResult editPassword(@RequestBody User user){
        try {
            User user1 = userService.editPwd(user);
            return new APIResult(ConstantsUtils.CODE_STATUS_201,null,ConstantsUtils.CODE_STATUS_1,user1);
        } catch (Exception e) {
            return new APIResult(ConstantsUtils.CODE_STATUS_404,e.getMessage(),null,null);

        }
    }


}
