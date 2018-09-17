package com.megamusic.findshow.domain.trans;

import lombok.Data;

/**
 * Created by chengchao on 2018/9/17.
 */
@Data
public class AliyunVideoTransDto {
    private String RequestId;
    private String PlayAuth;
    private VideoMeta VideoMeta;

    @Data
    public class VideoMeta {
        private String CoverURL;
        private String Status;
        private String VideoId;
        private String Duration;
        private String Title;
    }
}
