package com.zzc.community.controller.sys;


import com.zzc.community.po.Code;
import com.zzc.community.service.CodeService;
import com.zzc.community.utils.APIResult;
import com.zzc.community.utils.ConstantsUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-2-15 11:07
 * @Description: 注册码
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/code")
public class CodeController {

    final CodeService codeService;

    public CodeController( CodeService codeService){
        this.codeService = codeService;
    }


    @RequestMapping(value = "/delete" ,method = RequestMethod.DELETE)
    public APIResult delete(Integer codeId){
        Code code = codeService.updateCodeStatusBycodeId(codeId);
        if (code != null) {
            return APIResult.ok(code);
        } else {
            return APIResult.notFound("要删除的资源不存在");
        }
    }

    @RequestMapping(value = "/remove",method = RequestMethod.DELETE)
    public APIResult remove(Integer codeId){
        Code code = codeService.remove(codeId);
        if (code != null) {
            return APIResult.ok(code);
        } else {
            return APIResult.notFound("要删除的资源不存在");
        }
    }

    @RequestMapping(value = "/addCode",method = RequestMethod.POST)
    public APIResult addCode(@RequestBody String roleName){
        codeService.addCode(roleName);
        return new APIResult(ConstantsUtils.CODE_STATUS_201,null,1,"添加成功");
    }

    @RequestMapping(value = "getAll",method = RequestMethod.GET)
    public APIResult getAll(String roleName,String userName,String account){
        List<Code> codes = this.codeService.queryAll(roleName, userName, account);
        return new APIResult(ConstantsUtils.CODE_STATUS_200,null,codes.size(),codes);

    }

    @RequestMapping(value = "/upDelete/{id}",method = RequestMethod.POST)
    public APIResult removeBycodeId(@PathVariable("id") Integer id){
        codeService.remove(id);
        return new APIResult(201,null,1,null);
    }


}
