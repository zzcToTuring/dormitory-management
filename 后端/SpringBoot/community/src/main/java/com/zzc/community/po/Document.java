package com.zzc.community.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @Auther: zzc
 * @Date: 2023-3-19 11:40
 * @Description:
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Document implements Serializable {
    private Integer id;
    private String oldName;
    private String newName;
    private String type;
    private Long size;
    private String url;
    private String md5;
    private Integer status;
    private Integer enable;
}
