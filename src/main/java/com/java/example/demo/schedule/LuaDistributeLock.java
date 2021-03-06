package com.java.example.demo.schedule;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import com.java.example.demo.service.RedisService;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Service
public class LuaDistributeLock {


    private static final Logger logger = LoggerFactory.getLogger(LockNxExJob.class);

    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate redisTemplate;

    private static String LOCK_PREFIX = "lua_";

    private DefaultRedisScript<Boolean> lockScript;


    //@Scheduled(cron = "0/10 * * * * *")
    public void lockJob() {

        String lock = LOCK_PREFIX + "LockNxExJob";

        boolean luaRet = false;
        try {
            luaRet = luaExpress(lock,getHostIp());

            if (!luaRet) {
                String value = (String) redisService.genValue(lock);
                logger.info("lua get lock fail,lock belong to:{}", value);
                return;
            } else {
                logger.info("lua start  lock lockNxExJob success");
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            logger.error("lock error", e);

        } finally {
            if (luaRet) {
                logger.info("release lock success");
                redisService.remove(lock);
            }
        }
    }


    /**
     * 获取lua结果
     * @param key
     * @param value
     * @return
     */
    public Boolean luaExpress(String key,String value) {
        lockScript = new DefaultRedisScript<Boolean>();
        lockScript.setScriptSource(
                new ResourceScriptSource(new ClassPathResource("add.lua")));
        lockScript.setResultType(Boolean.class);
        List<Object> keyList = new ArrayList<Object>();
        keyList.add(key);
        keyList.add(value);
        Boolean result = (Boolean) redisTemplate.execute(lockScript, keyList);
        return result;
    }


    /**
     * 获取本机内网IP地址方法
     *
     * @return
     */
    private static String getHostIp() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress ip = (InetAddress) addresses.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
                            && ip.getHostAddress().indexOf(":") == -1) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
