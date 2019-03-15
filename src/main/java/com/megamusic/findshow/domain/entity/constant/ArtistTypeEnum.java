package com.megamusic.findshow.domain.entity.constant;

/**
 * Created by maita on 17/8/14.
 * 数据实体类型
 */
public enum
ArtistTypeEnum {

    ARTIST(0,"艺人"),FIELD(1,"场地");

    private Integer code;
    private String name;

    ArtistTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ArtistTypeEnum getEnumByCode(Integer code){
        for (ArtistTypeEnum artistTypeEnum : ArtistTypeEnum.values()) {
            if (artistTypeEnum.code.equals(code)) {
                return artistTypeEnum;
            }
        }
        return ARTIST;
    }
}
