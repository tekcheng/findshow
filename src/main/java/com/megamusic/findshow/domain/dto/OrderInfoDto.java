package com.megamusic.findshow.domain.dto;

import lombok.Data;

/**
 * Created by chengchao on 2019/3/21.
 */
@Data
public class OrderInfoDto {

    //订单编号
    private String orderNo;

    //订单标题
    private String orderTitle;

    //订单备注描述
    private String orderInfo;

    //活动类型
    private String activeType;

    //支付金额：单位分
    private Long  payPrice;

    //演出时间（ 单位：毫秒）
    private String activeTime;

    //展示图片
    private String image;

}
