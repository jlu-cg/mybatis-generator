package com.garden.mybatis.generator.util;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class GeneratorFileUtils {
	
	/**
	 * 创建目录
	 * @date 2018年3月27日 下午12:04:34
	 * @param packageName
	 */
	public static void createFolder(String packageName) {
		if(StringUtils.isBlank(packageName)) {
			return ;
		}
		String packagePath = packageName.replace('.', '/');
		File file = new File(packagePath);
		if(!file.exists()) {
			file.mkdirs();
		}
	}
}
