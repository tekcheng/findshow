package com.megamusic.findshow.domain.entity.constant;

/**
 * Created by maita on 17/8/14.
 * 数据实体类型
 */
public enum  ArtistTypeEnum {

    ARTIST(0,"艺人"),FIELD(1,"场地"),EQUIPMENT(2,"器材"),TEACHING(3,"教学");

    private Integer code;
    private String name;

    ArtistTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

}
