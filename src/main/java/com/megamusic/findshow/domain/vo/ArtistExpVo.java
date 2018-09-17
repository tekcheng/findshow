package com.megamusic.findshow.domain.vo;

import lombok.Data;

/**
 * Created by chengchao on 2018/9/12.
 */
@Data
public class ArtistExpVo {
    private String type; //类型 1综艺 & 2活动 @see ArtistExperienceTypeEnum
    private String detail;

    @Override
    public String toString() {
        return "artistExpVo{" +
                "type='" + type + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
