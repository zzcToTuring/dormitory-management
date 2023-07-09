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
 * @Date: 2023-2-21 15:52
 * @Description:
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Resident extends Address implements Serializable {
    private Integer resId;
    private String resName;
    private Integer resSex;
    private String resPhone;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date resIntotime;
    private String resRemarks;
    private Integer resState;
    private Integer resStatus;





}
