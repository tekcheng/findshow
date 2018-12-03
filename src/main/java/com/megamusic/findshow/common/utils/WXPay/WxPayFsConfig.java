package com.megamusic.findshow.common.utils.WXPay;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

/**
 * Created by chengchao on 2018/9/28.
 */
public class WxPayFsConfig implements WXPayConfig {


    private String appId;


    private String mchId;

    private String key;



    public WxPayFsConfig(String appId, String mchId, String key) {
        this.appId = appId;
        this.mchId = mchId;
        this.key = key;
    }

    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
        return mchId;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
