package com.oristartech.marketing.components.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 *
 */
public class Zip4jUtil {

    private static Log log = LogFactory.getLog(Zip4jUtil.class);



    /**
     * 添加文件夹到压缩包中
     *
     * @param zipFilePath  zip压缩包绝对路径
     * @param folderSource 文件夹源
     * @return 返回压缩之后的路径
     */
    public static String addFolder(String zipFilePath, String folderSource) {

        try {

            if (zipFilePath != null && !zipFilePath.trim().equals("") && folderSource != null && !folderSource.trim().equals("")) {
                ZipFile zipFile = new ZipFile(zipFilePath);

                ZipParameters parameters = new ZipParameters();

                parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
                parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
                zipFile.addFolder(folderSource, parameters);

                return zipFilePath;
            }

        } catch (ZipException e) {
            e.printStackTrace();
        }

        return null;

    }



}
