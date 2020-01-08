package com.megamusic.findshow.domain.dto;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;

/**
 * Created by chengchao on 2019/3/18.
 */
@Data
@ToString
public class OrderApplyDto {

    /**
     * 当前用户id
     */
    private Long userId;

    /**
     * 艺人id
     */
    private Long entityId;

    /**
     * 类型
     * 1 艺人 2 场地
     */
    private Integer entityType;


    /**
     * 活动类型
     */
    private String activeType;

    /**
     * 服务内容
     */
    private String serviceInfo;

    /**
     * 活动时间(单位：毫秒)
     */
    private String activeTime;

    /**
     * 具体需求
     */
    private String activeDetail;

    /**
     * 联系人
     */
    private String contractPerson;

    /**
     * 手机号
     */
    private String contractPhone;

}
