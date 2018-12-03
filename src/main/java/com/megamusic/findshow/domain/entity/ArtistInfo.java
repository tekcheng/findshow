package com.megamusic.findshow.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 艺人资料
 * Created by chengchao on 2018/9/12.
 */
@Data
@Entity
@Table(name = "FSArtistInfo")
public class ArtistInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long artistId; //艺人id
    @Column
    private String major;//专业
    @Column
    private String style;//风格
    @Column
    private String height;//身高
    @Column
    private String weight;//体重
    @Column
    private String fans;//粉丝数
    @Column
    private String weibo;//微博地址
    @Column
    private String douyin;//抖音地址
    @Column
    private String weixin;//微信号
    @Column
    private String mobile;//手机号
    @Column(columnDefinition = "TEXT")
    private String artistNews;
    @Column(columnDefinition = "TEXT")
    private String artistExperience;
    @Column
    private Long created;
    @Column
    private Long updated;


}
