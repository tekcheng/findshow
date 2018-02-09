package com.megamusic.findshow.domain.entity;

import com.megamusic.findshow.domain.entity.constant.ArtistTypeEnum;
import lombok.Data;

import javax.persistence.*;

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
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column
    private String cover; //封面图
    @Column(length = 2048)
    private String images;//图集
    @Column
    private ArtistTypeEnum type;
    @Column(nullable = false,columnDefinition="tinyint default 1")
    private boolean schedule = true;//是否有档期
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer status=0; //状态 0 正常 1 下线
    @Column
    private Long created;
    @Column
    private Long updated;
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer isDeleted=0;//0正常 1删除
    @Column
    private Long categoryId; //所属分类
    @Column
    private Integer sort=0; //分类下权重
    @Column
    private String videoInfo; //视频信息

}
