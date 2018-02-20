package com.megamusic.findshow.domain.entity.constant;

/**
 * Created by chengchao on 2018/2/19.
 */
public enum DeleteCodeEnum {
    DELETED(1,"已删除"),NOT_DELETEED(0,"未删除");
    private Integer code;
    private String name;

    DeleteCodeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
