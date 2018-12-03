package com.megamusic.findshow.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 入驻申请
 * Created by chengchao on 2018/11/4.
 */
@Data
@Entity
@Table(name = "FSArtistApply")
public class ArtistApplyInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String type;

    @Column
    private String weixin;

    @Column
    private String address;

    @Column
    private String intro; //描述

    @Column
    private String simpleTag;

    @Column(length = 5000)
    private String experience;

    @Column
    private String price;//参考价（展示用）


    @Column(length = 2048)
    private String photos;//图集

    @Column(length = 2048)
    private String videos; //视频信息

    @Column
    private Long created;

    @Column
    private Long updated;

}
