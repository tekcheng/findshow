package com.megamusic.findshow.service;

import com.alibaba.fastjson.JSON;
import com.megamusic.findshow.common.utils.HttpClientUtils;
import com.megamusic.findshow.dao.UserRepository;
import com.megamusic.findshow.domain.dto.UserDto;
import com.megamusic.findshow.domain.entity.User;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * Created by chengchao on 2018/9/26.
 */
@Slf4j
@Service("userService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${wx_appid}")
    private String appId;

    @Value("${wx_secret}")
    private String appSecret;

    /**
     * 微信登录
     */
    public UserDto wxLogin(String code) {
        UserDto userDto = new UserDto();

        //授权获取access_token
        String authUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
        String wxResult;
        try {
            wxResult = HttpClientUtils.doGet(authUrl);
        }catch (Exception e){
            log.error("[微信登录] 授权请求异常。",e);
            return null;
        }

        HashMap<String,String> resultMap = JSON.parseObject(wxResult, HashMap.class);
        String accessToken = resultMap.get("access_token");
        String openId  = resultMap.get("openid");
        if(StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(openId) ){
            log.error("[微信登录] 获取access_token失败，code:{} ，response:{}",code,wxResult);
            return null;
        }

        //获取用户信息
        String getUserUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
        String getUserResult;
        try {
            getUserResult = HttpClientUtils.doGet(getUserUrl);
        }catch (Exception e){
            log.error("[微信登录] 获取用户信息异常。",e);
            return null;
        }

        HashMap<String,String> userInfo = JSON.parseObject(getUserResult, HashMap.class);
        String avatar = userInfo.get("headimgurl");
        String nickName  =  EmojiParser.parseToAliases(userInfo.get("nickname"));
        if(StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(openId) ){
            log.error("[微信登录] 获取access_token失败，code:{} ，response:{}",code,wxResult);
            return null;
        }

        //判断用户是否注册
        User user = userRepository.findByOpenid(openId);
        if(user!=null){
            userDto.setAvatar(user.getAvatar());
            userDto.setNickName(EmojiParser.parseToUnicode(user.getNickName()));
            userDto.setUserId(user.getId().toString());
            return userDto;
        }

        //注册
        User addUser = new User();
        addUser.setAvatar(avatar);
        addUser.setOpenid(openId);
        addUser.setNickName(nickName);
        addUser.setCreated(System.currentTimeMillis());
        addUser.setUpdated(System.currentTimeMillis());
        userRepository.save(addUser);

        userDto.setAvatar(addUser.getAvatar());
        userDto.setUserId(addUser.getId().toString());
        userDto.setNickName(addUser.getNickName());

        return userDto;
    }


}
