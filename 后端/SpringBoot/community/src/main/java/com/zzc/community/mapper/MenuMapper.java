package com.zzc.community.mapper;

import com.zzc.community.po.Menu;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-3-2 11:21
 * @Description:
 */
@Mapper
@Component
public interface MenuMapper {


    //根据角色查询资源
    List<Menu> findAll(String roleName);

    List<Menu> findMenuByRoleId(Integer roleId);


    //根据菜单id查询
    Menu findByparentId(Integer parentId);


}
