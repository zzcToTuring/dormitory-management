package com.zzc.community.service.impl;

import com.zzc.community.mapper.AddressMapper;
import com.zzc.community.mapper.AddressResidentMapper;
import com.zzc.community.mapper.ResidentMapper;
import com.zzc.community.po.Address;
import com.zzc.community.po.AddressResident;
import com.zzc.community.po.Resident;
import com.zzc.community.service.ResidentService;
import com.zzc.community.utils.ConstantsUtils;
import com.zzc.community.utils.RegularExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @Auther: zzc
 * @Date: 2023-2-21 16:58
 * @Description:
 */
@Service
@Transactional
public class ResidentServiceImpl implements ResidentService {

    final ResidentMapper residentMapper;

    final AddressMapper addressMapper;

    final AddressResidentMapper addressResidentMapper;



    public ResidentServiceImpl(ResidentMapper residentMapper,
                               AddressMapper addressMapper,
                               AddressResidentMapper addressResidentMapper
                              ){
        this.residentMapper = residentMapper;
        this.addressMapper = addressMapper;
        this.addressResidentMapper = addressResidentMapper;
    }


    //选出所有的学生，增加过滤器，选出被标记的学生
    @Override
    public List<Resident> queryByresState() {
        List<Resident> collect = this.residentMapper.selectAll(null,null,null).stream()
                .filter(res -> res.getResState() == 1 || res.getResState() ==2 ||res.getResState() ==3 ||res.getResState() ==4||res.getResState() ==5||res.getResState() ==6)
                .collect(Collectors.toList());
        return collect;
    }

    //原功能不能
    @Override
    public List<Resident> queryAll_filter(String resName, Integer house, Integer unit) {
        List<Resident> collect = this.residentMapper.selectAll(resName,house,unit).stream()
                //状态码不为0的过滤
                .filter(res -> res.getResState() != 0)
                .filter(res -> res.getResStatus() !=1)
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public Resident editResident(Resident resident) throws Exception {
        //验证数据格式
        verification(resident);
        //验证地址是否存在
        String name = resident.getName();
        Integer house = resident.getHouse();
        Integer unit = resident.getUnit();
        Address newAddress = addressMapper.selectByhouseAndUnitAndName(name, house, unit);
        if (newAddress == null){
            throw new Exception("这个地址不存在，请重新输入");
        }
        //修改住户信息
        residentMapper.updateResident(resident);

        //住户人数修改
        //1.先查询原地址，根据用户id，查询出原地址Id
        AddressResident addressResident = addressResidentMapper.selectByresidentId(resident.getResId());
        //原地址操作
        try{
            Address oldAddress = addressMapper.findByAddressId(addressResident.getAddressId());
            String oldName = oldAddress.getName();
            Integer oldHouse = oldAddress.getHouse();
            Integer oldUnit = oldAddress.getUnit();
            if(!(name.equals(oldName) && house == oldHouse && unit == oldUnit)){
                //原地址减一
                addressMapper.deleteResidentCount(oldAddress);
                //新地址加一
                addressMapper.addResidentCount(newAddress);
                //修改地址信息
                //1.先删除关联
                addressResidentMapper.deleteResidentAddress(resident.getResId());
                //2.添加关联
                addressResidentMapper.addResidentAddress(newAddress.getAddressId(),resident.getResId());
            }
        }catch (Exception e){
            //如果报错了，没有添加学生信息为空，空指针异常
            //就可以直接跳过旧地址操作，直接进行新地址操作
            //新地址+1
            addressMapper.addResidentCount(newAddress);
            //绑定关联表
            addressResidentMapper.addResidentAddress(newAddress.getAddressId(),resident.getResId());
        }

        return resident;
    }

    @Override
    public Resident upRemove(Integer resId,Integer code) {
        Resident resident = residentMapper.findByResIdResident(resId);
        if(code == 1){
            resident.setResStatus(ConstantsUtils.CODE_STATUS_1);
        }else {
            resident.setResStatus(ConstantsUtils.CODE_STATUS_0);
        }
        residentMapper.updateResident(resident);
        return resident;
    }

    @Override
    public int resUpBatch(List<Integer> ids,Integer code) {

        int i = residentMapper.upBatchRes(ids,code);
        return i;
    }

    @Override
    public int editResState(Integer id) {

        int i = residentMapper.updateResStateByResId(id);
        return i;
    }


    @Override
    public List<Resident> queryAll(String resName, Integer house, Integer unit) {
        List<Resident> collect = this.residentMapper.selectAll(resName,house,unit).stream()
                .filter(res -> resName == null || resName == "" || res.getResName().equals(resName))
                .filter(res -> house == null  || res.getHouse().equals(house))
                .filter(res -> unit == null  || res.getUnit().equals(unit))
                //状态码不为0的过滤
                .filter(res -> res.getResState() == 0)
                .filter(res -> res.getResStatus() != 1)
                .collect(Collectors.toList());
        System.out.println("queryAll================="+collect);
        return collect;
    }

    @Override
    public List<Resident> getAll() {
        List<Resident> collect = this.residentMapper.selectAll(null,null,null).stream()
                .filter(res -> res.getResStatus() != 1)
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public Resident saveResident(Resident resident) throws Exception {
        //验证数据格式
        verification(resident);
        //添加住户
        residentMapper.addResident(resident);
        //添加地址
        Address address = new Address(resident.getName(),resident.getHouse(),resident.getUnit());
        Address addfront = addressMapper.selectByhouseAndUnitAndName(address.getName(),address.getHouse(), address.getUnit());

        if(addfront !=null){
            addressMapper.addResidentCount(addfront);
        }else {
           throw new Exception("这个地址不存在，请确认后填写！！");
        }
        //查询id
        Address addr = addressMapper.selectByhouseAndUnitAndName(address.getName(),address.getHouse(), address.getUnit());
        //添加关系
        addressResidentMapper.addResidentAddress(addr.getAddressId(),resident.getResId());
        return resident;
    }

    private void verification(Resident resident) throws Exception {
        //判断逻辑
        //测试
        //判断住户姓名
        if (resident.getResName() == null){
            throw new Exception("您输入有误，姓名不能为空");
        }
        if(resident.getResName().length() < 1 || resident.getResName().length() > 4){
            throw new Exception("您输入有误，姓名字符长度只能是2-4个");
        }
        //判断手机号
        if (!RegularExpression.decidePhone(resident.getResPhone())){
            throw new Exception("您输入有误，手机号格式输入有误");
        }
        //判断社区名称
        if(resident.getName() == null){
            throw new Exception("您输入有误，校区必须选择一个");
        }
        //楼号判断
        if (resident.getHouse() == null){
            throw new Exception("您输入有误，您楼栋填写为空，请正确输入");
        }
        //单元号判断
        if(resident.getUnit() == null){
            throw new Exception("您输入有误，您宿舍号填写为空，请正确输入");
        }
    }

}
