package com.megamusic.findshow.domain.dto;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import java.util.List;

/**
 * Created by chengchao on 2018/9/22.
 */
@Data
@ToString
public class OpArtistRequestDto {
    private Long id;
    private String name="";
    private String cityId="";
    private String cateId="";
    private Integer sort=0;
    private String age="";
    private String popularity="";
    private String price="";//参考价

    private String tipTag; //展示标签
    private String shortDesc; //一句话描述
    private String shortDesc2; //实力作证

    private String weibo="";
    private String weixin="";
    private String douyin="";
    private String mobile="";
    private String major="";
    private String style="";
    private String height="";
    private String weight="";
    private String fans="";
    private String description="";
    private String video="";
    private String avatar;
    private List<String> images;
    private List<ArtistNews> news;
    private List<ArtistExperience> experience;
}
