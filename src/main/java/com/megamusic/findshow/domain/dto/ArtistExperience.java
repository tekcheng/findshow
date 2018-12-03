package com.megamusic.findshow.domain.dto;

import lombok.Data;

import javax.persistence.*;

/**
 * 艺人经历
 * Created by chengchao on 2018/9/12.
 */
@Data
public class ArtistExperience {
    private Integer type; //类型 1综艺 & 2活动 @see ArtistExperienceTypeEnum
    private String detail;//详情
}
