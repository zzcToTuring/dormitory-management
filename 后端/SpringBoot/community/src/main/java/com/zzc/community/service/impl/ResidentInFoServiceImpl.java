//residentAccess  //(原-住户进出) 寝室报修业务
package com.zzc.community.service.impl;

import com.zzc.community.mapper.AddressMapper;
import com.zzc.community.mapper.ResidentInFoMapper;

import com.zzc.community.mapper.ResidentMapper;
import com.zzc.community.po.Address;
import com.zzc.community.po.Resident;
import com.zzc.community.po.ResidentInFo;
import com.zzc.community.service.ResidentInFoService;
import com.zzc.community.utils.RegularExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: zzc
 * @Date: 2023-3-11 17:46
 * @Description:
 */
@Service
@Transactional
public class ResidentInFoServiceImpl implements ResidentInFoService {

    final ResidentInFoMapper residentInFoMapper;

    final ResidentMapper residentMapper;

    final AddressMapper addressMapper;

    public ResidentInFoServiceImpl(ResidentInFoMapper residentInFoMapper, ResidentMapper residentMapper, AddressMapper addressMapper) {
        this.residentInFoMapper = residentInFoMapper;
        this.residentMapper = residentMapper;
        this.addressMapper = addressMapper;
    }

    @Override
    public List<ResidentInFo> queryAll(String resName, String name , Integer house) {
        List<ResidentInFo> collect = this.residentInFoMapper.findAll(resName, name, house).stream()
                .filter(info -> resName == null || resName.equals("") || info.getResName().equals(resName))
                .filter(info -> name == null || name.equals("") || info.getName().equals(name))
                .filter(info -> house == null || info.getHouse() == house)
                .filter( info -> info.getInfoStatus() != 1)
                .collect(Collectors.toList());
        System.out.println(collect);
        return collect;
    }

    @Override
    public ResidentInFo addResInfo(ResidentInFo residentInFo) throws Exception {
        //验证格式
        verification(residentInFo);
        String name = residentInFo.getName();
        Integer house = residentInFo.getHouse();
        Integer unit = residentInFo.getUnit();
        Address address = addressMapper.selectByhouseAndUnitAndName(name, house, unit);
        if( address == null){
            throw new Exception("地址不存在，请重新输入");
        }
        residentInFo.setResAddressId(address.getAddressId());
        residentInFoMapper.addResidentInFo(residentInFo);
        //更新健康码信息
        Resident findResident = residentMapper.findByResNameAndResPhoneResident(residentInFo.getResName(), residentInFo.getResPhone());
        if(findResident == null){
            throw new Exception("姓名和电话在住户信息中不匹配");
        }
        Resident resident = new Resident();
        resident.setResState(residentInFo.getResState());
        resident.setResId(findResident.getResId());
        //修改逻辑之后没有新的更新项了，因此这个更新可以不需要了
        residentMapper.updateResident(resident);
        return residentInFo;
    }
    //addResInfo_Temp--->添加学生与报修平台同用一个方法，某些时刻会导致数据库异常，因此重写一个方法分开放
    @Override
    public ResidentInFo addResInfo_Temp(ResidentInFo residentInFo) throws Exception {
        //验证格式
        verification(residentInFo);
        String name = residentInFo.getName();
        Integer house = residentInFo.getHouse();
        Integer unit = residentInFo.getUnit();
        Address address = addressMapper.selectByhouseAndUnitAndName(name, house, unit);
        if( address == null){
            throw new Exception("地址不存在，请重新输入");
        }
        residentInFo.setResAddressId(address.getAddressId());
        residentInFoMapper.addResidentInFo(residentInFo);
        //查找相关的信息
        Resident findResident = residentMapper.findByResNameAndResPhoneResident(residentInFo.getResName(), residentInFo.getResPhone());
        if(findResident == null){
            throw new Exception("姓名和电话在住户信息中不匹配");
        }
        Resident resident = new Resident();
        resident.setResState(residentInFo.getResState());
        resident.setResId(findResident.getResId());
        return residentInFo;
    }

    @Override
    public int upRemove(Integer resInfoId,Integer code) {
        return residentInFoMapper.updateResInfo(resInfoId,code);
    }

    @Override
    public int resInfoUpBatch(List<Integer> ids,Integer code) {
        return residentInFoMapper.upBatchRes(ids,code);
    }

    private void verification(ResidentInFo residentInFo) throws Exception {
        //判断逻辑
        //测试
        //判断住户姓名
        if (residentInFo.getResName() == null){
            throw new Exception("您输入有误，住户名不能为空");
        }
        if(residentInFo.getResName().length() < 1 || residentInFo.getResName().length() > 4){
            throw new Exception("您输入有误，住户姓名字符长度只能是2-4个");
        }
        //判断手机号
        if (!RegularExpression.decidePhone(residentInFo.getResPhone())){
            throw new Exception("您输入有误，手机号格式输入有误");
        }
        //判断社区名称
        if(residentInFo.getName() == null){
            throw new Exception("您输入有误，校区必须选择一个");
        }
        //楼号判断
        if (residentInFo.getHouse() == null){
            throw new Exception("您输入有误，您楼栋填写为空，请正确输入");
        }
        //单元号判断
        if(residentInFo.getUnit() == null){
            throw new Exception("您输入有误，您宿舍号填写为空，请正确输入");
        }
    }
}
