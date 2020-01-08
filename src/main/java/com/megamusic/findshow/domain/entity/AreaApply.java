package com.megamusic.findshow.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by chengchao on 2019/12/13.
 */
@Data
@Entity
@Table(name = "FSAreaApply")
public class AreaApply {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    //联系人姓名
    @Column(length = 128)
    private String name;
    //联系电话
    @Column(length = 128)
    private String phone;
    //: "工作职位",
    @Column(length = 256)
    private String job;
    //: "身份证图片",
    @Column(length = 128)
    private String idCardImg;
    //: "营业执照图片"
    @Column(length = 128)
    private String businessImg;
    //":"场地名字"
    @Column(length = 128)
    private String placeName;
    //":"详细地理位置"
    @Column(length = 1024)
    private String placePosition;

    //":"容纳人数"
    @Column(length = 128)
    private String placeCount;

    //"大厅面积"
    @Column(length = 128)
    private String hallArea;

    //":"餐区面积"
    @Column(length = 128)
    private String restaurantSquare;

    //":"会议室面积"
    @Column(length = 128)
    private String meetingSquare;

    //":场地类别
    @Column(length = 128)
    private String placeType;

    //":场地属性
    @Column(length = 128)
    private String Attribute;

    //":["大屏幕","桌游电游"]
    @Column(length = 2048)
    private String equipment;

    //":["大屏幕","桌游电游"]
    @Column(length = 2048)
    private String entertainment;

    //":"场地描述"
    @Column(columnDefinition = "TEXT")
    private String description;

    //":"曾举办"
    @Column(columnDefinition = "TEXT")
    private String hostingHistory;

    //餐饮
    @Column(length = 256)
    private String dinner;

    @Column
    private Long created;

    @Column
    private Long updated;
}
