package com.garden.mybatis.generator.util;

import org.apache.commons.lang3.StringUtils;

public class GeneratorStringUtils {
	
	/**
	 * 下滑线转驼峰
	 * @date 2018年3月6日 下午8:47:33
	 * @param underline
	 * @return
	 */
	public static String underlineToCamel(String underline) {
		String[] underlines = underline.split("\\_");
		StringBuilder camel = new StringBuilder();
		for(String temp : underlines) {
			if(StringUtils.isBlank(temp)) {
				continue;
			}
			String word = temp.toLowerCase();
			camel.append((char)(word.charAt(0) - 32));
			camel.append(word.substring(1));
		}
		return camel.toString();
	}
	
	/**
	 * 下滑线转驼峰
	 * @date 2018年3月6日 下午8:47:33
	 * @param underline
	 * @return
	 */
	public static String underlineToCamelLow(String underline) {
		String[] underlines = underline.split("\\_");
		StringBuilder camel = new StringBuilder();
		camel.append(underlines[0].toLowerCase());
		for(int i = 1; i < underlines.length; i++) {
			if(StringUtils.isBlank(underlines[i])) {
				continue;
			}
			String word = underlines[i].toLowerCase();
			camel.append((char)(word.charAt(0) - 32));
			camel.append(word.substring(1));
		}
		return camel.toString();
	}
	
	/**
	 * 首字母转小写
	 * @date 2018年3月31日 下午10:24:17
	 * @param word
	 * @return
	 */
	public static String lowStartWord(String word) {
		StringBuilder camel = new StringBuilder();
		camel.append(word.substring(0, 1).toLowerCase());
		camel.append(word.substring(1));
		return camel.toString();
	}

	/**
	 * 将包文件路径转为文件系统文件路径
	 * @date 2018年3月26日 上午11:10:58
	 * @param packageName
	 * @param fileName
	 * @return
	 */
	public static String packageFilePath(String packageName, String fileName) {
		StringBuilder sb = new StringBuilder();
		String packageFoldPath = packageName.replace('.', '/');
		sb.append(packageFoldPath).append('/').append(fileName);
		return sb.toString();
	}
}
