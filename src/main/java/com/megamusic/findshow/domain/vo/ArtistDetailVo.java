package com.megamusic.findshow.domain.vo;

import lombok.Data;
import java.util.List;

/**
 * Created by maita on 18/2/4.
 */
@Data
public class ArtistDetailVo {
    private String id;
    private String name;
    private String description;
    private String cateId;
    private List<String> images;//图集
    private List<String> videoInfo; //视频信息
}
