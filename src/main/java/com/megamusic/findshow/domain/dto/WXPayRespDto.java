package com.megamusic.findshow.domain.dto;

import lombok.Data;

/**
 * Created by chengchao on 2018/9/29.
 */
@Data
public class WXPayRespDto {

    private String appId;
    private String timeStamp;
    private String nonceStr;
    private String packageStr;
    private String signType;
    private String paySign;


    private String prepayId;

}
