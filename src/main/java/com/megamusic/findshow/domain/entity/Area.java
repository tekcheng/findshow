package com.megamusic.findshow.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 场地信息
 * Created by chengchao on 2019/8/1.
 */
@Data
@Entity
@Table(name = "FSArea")
public class Area {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 128)
    private String name;

    @Column
    private Long cityId;

    @Column(length = 512)
    private String cover;//封面图

    @Column(length = 2048)
    private String images;//图集

    @Column(length = 128)
    private String tag;  //场地标签

    @Column(length = 128)
    private String spTag; //特性标签

    @Column(length = 1024)
    private String addrDesc; //地址详细信息

    @Column(length = 1024)
    private String addrDistrict; //地址区

    @Column(length = 1024)
    private String addrStreet; //地址街道

    @Column(length = 1024)
    private String addrTag; //地址标志

    @Column
    private Long square; //面积

    @Column
    private Long numberOfPersons=0L; //容纳人数

    @Column
    private Long conferenceNumber=0L;//会议室数量

    @Column
    private Long guestRoomNumber=0L;//客房数量

    @Column(columnDefinition = "TEXT")
    private String areaDescription; //场地简介

    @Column(columnDefinition = "TEXT")
    private String launchedActivities; //举办活动

    @Column(length = 1024)
    private String equipment; //设备

    @Column(length = 1024)
    private String service; //服务

    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer sort=0;

    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer status=0; //状态 0 正常 1 下线

    @Column
    private Long created;

    @Column
    private Long updated;

}
