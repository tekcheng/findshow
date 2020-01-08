package com.megamusic.findshow.domain.entity.constant;

/**
 * Created by maita on 17/8/14.
 * 数据实体类型
 */
public enum
EntityTypeEnum {

    ARTIST(0,"艺人"),AREA(1,"场地");

    private Integer code;
    private String name;

    EntityTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static EntityTypeEnum getEnumByCode(Integer code){
        for (EntityTypeEnum entityTypeEnum : EntityTypeEnum.values()) {
            if (entityTypeEnum.code.equals(code)) {
                return entityTypeEnum;
            }
        }
        return ARTIST;
    }
}
