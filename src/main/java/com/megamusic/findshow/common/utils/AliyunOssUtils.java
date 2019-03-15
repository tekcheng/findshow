package com.megamusic.findshow.common.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by chengchao on 2018/2/13.
 */
@Component
@Slf4j
public class AliyunOssUtils {


    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String bucketName;
    // 文件访问域名
    private static String fileHost;



    public static String upload( File file ){
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 判断文件容器是否存在，不存在则创建
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }

            //创建文件路径  当天日期／时间戳-文件名
            String beginDate = System.currentTimeMillis()+"";
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            String path = sdf.format(new Date(Long.parseLong(beginDate)));
            String fileName = System.currentTimeMillis()+"-"+file.getName();

            String fileUrl = path+"/"+fileName;
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            if (null != result) {
                return fileHost + fileUrl;
            }
        } catch (Exception oe) {
            log.error("[oss上传图片失败] ",oe);
        } finally {
            // 关闭OSS服务
            ossClient.shutdown();
        }

        return null;

    }

    public static void delete( String bucketName,String key ){
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject(bucketName, key);
    }

    @Value("${bucketName}")
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Value("${endpoint}")
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    @Value("${ossAccessKeyId}")
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }
    @Value("${ossAccessKeySecret}")
    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
    @Value("${fileHost}")
    public void setFileHost(String fileHost) {
        this.fileHost = fileHost;
    }
}
