package com.zzc.community.mapper;

import com.zzc.community.po.Resident;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-2-21 16:04
 * @Description:
 */
@Mapper
@Component
public interface ResidentMapper {

    //查询所有的居民(动态查询)
    List<Resident> findAll(Resident resident);

    List<Resident> selectAll(@Param("resName") String resName, @Param("house") Integer house, @Param("unit") Integer unit);

    //根据id查询
    Resident findByResIdResident(Integer resId);


    Resident findByResNameAndResPhoneResident(@Param("resName") String resName,@Param("resPhone") String resPhone);

    int addResident(Resident resident);

    Resident findByresName(String resName);

    List<Resident> selectByResState(Integer resState);

    int updateResident(Resident resident);

    //批量修改
    int upBatchRes(@Param("ids") List<Integer> ids,@Param("resStatus") Integer  resStatus);

    int updateResStateByResId(Integer resId);

    //批量删除
    int deleteByResIdInt(List<Integer> ids);
}
