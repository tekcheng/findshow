package com.megamusic.findshow.domain.vo;

import com.megamusic.findshow.domain.entity.constant.ResContentTypeEnum;
import lombok.Data;

/**
 * Created by maita on 17/8/16.
 */
@Data
public class ResourceVo<T> {
    private String title; //标题
    private String image; //图片
    private ResContentTypeEnum contentType; //内容类型
    private String contentId; //内容id
    private T content;  //内容
}
