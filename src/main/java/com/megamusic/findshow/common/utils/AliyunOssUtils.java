package com.megamusic.findshow.common.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by chengchao on 2018/2/13.
 */
@Component
public class AliyunOssUtils {

    private static String endpoint = "oss-cn-beijing.aliyuncs.com";
    private static String accessKeyId = "LTAIO3aygVkKcnaJ";
    private static String accessKeySecret = "WWHvsi72TKvBlhyznQkVBCyj3DZr0J";
    private static String bucketName = "findshow";
    // 文件访问域名
    private static String fileHost  = "http://findshow.oss-cn-beijing.aliyuncs.com/";



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
        } catch (OSSException oe) {
            //TODO LOG
        } catch (ClientException ce) {
            //TODO LOG
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

}
