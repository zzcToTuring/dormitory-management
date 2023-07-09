package com.zzc.community.service;

import com.zzc.community.po.ResidentInFo;

import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-3-11 17:46
 * @Description:
 */
public interface ResidentInFoService {

    List<ResidentInFo> queryAll(String resName,String name ,Integer house);

    ResidentInFo addResInfo(ResidentInFo residentInFo) throws Exception;

    ResidentInFo addResInfo_Temp(ResidentInFo residentInFo) throws Exception;

    int upRemove(Integer resInfoId,Integer code);

    int resInfoUpBatch(List<Integer> ids,Integer code);


}
