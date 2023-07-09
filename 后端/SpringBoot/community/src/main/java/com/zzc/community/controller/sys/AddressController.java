package com.zzc.community.controller.sys;


import com.zzc.community.po.Address;
import com.zzc.community.service.AddressService;
import com.zzc.community.utils.APIResult;
import com.zzc.community.utils.ConstantsUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-2-23 11:07
 * @Description: 地址管理
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public APIResult getAll(String name, Integer house, Integer unit) {
        List<Address> addresses = this.addressService.queryAll(name, house, unit);
        APIResult apiResult = new APIResult(ConstantsUtils.CODE_STATUS_200,null,addresses.size(),addresses);
        return apiResult;
    }

    @RequestMapping(value = "/addAddress",method = RequestMethod.POST)
    public APIResult add(@RequestBody Address address){
        try {
            Address address1 = addressService.addAddress(address);
            return new APIResult(ConstantsUtils.CODE_STATUS_201,null,1,address1);
        }
        catch (Exception e){
            return new APIResult(ConstantsUtils.CODE_STATUS_404,e.getMessage(),-1,null);
        }

    }

}
