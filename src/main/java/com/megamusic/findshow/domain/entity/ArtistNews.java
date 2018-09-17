package com.megamusic.findshow.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 艺人相关报道
 * Created by chengchao on 2018/9/12.
 */
@Data
@Entity
@Table(name = "FSArtistNews")
public class ArtistNews {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long artistId; //艺人id
    @Column
    private String title;
    @Column
    private String linkUrl;

}
