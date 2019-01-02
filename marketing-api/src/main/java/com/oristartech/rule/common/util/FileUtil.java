package com.oristartech.rule.common.util;

import org.apache.commons.codec.binary.Base64;


/**
 * 文件工具类
 * @author chenjunfei
 *
 */
public class FileUtil {
	private static String DATA_URI_SECTION_SPLITER = ",";
	/**
	 * 把base64编码的Data uri中的文件内容,转为byte数组
	 * @param dataUriContent
	 * @return
	 */
	public static byte[] dataUriContent2Byte(String dataUriContent) {
		if(!BlankUtil.isBlank(dataUriContent)) {
			String[] sections = dataUriContent.split(DATA_URI_SECTION_SPLITER);
			String base64Content = sections[sections.length - 1];
			return Base64.decodeBase64(base64Content);
		}
		return null;
	}
}
