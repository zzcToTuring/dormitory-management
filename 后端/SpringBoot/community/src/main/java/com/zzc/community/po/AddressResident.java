package com.zzc.community.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Auther: zzc
 * @Date: 2023-2-21 15:56
 * @Description:
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AddressResident implements Serializable {
    private Integer addResId;
    private Integer addressId;
    private Integer resId;
}
