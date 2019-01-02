package com.oristartech.marketing.components.constant.enums;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.oristartech.api.exception.BizExceptionEnum;
import com.oristartech.marketing.components.exception.MException;

/**
 * @description: oss 图片保存路径
 * @author: zhangdongdong
 * @createDate: 2018/9/3 上午11:12
 * @Version: 1.0
 */
public enum FileSaveModuleEnum {

    ACTOR_PICTURE("actor_picture", "actor/picture/", "影人", 1024 * 1024 * 50, Arrays.asList("jpg", "jpeg", "png")),
    CINEMA_PICTURE("cinema_picture", "cinema/picture/", "影院", 1024 * 1024 * 50,Arrays.asList("jpg", "jpeg", "bmp", "png", "webp")),
    COMPANY_PICTURE("company_picture", "company/picture/", "公司", 1024 * 1024 * 50,Arrays.asList("jpg", "jpeg", "bmp", "png", "webp")),
    MERCHANT_AGREEMENT("merchant_agreement", "merchant/agreement/", "商户-协议", 1024 * 1024 * 50,Arrays.asList("jpg", "jpeg", "bmp", "png", "webp","zip","doc","docx","pdf","rar")),
    MERCHANT_PIC("merchant_pic", "merchant/merchantpic/", "商户-头像", 1024 * 1024 * 50,Arrays.asList("jpg", "jpeg", "bmp", "png", "webp")),
    MOVIE_PICTURE("movie_picture", "movie/picture/", "影片-剧照", 1024 * 1024 * 50,Arrays.asList("jpg", "jpeg", "png")),
    MOVIE_POSTER("movie_poster", "movie/poster/", "影片-海报", 1024 * 1024 * 50,Arrays.asList("jpg", "jpeg", "png")),
    MOVIE_VIDEO("movie_video", "movie/video/", "影片-视频", 1024 * 1024 * 50,Arrays.asList("mp4", "giv")),
    DEFAULT("default", "default/", "默认", 1024 * 1024 * 50,Arrays.asList("jpg", "jpeg", "bmp", "png", "webp"));

    private String moduleName;
    private String savePath;
    private String desc;
    //文件大小
    private long maxFileSize;
    //文件类型
    private Set<String> allowedTypes;

    FileSaveModuleEnum(String moduleName, String savePath, String desc, long maxFileSize, Collection allowedTypes) {
        this.moduleName = moduleName;
        this.savePath = savePath;
        this.desc = desc;
        this.maxFileSize = maxFileSize;
        this.allowedTypes = new HashSet<>(allowedTypes);
    }

    public void checkFile(MultipartFile file){
        String[] split = file.getOriginalFilename().split("\\.");
        if (split.length < 2) {
            throw new MException(BizExceptionEnum.FILE_TYPE_ERROR);
        }
        if (!allowedTypes.contains(split[split.length - 1].toLowerCase())) {
            throw new MException(BizExceptionEnum.FILE_TYPE_ERROR);
        }
        if (file.getSize() > maxFileSize) {
            throw new MException(BizExceptionEnum.FILE_SIZE_BIG);
        }

    }


    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public Set<String> getAllowedTypes() {
        return allowedTypes;
    }

    public void setAllowedTypes(Set<String> allowedTypes) {
        this.allowedTypes = allowedTypes;
    }
}
