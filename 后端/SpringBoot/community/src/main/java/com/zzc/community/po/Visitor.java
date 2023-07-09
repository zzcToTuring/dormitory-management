package com.zzc.community.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Auther: zzc
 * @Date: 2023-2-24 14:49
 * @Description:
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Visitor implements Serializable{

    private Integer visitorId;

    private Integer addressId;

    private String visName;

    private String visPhone;

    private String visRemark;

    private String visAddress;

    private Integer visHelthCode;

    private String temperature;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date visTime;

    private Integer visType;

    private Integer visStatus;

    private String name;

    private Integer house;

    private Integer unit;
}
