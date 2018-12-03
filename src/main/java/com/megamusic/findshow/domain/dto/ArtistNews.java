package com.megamusic.findshow.domain.dto;

import lombok.Data;

import javax.persistence.*;

/**
 * 艺人相关报道
 * Created by chengchao on 2018/9/12.
 */
@Data
public class ArtistNews {
    private String title;
    private String linkUrl;
}
