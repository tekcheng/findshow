package com.megamusic.findshow.domain.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * Created by maita on 17/8/15.
 * 类目
 */
@Data
@Entity
@Table(name = "FSCategory")
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String image;
    @Column
    private String description;
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer status=0; //状态 0 正常 1 下线
    @Column
    private Long created;
    @Column
    private Long updated;
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer sort=0;
}
