package com.zzc.community.service;

import com.zzc.community.po.Menu;

import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-3-11 11:00
 * @Description:
 */
public interface MenuService {

    List<Menu> findMenuByRoleName(String roleName);
}
