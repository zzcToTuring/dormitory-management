package com.zzc.community.mapper;

import com.zzc.community.po.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-2-21 16:04
 * @Description:
 */
@Mapper
@Component
public interface AddressMapper {

    //添加地址
    int addAddress(Address address);

    //添加用户的数量
    int addResidentCount(Address address);

    //减少用户数量
    int deleteResidentCount(Address address);

    //查询地址
    Address selectByhouseAndUnit(@Param("house") Integer house,@Param("unit") Integer unit);

    //查询地址
    Address selectByhouseAndUnitAndName(@Param("name") String name,@Param("house") Integer house,@Param("unit") Integer unit);



    //查询所有地址
    List<Address> selectAll(@Param("name") String name,@Param("house") Integer house,@Param("unit") Integer unit);


    //根据住户地址的id查询
    Address findByAddressId(Integer addressId);

    int updateAddress(Address address);



}
