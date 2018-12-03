package com.megamusic.findshow.service;

import com.megamusic.findshow.common.utils.AliyunSmsUtils;
import com.megamusic.findshow.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * Created by chengchao on 2018/11/16.
 */
@Slf4j
@Service("smsService")
public class SmsService {

    @Autowired
    private RedisUtil redisUtil;

    public String sendVerifyCode(String phoneNo) {
        if(StringUtils.isEmpty(phoneNo))
            return null;

        String key = getSMSCodeCacheKey(phoneNo);
        String code = getRandomString(4);
        try {
            AliyunSmsUtils.sendSms(code,phoneNo);
        }catch (Exception e){
            log.error("短信发送失败，手机号：{}",phoneNo);
        }

        //放入缓存
        redisUtil.set(key,code,60);
        return code;

    }


    public boolean checkVerifyCode(String phoneNo,String code) {
        if(StringUtils.isEmpty(phoneNo) || StringUtils.isEmpty(code))
            return false;
        String key = getSMSCodeCacheKey(phoneNo);
        Object cacheCode = redisUtil.get(key);
        if(cacheCode==null)
            return false;
        if(code.equals(cacheCode.toString()))
            return true;
        else
            return false;
    }

    private String getSMSCodeCacheKey(String phoneNo){
        return  "fs_sms_code_"+phoneNo;
    }

    public static String getRandomString(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
