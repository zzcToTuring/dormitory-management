package com.zzc.community.scheduled;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;

/**
 * @Auther: zzc
 * @Date: 2023-3-19 14:26
 * @Description:
 */

//@Component
public class Scheduler {
    // 从配置文件中读取一个值
    @Value("${spring.servlet.multipart.location}")
    private  String folderPath;

    @Scheduled(cron="0/5 * * * * ?")
    public  void  testTasks(){
        File file=new File(folderPath);
        File[] files = file.listFiles();
        File temp=null;
        for(int i=0;i<files.length;i++){
            temp=files[i];
            //是否是文件
                //文件以什么为结尾
                if(temp.getName().endsWith(".tmp")){
                    temp.delete();
                }
        }
    }
}
