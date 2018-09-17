package com.megamusic.findshow.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 艺人经历
 * Created by chengchao on 2018/9/12.
 */
@Data
@Entity
@Table(name = "FSArtistExperience")
public class ArtistExperience {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long artistId; //艺人id
    @Column
    private Integer type; //类型 1综艺 & 2活动 @see ArtistExperienceTypeEnum
    @Column
    private String detail;//详情


}
