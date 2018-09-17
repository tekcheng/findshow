package com.megamusic.findshow.domain.entity.constant;

/**
 * Created by chengchao on 2018/9/12.
 */
public enum ArtistExperienceTypeEnum {
    ZONGYI(1,"综艺"),HUODONG(2,"活动");
    private Integer type;
    private String msg;

    ArtistExperienceTypeEnum(Integer type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
