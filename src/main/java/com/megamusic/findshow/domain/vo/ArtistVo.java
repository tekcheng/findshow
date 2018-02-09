package com.megamusic.findshow.domain.vo;

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
    private String images;
    private ArtistTypeEnum type;
}
