/**
 * com.rightoo.util
 * StringUtil.java
 * 2014年10月29日 下午3:27:27
 * @author: z```s
 */
package com.huashi.app.util;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 字符串工具类
 * </p>
 *
 */
public class StringUtils {



	/**
	 * <p>
	 * 判断是否为空
	 * </p>
	 * 
	 * @param arg
	 * @return 2014年10月29日 下午3:32:51
	 * @author: z```s
	 */
	public static boolean isNullOrEmpty(String arg) {
		boolean rel = false;
		if (arg == null || arg.isEmpty()) {
			rel = true;
		}
		return rel;
	}

	/**
	 * <p>
	 * 验证手机号
	 * </p>
	 * 
	 * @param phoneNo
	 *
	 *
	 */
	public static boolean isPhoneNo(String phoneNo) {
		if (isNullOrEmpty(phoneNo)) {
			return false;
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(phoneNo);
		return m.matches();
	}


	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、177（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
		String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles)) return false;
		else return mobiles.matches(telRegex);
	}
	/**
	 * <p>
	 * 验证邮箱
	 * </p>
	 * 
	 * @param email
	 *
	 *
	 */
	public static boolean isEmail(String email) {
		if (isNullOrEmpty(email)) {
			return false;
		}
		String regex = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * <p>
	 * 验证是否为纯数字
	 * </p>
	 * 
	 * @param number
	 * @return 2014年10月29日 下午3:33:26
	 * @author: z```s
	 */
	public static boolean isNumber(String number) {
		if (isNullOrEmpty(number)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(number).matches();
	}







}
