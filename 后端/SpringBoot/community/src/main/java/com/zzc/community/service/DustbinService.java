package com.zzc.community.service;

import com.zzc.community.po.Resident;
import com.zzc.community.po.ResidentInFo;
import com.zzc.community.po.Visitor;

import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-3-17 17:48
 * @Description:
 */
public interface DustbinService {

    List<ResidentInFo> queryAllResidentInFo(String resName);

    List<Resident> queryAllRes(String resName);

    List<Visitor> queryAllVisitor(String visName);

    int removeVisitor(List<Integer> ids);

    int removeResident(List<Integer> ids);

    int removeResidentInfo(List<Integer> ids);
}
