package com.zzc.community.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Auther: zzc
 * @Date: 2023-2-18 19:46
 * @Description:
 */
@Mapper
@Component
public interface UserRoleMapper {

    void add(Integer userId,Integer roleId);

    int deleteByuserId(Integer userId);
}
