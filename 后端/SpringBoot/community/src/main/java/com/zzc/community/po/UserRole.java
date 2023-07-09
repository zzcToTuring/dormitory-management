package com.zzc.community.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: zzc
 * @Date: 2023-2-11 15:54
 * @Description:
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {
    private Integer userRoleId;
    private Integer userId;
    private Integer roleId;
}
