package com.zzc.community.mapper;

import com.zzc.community.po.AddressResident;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Auther: zzc
 * @Date: 2023-2-21 16:05
 * @Description:
 */
@Mapper
@Component
public interface AddressResidentMapper {

    //添加关系
    int addResidentAddress(@Param("addressId") Integer addressId, @Param("resId") Integer resId);

    //删除关系

    int deleteResidentAddress(@Param("resId") Integer resId);

    AddressResident selectByresidentId(Integer resId);

}
