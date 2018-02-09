package com.megamusic.findshow.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by maita on 17/8/20.
 */
@Data
public class DataCollectionVo<T> {
    private List<T> list;
    private String fpage;
    private Boolean end=false;
}
