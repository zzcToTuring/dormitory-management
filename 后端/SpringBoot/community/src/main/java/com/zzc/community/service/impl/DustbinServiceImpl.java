package com.zzc.community.service.impl;

import com.zzc.community.mapper.*;
import com.zzc.community.po.*;
import com.zzc.community.service.DustbinService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DustbinServiceImpl implements DustbinService {

    final ResidentInFoMapper residentInFoMapper;

    final ResidentMapper residentMapper;

    final VisitorMapper visitorMapper;

    final AddressMapper addressMapper;

    final AddressResidentMapper addressResidentMapper;

    public DustbinServiceImpl(ResidentInFoMapper residentInFoMapper, ResidentMapper residentMapper, VisitorMapper visitorMapper, AddressMapper addressMapper, AddressResidentMapper addressResidentMapper) {
        this.residentInFoMapper = residentInFoMapper;
        this.residentMapper = residentMapper;
        this.visitorMapper = visitorMapper;
        this.addressMapper = addressMapper;
        this.addressResidentMapper = addressResidentMapper;
    }


    @Override
    public List<ResidentInFo> queryAllResidentInFo(String resName) {
        List<ResidentInFo> collect = this.residentInFoMapper.findAll(resName, null, null).stream()
                .filter(info -> resName == null || resName.equals("") || info.getResName().equals(resName))
                .filter( info -> info.getInfoStatus() != 0)
                .collect(Collectors.toList());
        System.out.println(collect);
        return collect;
    }

    @Override
    public List<Resident> queryAllRes(String resName) {
        List<Resident> collect = this.residentMapper.selectAll(resName,null,null).stream()
                .filter(res -> resName == null || resName == "" || res.getResName().equals(resName))
                .filter(res -> res.getResStatus() != 0)
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<Visitor> queryAllVisitor(String visName) {
        List<Visitor> collect = this.visitorMapper.findAll(visName,null,null).stream()
                .filter(v -> visName == null || visName == "" || v.getVisName().equals(visName))
                .filter(v -> v.getVisStatus() != 0)
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public int removeVisitor(List<Integer> ids) {
        return visitorMapper.deleteByVisitorIdInt(ids);
    }

    @Override
    public int removeResident(List<Integer> ids) {
        //徐娜换循环删除住户的数量   住户和地址的关系
        for(int i =0; i<ids.size(); i++){
            AddressResident addressResident = addressResidentMapper.selectByresidentId(ids.get(i));
            Address byAddressId = addressMapper.findByAddressId(addressResident.getAddressId());
            addressMapper.deleteResidentCount(byAddressId);
            addressResidentMapper.deleteResidentAddress(ids.get(i));
        }
        return residentMapper.deleteByResIdInt(ids);
    }

    @Override
    public int removeResidentInfo(List<Integer> ids) {
        return residentInFoMapper.deleteByResInfoIdInt(ids);
    }
}
