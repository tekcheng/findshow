package com.megamusic.findshow.domain.vo;

import lombok.Data;

/**
 * Created by chengchao on 2018/9/12.
 */
@Data
public class RelateNewsVo {
    private String title;
    private String linkUrl;

    @Override
    public String toString() {
        return "relateNews{" +
                "title='" + title + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                '}';
    }
}
