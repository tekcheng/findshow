package com.megamusic.findshow.domain.vo;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;

/**
 * Created by maita on 18/2/4.
 */
@Data
public class ArtistDetailVo {

    private String id;
    private Integer type; //类型 0 艺人 1 场地
    private String name;
    private String age;
    private String avatar; //头像
    private String description; //艺人 作品信息 ，场地 场地简介
    private String popularity; //人气
    private String price;//参考价

    private String category;  //所属分类
    private String cityName;  //cityId 对应的地区

    private List<String> images;//图集
    private List<String> videoInfo; //视频信息

    private String major;//专业
    private String style;//风格
    private String height;//身高
    private String weight;//体重
    private String fans;//粉丝数

    //联系方式是否可见
    private boolean showContact;

    //以下信息付费可见
    private String weibo;//微博地址
    private String douyin;//抖音地址
    private String weixin;//微信号
    private String mobile;//手机号


    //相关报道
    private List<RelateNewsVo> news;
    //经历
    private List<ArtistExpVo> experience;


}
