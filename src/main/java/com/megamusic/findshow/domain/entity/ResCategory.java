package com.megamusic.findshow.domain.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * Created by maita on 17/8/15.
 */
@Data
@Entity
@Table(name = "FSResCategory")
public class ResCategory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer status=0; //0 正常 1 隐藏
    @Column
    private Long created;
    @Column
    private Long updated;

}
