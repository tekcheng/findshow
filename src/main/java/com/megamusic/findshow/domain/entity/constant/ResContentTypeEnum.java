package com.megamusic.findshow.domain.entity.constant;

/**
 * Created by chengchao on 2018/12/18.
 */
public enum  ResContentTypeEnum {
    ENTITY(1,"实体"),LINK(2,"h5页面");

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
