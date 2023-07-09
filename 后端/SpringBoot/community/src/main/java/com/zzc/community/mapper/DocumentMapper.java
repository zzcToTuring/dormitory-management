package com.zzc.community.mapper;

import com.zzc.community.po.Document;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: zzc
 * @Date: 2023-3-19 12:12
 * @Description:
 */
@Mapper
@Component
public interface DocumentMapper {

    int addDocument(Document document);

    List<Document> findBymd5(String md5);

    Document findByNewNameDocument(String newFilename);

    List<Document> findAllFile(String oldName);

    int updateEnableInt(@Param("id") Integer id,@Param("enable") Integer enable);

    Document findAllByIdDocument(Integer id);


    int deleteByIdInt(Integer id);

    //批量修改
    int upBatchVis(List<Integer> ids);
}
