package com.megamusic.findshow.domain.entity.constant;

/**
 * Created by chengchao on 2018/9/12.
 */
public enum CityEnum {
    BEIJING(1,"北京");
    private Integer cityId;
    private String cityName;

    CityEnum(Integer cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public static CityEnum getCityById(Integer cityId){
        for( CityEnum c:CityEnum.values() ){
            if(c.getCityId().equals(cityId)){
                return c;
            }
        }
        return null;
    }
}
