package com.zzc.community.service.impl;

import com.zzc.community.mapper.AddressMapper;
import com.zzc.community.po.Address;
import com.zzc.community.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    final AddressMapper addressMapper;


    public AddressServiceImpl(AddressMapper addressMapper){
        this.addressMapper =addressMapper;
    }


    @Override
    public List<Address> queryAll(String name, Integer house, Integer unit) {
        List<Address> collect = this.addressMapper.selectAll(name,house,unit).stream()
                .filter(a->name==null || name == "" ||a.getName().equals(name))
                .filter(a->house==null ||a.getHouse().equals(house))
                .filter(a->unit==null ||a.getUnit().equals(unit))
                .collect(Collectors.toList());
        return  collect;
    }

    @Override
    public Address addAddress(Address address) throws Exception {
        //判断格式
        verification(address);
        String name = address.getName();
        Integer house = address.getHouse();
        Integer unit = address.getUnit();
        Address address1 = addressMapper.selectByhouseAndUnitAndName(name, house, unit);
        if (address1 !=null){
            throw new Exception("地址已经存在，请重新填写！！");
        }
        addressMapper.addAddress(address);
        return address;
    }
    
    private void verification(Address address) throws Exception {
        //判断社区名称
        if(address.getName() == null){
            throw new Exception("您输入有误，校区必须选择一个");
        }
        //楼号判断
        if (address.getHouse() == null){
            throw new Exception("您输入有误，您楼栋填写为空，请正确输入");
        }
        //单元号判断
        if(address.getUnit() == null){
            throw new Exception("您输入有误，您宿舍号填写为空，请正确输入");
        }
    }

}
