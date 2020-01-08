package com.megamusic.findshow.domain.entity.constant;

/**
 * Created by chengchao on 2018/12/18.
 */
public enum  ResContentTypeEnum {
    LINK(0,"h5页面"),ARTIST_ENTITY(1,"艺人实体"),AREA_ENTITY(2,"场地实体");

    private Integer code;
    private String name;

    ResContentTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
