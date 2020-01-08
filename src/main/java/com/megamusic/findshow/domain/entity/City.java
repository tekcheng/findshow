package com.megamusic.findshow.domain.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by chengchao on 2019/7/25.
 */
@Data
@Entity
@Table(name = "FSCity")
public class City {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer status=0; //状态 0 正常 1 下线
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer sort=0;
    @Column
    private Long created;
    @Column
    private Long updated;
}
