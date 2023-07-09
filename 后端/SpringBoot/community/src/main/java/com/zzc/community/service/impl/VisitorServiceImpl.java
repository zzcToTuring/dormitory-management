//来访登记页面 有与之相对应的mapper界面
package com.zzc.community.service.impl;

import com.zzc.community.mapper.AddressMapper;
import com.zzc.community.mapper.VisitorMapper;
import com.zzc.community.po.Address;
import com.zzc.community.po.Visitor;
import com.zzc.community.service.VisitorService;
import com.zzc.community.utils.RegularExpression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: zzc
 * @Date: 2023-2-24 15:46
 * @Description:
 */
@Service
@Transactional
public class VisitorServiceImpl implements VisitorService {

    final VisitorMapper visitorMapper;

    final AddressMapper addressMapper;



    public VisitorServiceImpl(VisitorMapper visitorMapper,
                              AddressMapper addressMapper){
        this.visitorMapper = visitorMapper;
        this.addressMapper = addressMapper;
    }

    @Override
    public Visitor addVisitorInFoM(Visitor visitor) throws Exception {
        //验证格式是否正确
        verification(visitor);
        Address address = addressMapper.selectByhouseAndUnitAndName(visitor.getName(), visitor.getHouse(), visitor.getUnit());

        if (address == null){
            throw new Exception("您输入有误，输入的访问地址没有，请重新输入");
        }
        visitor.setAddressId(address.getAddressId());
        visitorMapper.save(visitor);
        return visitor;
    }

    @Override
    public List<Visitor> queryAll(String visName, Integer visHelthCode, Integer visType) {
        List<Visitor> collect = this.visitorMapper.findAll(visName,visHelthCode,visType).stream()
                .filter(v -> visName == null || visName == "" || v.getVisName().equals(visName))
                .filter(v -> visHelthCode == null || v.getVisHelthCode().equals(visHelthCode))
                .filter(v -> visType == null || v.getVisType().equals(visType))
                .filter(v -> v.getVisStatus() != 1)
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<Visitor> getAll() {
        List<Visitor> collect = this.visitorMapper.findAll(null,null,null).stream()
                .filter(v -> v.getVisStatus() != 1)
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public int upRemove(Integer visitorId,Integer code) {
        return visitorMapper.updateVisStatusByResId(visitorId,code);
    }

    @Override
    public int visitorUpBatch(List<Integer> ids,Integer code) {
        return visitorMapper.upBatchVis(ids,code);
    }

    private void  verification(Visitor visitor) throws Exception {
        //判断访客姓名
        if (visitor.getVisName() == null){
            throw new Exception("您输入有误，访客姓名不能为空");
        }
        //判断手机格式
        if (!RegularExpression.decidePhone(visitor.getVisPhone())){
            throw new Exception("您输入有误，手机号格式不对，");
        }
        //访客地址
        if(visitor.getVisAddress() == null){
            throw new Exception("您输入有误，地址不能为空，");
        }
        if (visitor.getHouse() == null){
            throw new Exception("您输入有误，楼号不能为空，");
        }

        if(visitor.getUnit() == null){
            throw new Exception("您输入有误，单元号不能为空，");
        }
    }
}
