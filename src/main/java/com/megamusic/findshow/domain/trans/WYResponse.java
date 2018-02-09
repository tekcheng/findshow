package com.megamusic.findshow.domain.trans;

import lombok.Data;

import java.util.List;

/**
 * Created by maita on 17/8/17.
 */
@Data
public class WYResponse {
    private List<WYArtistData> data;
    private Boolean hasMore;
    private Integer code;
}
