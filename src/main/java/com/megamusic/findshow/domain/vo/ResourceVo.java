package com.megamusic.findshow.domain.vo;

import lombok.Data;

/**
 * Created by maita on 17/8/16.
 */
@Data
public class ResourceVo<T> {
    private String contentId; //内容id
    private String title; //标题
    private String image; //图片
    private Integer contentType; //内容类型
    private T content;  //实体内容  或者 跳转链接
}
