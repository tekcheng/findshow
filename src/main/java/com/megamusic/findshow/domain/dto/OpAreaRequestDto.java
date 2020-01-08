package com.megamusic.findshow.domain.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by chengchao on 2019/8/6.
 */
@Data
@ToString
public class OpAreaRequestDto {
    private Long Id;
    private String name;
    private Long cityId;
    private Integer sort;
    private String district;
    private String street;
    private String addrTag;
    private String addrDesc;
    private Long square;
    private Long numberOfPersons;
    private Long conferenceNumber;
    private Long guestRoomNumber;
    private String tag;
    private String spTag;
    private String description;
    private String launchedActivities;
    private String avatar;
    private List<String> images;
    private List<String> equipment;
    private List<String> service;
}
