package com.megamusic.findshow.domain.entity;

import com.megamusic.findshow.domain.entity.constant.EntityTypeEnum;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by maita on 17/8/14.
 */
@Data
@Entity
@Table(name = "FSArtist")
public class Artist {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String age;
    @Column(columnDefinition = "TEXT")
    private String description; //类型为艺人是 是 作品信息
    @Column
    private String popularity; //人气

    @Column
    private String tipTag; //展示标签

    @Column
    private String shortDesc; //一句话描述
    @Column
    private String shortDesc2; //实力作证

    @Column
    private String price;//参考价（展示用）
    @Column
    private BigDecimal contactPrice;//查看联系方式价钱
    @Column
    private String cover; //封面图(头像)
    @Column(length = 2048)
    private String images;//图集
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer type= EntityTypeEnum.ARTIST.getCode();
    @Column(nullable = false,columnDefinition="tinyint default 1")
    private boolean schedule = true;//是否有档期
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer status=0; //状态 0 正常 1 下线
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer isDeleted=0;//0正常 1删除
    @Column
    private Long categoryId; //所属分类
    @Column
    private Integer cityId=1; //所属城市 默认 1 北京
    @Column
    private Integer sort=0; //分类下权重
    @Column
    private String videoInfo; //视频信息
    @Column
    private Long created;
    @Column
    private Long updated;
}
