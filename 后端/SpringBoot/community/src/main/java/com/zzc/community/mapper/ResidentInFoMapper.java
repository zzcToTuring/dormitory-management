package com.zzc.community.mapper;

import com.zzc.community.po.ResidentInFo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-3-11 17:23
 * @Description:
 */
@Component
@Mapper
public interface ResidentInFoMapper {

    List<ResidentInFo> findAll(@Param("resName") String resName, @Param("name") String name, @Param("house") Integer house);

    void addResidentInFo(ResidentInFo residentInFo);

    int updateResInfo(@Param("resInfoId")Integer resInfoId,@Param("infoStatus") Integer  infoStatus);

    int upBatchRes(@Param("ids") List<Integer> ids,@Param("infoStatus") Integer  infoStatus);

    int deleteByResInfoIdInt(List<Integer> ids);
}
