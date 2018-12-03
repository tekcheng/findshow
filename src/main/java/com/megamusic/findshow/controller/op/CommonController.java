package com.megamusic.findshow.controller.op;

import com.aliyun.oss.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.megamusic.findshow.common.constant.SystemConstantsEnum;
import com.megamusic.findshow.common.utils.*;
import com.megamusic.findshow.domain.Response;
import com.megamusic.findshow.domain.op.ImageVo;
import com.megamusic.findshow.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengchao on 2018/2/13.
 */
@Slf4j
@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private AliyunVideoUtils aliyunVideoUtils;

    @Autowired
    private SmsService smsService;

    @RequestMapping("sendSmsCode")
    @ResponseBody
    public Response sendSmsCode(String phoneNo) throws ClientException {
        smsService.sendVerifyCode(phoneNo);
        return ResponseUtils.getSuccessResponse("success");
    }


    @RequestMapping("getVideoPlayAuth")
    @ResponseBody
    public Response getVideoPlayAuth(String videoId) throws ClientException {
        return ResponseUtils.getSuccessResponse(aliyunVideoUtils.getAliyunVideo(videoId));
    }

    @RequestMapping("getVideoPlayUrl")
    @ResponseBody
    public Response getVideoPlayUrl(String videoId) throws ClientException {
        GetPlayInfoResponse getPlayInfoResponse = aliyunVideoUtils.getPlayInfo(videoId);
        return ResponseUtils.getSuccessResponse(getPlayInfoResponse);
    }

    @RequestMapping("upload/page")
    public String upload() throws ClientException {
        return "test_upload";
    }

    @RequestMapping(value = "/upload", method = { RequestMethod.POST })
    @ResponseBody
    public Response uploadDo( @RequestParam("fileName") MultipartFile file ) throws ClientException,IOException {
        if (file != null){
            // 取得当前上传文件的文件名称
            String fileName = file.getOriginalFilename();
            // 如果名称不为空,说明该文件存在，否则说明该文件不存在
            if (fileName.trim() != "") {
                File newFile = new File(fileName);
                FileOutputStream outStream = new FileOutputStream(newFile); // 文件输出流用于将数据写入文件
                outStream.write(file.getBytes());
                outStream.close(); // 关闭文件输出流
                file.transferTo(newFile);
                // 上传到阿里云
                String fileUrl = AliyunOssUtils.upload(newFile);
                newFile.delete();
                return ResponseUtils.getSuccessResponse(fileUrl);
            }
        }
        return ResponseUtils.getErrorResponse(SystemConstantsEnum.FAIL);
    }

    @RequestMapping(value = "/batchUpload", method = { RequestMethod.POST })
    @ResponseBody
    public Response<List<ImageVo>> batchUpload( HttpServletRequest request, HttpServletResponse response ) throws ClientException,IOException {
        Response<List<ImageVo>> resp = new Response<List<ImageVo>>();
        try {
            // 创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            //设置文件上传大小限制
            multipartResolver.setMaxUploadSize(8*1024*1024);
            List<ImageVo> files = new ArrayList<ImageVo>();
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                // 转换成多部分request
                List<MultipartFile> fileList =((MultipartHttpServletRequest)request).getFiles("fileName");
                for( int i=0;i<fileList.size();i++ ){
                    MultipartFile file = fileList.get(i);
                    if (file != null) {
                        // 取得当前上传文件的文件名称
                        String fileName = file.getOriginalFilename();
                        // 如果名称不为空,说明该文件存在，否则说明该文件不存在
                        if (fileName.trim() != "") {
                            File newFile = new File(fileName);
                            FileOutputStream outStream = new FileOutputStream(newFile); // 文件输出流用于将数据写入文件
                            outStream.write(file.getBytes());
                            outStream.close(); // 关闭文件输出流
                            file.transferTo(newFile);
                            // 上传到阿里云
                            String fileUrl = AliyunOssUtils.upload(newFile);
                            String fileId = CommonUtils.MD5(fileUrl);
                            ImageVo imageVo = new ImageVo();
                            imageVo.setId(fileId);
                            imageVo.setUrl(fileUrl);
                            files.add(imageVo);
                            newFile.delete();
                        }
                    }
                }
            }
            log.info("[图片上传] 批量上传成功，返回地址：{}",files.toString());
            resp.setData(files);
            resp.setCode(SystemConstantsEnum.SUCCESS.getCode());
            resp.setMsg("success");
        } catch (Exception e) {
            resp.setMsg("upload error:"+e.getMessage());
            resp.setCode(SystemConstantsEnum.FAIL.getCode());
            e.printStackTrace();
        }
        return resp;
    }





}
