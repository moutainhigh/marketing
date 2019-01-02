package com.oristartech.marketing.components.util;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.oristartech.marketing.components.config.properties.OSSProperties;
import com.oristartech.marketing.components.constant.enums.FileSaveModuleEnum;

/**
 * @Auther: hexu
 * @Date: 2018/9/7 17:49
 * @Description:
 */
public class OSSUploadTools {

    @Autowired
    private OSSProperties ossProperties;


    public String uploadFile(String fileName, InputStream in, String path) throws Exception {
        OSSClient client = new OSSClient("http://oss-cn-beijing.aliyuncs.com/", ossProperties.getAliyunOssAccessId(), ossProperties.getAliyunOssAccessKey());
        String key = path + fileName;
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(in.available());
        objectMeta.setCacheControl("no-cache");
        objectMeta.setHeader("Pragma", "no-cache");
        objectMeta.setContentDisposition("inline;filename=" + fileName);
        client.putObject(ossProperties.getAliyunOssBucketName(), key, in, objectMeta);

        return ossProperties.getAliyunQrUrl() + key;
    }

    /**
     * @param multipartFile
     * @param fileSaveModuleEnum
     * @return
     * @throws Exception
     */
    public String uploadFile(MultipartFile multipartFile, FileSaveModuleEnum fileSaveModuleEnum) throws Exception {
        fileSaveModuleEnum.checkFile(multipartFile);
        String randFileName = ToolUtil.getRandomFileName(multipartFile.getOriginalFilename(),
                fileSaveModuleEnum.getModuleName());
        String url = uploadFile(randFileName, multipartFile.getInputStream(), fileSaveModuleEnum.getSavePath());
        return url;
    }





}
