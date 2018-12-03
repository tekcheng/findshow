package com.megamusic.findshow.domain.entity.constant;

/**
 * Created by chengchao on 2018/9/30.
 */
public enum OrderType {
    CONTACT(1,"查看联系方式"),RESERVE(2,"预定");

    private Integer code;
    private String name;

    OrderType(Integer code, String name) {
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
