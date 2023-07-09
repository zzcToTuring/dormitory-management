package com.zzc.community;

import com.zzc.community.mapper.UserMapper;
import com.zzc.community.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {

        User user = userMapper.findByAccountMenus("tom");
        System.out.println("-----------------------"+user);

    }

}
