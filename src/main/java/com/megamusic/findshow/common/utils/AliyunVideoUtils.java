package com.megamusic.findshow.common.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Created by chengchao on 2018/9/17.
 */
@Component
public class AliyunVideoUtils {

    private static final Logger LOG = LoggerFactory.getLogger(AliyunVideoUtils.class);

    //账号AccessKey信息请填写(必选)
    private static String accessKeyId;
    //账号AccessKey信息请填写(必选)
    private static String accessKeySecret;


    @Value("${accessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }
    @Value("${accessKeySecret}")
    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public GetVideoPlayAuthResponse getAliyunVideo(String videoId) {
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);

        GetVideoPlayAuthResponse getVideoPlayAuthResponse;
        try {
            getVideoPlayAuthResponse =  client.getAcsResponse(request);
        }catch (Exception e){
            LOG.error("获取视频url请求异常", e);
            return null;
        }

        return getVideoPlayAuthResponse;

    }


    /**
     * 视频播放获取地址
     * @return
     */

    public GetPlayInfoResponse getPlayInfo(String videoId) {

        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        GetPlayInfoResponse getPlayInfoResponse;
        try {
            getPlayInfoResponse = client.getAcsResponse(request);
        } catch (Exception e){
            LOG.error("获取视频url请求异常", e);
            return null;
        }
        return getPlayInfoResponse;
    }

}
