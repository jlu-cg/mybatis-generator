package com.garden.mybatis.generator.util;

import java.util.Collection;

public class GeneratorCollectionUtils {
	
	/**
	 * 判断集合是否为空
	 * @date 2018年3月6日 下午8:47:33
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		if(collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断集合是否不为空
	 * @date 2018年3月26日 上午11:10:26
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	public static void main(String[] args) {
	}

}
