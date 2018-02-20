package com.megamusic.findshow.domain.op;

import lombok.Data;

/**
 * Created by chengchao on 2018/2/19.
 */
@Data
public class OpArtistVo {
    private String id;
    private String name;
    private String description;
    private String cover;
    private String images;
    private String type;
    private String sort;
    private String categoryId;
    private String categoryName;
    private Integer status;
    private Long created;
    private Long updated;
}
