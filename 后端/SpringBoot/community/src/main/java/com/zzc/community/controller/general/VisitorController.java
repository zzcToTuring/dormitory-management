package com.zzc.community.controller.general;

import com.zzc.community.po.Visitor;
import com.zzc.community.service.VisitorService;
import com.zzc.community.utils.APIResult;
import com.zzc.community.utils.ConstantsUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-2-24 15:49
 * @Description:
 */

@RestController
@RequestMapping("/vis")
public class VisitorController {

    final VisitorService visitorService;

    public VisitorController(VisitorService visitorService){
        this.visitorService = visitorService;
    }



    @RequestMapping(value = "addVis", method = RequestMethod.POST)
    public APIResult addVisitor(@RequestBody Visitor visitor){
        try {
            Visitor visitor1 = visitorService.addVisitorInFoM(visitor);
            return  APIResult.created(visitor1);
        } catch (Exception e) {
            e.printStackTrace();
            return APIResult.notFound(e.getMessage());
        }
    }

    @RequestMapping(value = "getAll",method = RequestMethod.GET)
    public APIResult getAll(String visName, Integer visHelthCode, Integer visType){
        List<Visitor> visitors = this.visitorService.queryAll(visName, visHelthCode, visType);
        return new APIResult(ConstantsUtils.CODE_STATUS_200,null,visitors.size(),visitors);

    }

    @RequestMapping(value = "/upDelete/{id}",method = RequestMethod.POST)
    public APIResult upDelete(@PathVariable("id") Integer id){
        visitorService.upRemove(id,ConstantsUtils.CODE_STATUS_1);
        return new APIResult(ConstantsUtils.CODE_STATUS_201,null,1,null);
    }

    @RequestMapping(value = "/upDelete/batch",method = RequestMethod.POST)
    public APIResult visitorInfoBatch(@RequestBody Integer[] ids){
        List<Integer> ides = Arrays.asList(ids);
        visitorService.visitorUpBatch(ides,ConstantsUtils.CODE_STATUS_1);
        return new APIResult(201,null,ides.size(),null);
    }
}
