package com.megamusic.findshow.domain.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by chengchao on 2019/8/1.
 */
@Data
@ToString
public class AreaVo {

    private Long id;

    private String name;

    private String cover;//封面图

    private String tag;  //场地标签

    private String spTag; //特性标签

    private String addrDesc; //地址详细信息

    private String addrDistrict; //地址区

    private String addrStreet; //地址街道

    private String addrTag; //地址标志

    private Long square; //面积

    private Long numberOfPersons = 0L; //容纳人数

    private Long conferenceNumber = 0L;//会议室数量

    private Long guestRoomNumber = 0L;//客房数量

    private String areaDescription; //场地简介

    private String launchedActivities; //举办活动

    private List<String> imageList;//图集

    private List<String> equipmentList; //设备

    private List<String> serviceList; //服务
}
