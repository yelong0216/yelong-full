package org.yelong.commons.toy;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author PengFei
 * @date 2020年1月20日上午10:21:16
 */
public class ToyStringUtils {

	/**
	 * str中是否包含strs中任意一个值
	 * str='abc' strs{"a","b"} return true
	 * @author 彭飞
	 * @date 2019年4月12日上午9:09:26
	 * @param str
	 * @param strs
	 * @return 
	 */
	public static boolean contains(String str , String [] strs) {
		for (String string : strs) {
			if(str.contains(string))
				return true;
		}
		return false;
	}

	/**
	 * str中包含strs中值的个数
	 * str= "abcd" strs = {"a","b"} return 2
	 * @author 彭飞
	 * @date 2019年4月12日上午9:33:43
	 * @param str
	 * @param strs
	 * @return
	 */
	public static int containCount(String str , String [] strs) {
		int i = 0;
		for (String string : strs) {
			if(str.contains(string))
				i++;
		}
		return i;
	}

	/**
	 * str中包含strs的字符数组
	 * str = "abcd" strs = {"a","b","e"} return {"a","b"}
	 * @author 彭飞
	 * @date 2019年4月12日上午9:35:00
	 * @param str
	 * @param strs
	 * @return
	 */
	public static String [] containValue(String str , String [] strs) {
		List<String> containValues = new ArrayList<String>();
		for (String string : strs) {
			if(str.contains(string)) 
				containValues.add(string);
		}
		return containValues.toArray(new String[containValues.size()]);
	}

	/**
	 * str1中包含str2的个数
	 * str1 = "abcdefc" str2 = "c" return 2
	 * @author 彭飞
	 * @date 2019年4月12日上午9:46:10
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static int containCount(String str1 , String str2) {
		int count = 0;
		if(!str1.contains(str2)) {
			return count;
		}
		while(str1.indexOf(str2) != -1) {
			count++;
			str1 = str1.substring(str1.indexOf(str2)+str2.length());
		}
		return count;
	}
	
}
