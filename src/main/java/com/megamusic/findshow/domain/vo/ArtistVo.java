package com.megamusic.findshow.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.megamusic.findshow.domain.entity.constant.EntityTypeEnum;
import lombok.Data;

/**
 * Created by maita on 17/8/14.
 */
@Data
public class ArtistVo  {
    //图片 名称 一句话描述 实力佐证 人气 参考价 地区
    private String id;
    private String name; //名称
    private String shortDesc; //一句话描述
    private String shortDesc2; //实力佐证
    private String popularity; //人气
    private String cover;   //封面图
    private String tipTag; //标签
    private String categoryId;
    private String categoryName;
    private String referencePrice; //参考价

    @JsonIgnore
    private String images;
    @JsonIgnore
    private EntityTypeEnum type;
}
