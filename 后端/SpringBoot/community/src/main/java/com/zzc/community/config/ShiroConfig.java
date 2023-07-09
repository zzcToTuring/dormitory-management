package com.zzc.community.config;

import com.zzc.community.shiro.realm.MyShiroRealm;
import com.zzc.community.shiro.cache.RedisCacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zzc
 * @Date: 2023-2-11 16:57
 * @Description:
 */
@Configuration
public class ShiroConfig {

    //1.创建shiroFilter
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //置登录url
        shiroFilterFactoryBean.setLoginUrl("/login");
//        设置主页url
        shiroFilterFactoryBean.setSuccessUrl("/home");
        //配置公共资源
        //配置受限资源
        Map<String, String> map = new HashMap<String, String>();
        map.put("/login","anon");
        map.put("/reg","anon");
        map.put("/home","anon");
        map.put("/user/**","anon");
        map.put("/user/login","anon");
        map.put("/code/**","anon");
        map.put("/dustbin/**","anon");
        map.put("/echarts/**","anon");
        map.put("/residentInfo/**","anon");
        map.put("/vis/**","anon");
        map.put("/file/**","anon");
        map.put("/resident/**","anon");
        map.put("/address/**","anon");
        map.put("/user/reg","anon");
        map.put("/user/loginout","anon");
        map.put("/**","authc");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm myShiroRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器
        defaultWebSecurityManager.setRealm(myShiroRealm);
        return defaultWebSecurityManager;
    }

    @Bean
    public Realm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        //修改凭证效验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为md5 加密
        credentialsMatcher.setHashAlgorithmName("md5");
        //设置散列次数\
        credentialsMatcher.setHashIterations(1024);
        myShiroRealm.setCredentialsMatcher(credentialsMatcher);

        //开启全局缓存
        myShiroRealm.setCacheManager(new RedisCacheManager());
        myShiroRealm.setCachingEnabled(true);//开启全局管理
        myShiroRealm.setAuthenticationCachingEnabled(true);//认证缓存
        myShiroRealm.setAuthenticationCacheName("authenticationCache");
        myShiroRealm.setAuthorizationCachingEnabled(true);//授权缓存
        myShiroRealm.setAuthorizationCacheName("authorizationCache");
        return myShiroRealm;
    }

}
