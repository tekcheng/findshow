package com.megamusic.findshow.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.megamusic.findshow.domain.entity.constant.ArtistTypeEnum;
import lombok.Data;

/**
 * Created by maita on 17/8/14.
 */
@Data
public class ArtistVo {
    private String id;
    private String name;
    private String description;
    private String cover;
    private String categoryId;
    private String categoryName;
    @JsonIgnore
    private String images;
    @JsonIgnore
    private ArtistTypeEnum type;
}
