package com.megamusic.findshow.domain.entity.constant;

/**
 * Created by maita on 17/8/15.
 * 资源位资源类型
 */
public enum ResContentTypeEnum {
    ARTIST(0,"艺人"),FIELD(1,"场地"),EQUIPMENT(2,"器材"),TEACHING(3,"教学"),H5(4,"h5页面");

    private Integer code;
    private String name;

    ResContentTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
