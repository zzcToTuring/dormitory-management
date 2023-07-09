package com.zzc.community.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.zzc.community.po.Address;
import com.zzc.community.po.Resident;
import com.zzc.community.po.Visitor;
import com.zzc.community.service.AddressService;
import com.zzc.community.service.ResidentService;
import com.zzc.community.service.VisitorService;
import com.zzc.community.utils.APIResult;
import com.zzc.community.utils.ConstantsUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;


/**
 * @Auther: zzc
 * @Date: 2023-3-26 14:11
 * @Description:
 */
@RequestMapping("/echarts")
@RestController
public class EchartsController {

    final ResidentService residentService;
    final VisitorService visitorService;
    final AddressService addressService;

    public EchartsController(ResidentService residentService, VisitorService visitorService, AddressService addressService) {
        this.residentService = residentService;
        this.visitorService = visitorService;
        this.addressService = addressService;
    }

    @GetMapping("/members")
    public APIResult members() {
        List<Resident> list = residentService.queryAll(null,null,null);
        int q1 = list.size(); // 正常
        List<Resident> residents = residentService.queryByresState();
        int q2 = residents.size();
        return new APIResult(ConstantsUtils.CODE_STATUS_200,null,list.size(),CollUtil.newArrayList(q1, q2));
    }

    @RequestMapping(value = "/vis",method = RequestMethod.GET)
    public APIResult getVis(){
        List<Visitor> list = visitorService.queryAll(null, null, null);
        int q1 = 0; // 第一季度
        int q2 = 0; // 第二季度
        int q3 = 0; // 第三季度
        int q4 = 0; // 第四季度
        for (Visitor visitor : list) {
            Date visTime = visitor.getVisTime();
            Quarter quarter = DateUtil.quarterEnum(visTime);
            switch (quarter) {
                case Q1: q1 += 1; break;
                case Q2: q2 += 1; break;
                case Q3: q3 += 1; break;
                case Q4: q4 += 1; break;
                default: break;
            }
        }
        return new APIResult(ConstantsUtils.CODE_STATUS_200,null,list.size(),CollUtil.newArrayList(q1, q2, q3, q4));
    }

    @RequestMapping(value = "/getFour",method = RequestMethod.GET)
    public APIResult getFour(){
        //住户总体人数
        List<Resident> list1 = residentService.getAll();
        int number1=list1.size();
        //访客数量
        List<Visitor> list2 = visitorService.getAll();
        int number2 =list2.size();
        //中高风险地区人数
        List<Resident> list3 = residentService.queryByresState();
        int number3 = list3.size();
        //房间数量
        List<Address> list4 = addressService.queryAll(null, null, null);
        int number4 = list4.size();
        return new APIResult(ConstantsUtils.CODE_STATUS_200,null,null,CollUtil.newArrayList(number1, number2, number3, number4));
    }

}
