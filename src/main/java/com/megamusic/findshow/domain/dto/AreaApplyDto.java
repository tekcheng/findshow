package com.megamusic.findshow.domain.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by chengchao on 2019/12/13.
 */
@Data
@ToString
public class AreaApplyDto {

    //联系人姓名
    private String name;
    //联系电话
    private String phone;
    //: "工作职位",
    private String job;
    //: "身份证图片",
    private String idCardImg;
    //: "营业执照图片"
    private String businessImg;
    //":"场地名字"
    private String placeName;
    //":"详细地理位置"
    private String placePosition;

    //":"容纳人数"、
    private String placeCount;

    //"大厅面积"
    private String hallArea;

    //":"餐区面积"
    private String restaurantSquare;

    //":"会议室面积"
    private String meetingSquare;

    //":场地类别
    private String placeType;

    //":场地属性
    private String Attribute;

    //":["大屏幕","桌游电游"]
    private List<String> equipment;

    //":["大屏幕","桌游电游"]
    private List<String> entertainment;

    //":"场地描述"
    private String description;

    //":"曾举办"
    private String hostingHistory;

    //餐饮
    private String dinner;

    private String verifyCode;

}
