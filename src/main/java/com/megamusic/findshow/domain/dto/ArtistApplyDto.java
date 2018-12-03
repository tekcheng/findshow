package com.megamusic.findshow.domain.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by chengchao on 2018/11/4.
 */
@Data
@ToString
public class ArtistApplyDto {

    private String name;

    private String phone;

    private String type;

    private String weixin;

    private String address;

    private String intro; //描述

    private String simpleTag;

    private String experience;

    private String price;//参考价（展示用）

    private String photos;//图集

    private String videos; //视频信息

    private String verifyCode;

}
