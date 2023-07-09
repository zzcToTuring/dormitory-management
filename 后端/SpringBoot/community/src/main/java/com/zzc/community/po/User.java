package com.zzc.community.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-2-11 15:50
 * @Description: 
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class User  implements Serializable{
    private Integer userId;
    private String account;
    private String password;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userAddress;
    private String codeText;
    private Integer status;
    private String head;
    private String roleName;
    private Integer roleId;

    private String newPassword;
    //角色信息
    private List<Role> roles;

    private String confirmPassword;

    private List<Menu> menus;
}
