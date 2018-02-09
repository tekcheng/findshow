package com.megamusic.findshow.domain.entity;

import com.megamusic.findshow.domain.entity.constant.ResContentTypeEnum;
import lombok.Data;
import javax.persistence.*;

/**
 * Created by maita on 17/8/15.
 */
@Data
@Entity
@Table(name = "FSResContent")
public class ResContent {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long categoryId; //所属资源位id
    @Column
    private String title; //标题
    @Column
    private String image;
    @Column
    private String content;  //内容 类型为H5的时候 存页面地址
    @Column
    private ResContentTypeEnum contentType; //数据类型
    @Column
    private Long contentId;  //实体id
    @Column(nullable = false,columnDefinition="tinyint default 0")
    private Integer sort=0;  //排序
    @Column
    private Long created;
    @Column
    private Long updated;
}
