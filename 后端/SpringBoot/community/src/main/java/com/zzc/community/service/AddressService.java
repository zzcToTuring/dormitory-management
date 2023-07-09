package com.zzc.community.service;

import com.zzc.community.po.Address;

import java.util.List;

public interface AddressService {



    /**
     * 功能描述: 按条件查询所有地址
     * @param: [name, house, unit]
     * @return: java.util.List<com.zzc.community.po.Address>
     * @date: 2023-3-14 19:42
     */
    List<Address> queryAll(String name, Integer house, Integer unit);

    /**
     * 功能描述: 添加地址
     * @param: [address]
     * @return: com.zzc.community.po.Address
     * @date: 2023-3-14 19:43
     */
    Address addAddress(Address address) throws Exception;
}
