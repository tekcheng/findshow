package com.megamusic.findshow.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 *
 * 订单申请明细 关联订单表
 * Created by chengchao on 2019/3/18.
 */
@Data
@Entity
@Table(name = "FSOrderApplyDetail")
public class OrderApplyDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String orderNo;

    /**
     * 活动类型
     */
    @Column
    private String activeType;

    /**
     * 服务内容
     */
    @Column
    private String serviceInfo;

    /**
     * 活动时间
     */
    @Column
    private String activeTime;

    /**
     * 具体需求
     */
    @Column
    private String activeDetail;

    /**
     * 联系人
     */
    @Column
    private String contractPerson;

    /**
     * 手机号
     */
    @Column
    private String contractPhone;

    @Column
    private Long created;

    @Column
    private Long updated;

}
