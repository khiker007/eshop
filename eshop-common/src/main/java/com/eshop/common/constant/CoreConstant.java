package com.eshop.common.constant;

import com.eshop.common.util.io.PropertyUtil;

/**
 * @author Administrator
 */

public class CoreConstant {
	// 属性文件配置名称
	public static final String PROPERTY_FILE_NAME = "project";
	// 验证码
	public static String RAND_CODE = "user_rand_code";
	// 后台用户存seesion用
	public static String SYS_USER_SESSION_NAME = "sys_user_ukey";
	public static String USER_SESSION_NAME = "USER_SESSION_NAME";
	// 读取配置文件类
	public static PropertyUtil propertyUtil = PropertyUtil.getInstance("project");
	// 项目路径
	public static String contextPath = propertyUtil.getProperty("contextPath");
	// 图片、CSS、js 服务器地址
	public static String imagesPath = propertyUtil.getProperty("imagesPath");

	// 上传服务用服务器地址，访问时用imagesPath，数据库中不存储域名
	public static String uploadServerUrl = propertyUtil.getProperty("uploadServerUrl");
	/*
	 * 上传图片后访问的地址，使用imagesPath或者uploadServerUrl防止项目部署到多台机器，单独定义此变量
	 */
	public static String uploadStaticUrl = propertyUtil.getProperty("uploadStaticUrl");

	public static String scriptFilePath = propertyUtil.getProperty("scriptFilePath");

	public static String indexFilePath = propertyUtil.getProperty("indexFilePath");

	public static String DICT_KEY = "sys_dict_key";

	public static String USER_SECURITY_KEY = "user_security_key";

	// 网站积分、经验、勋章排名的最大名次。
	public static final Integer MAX_RANGING = 100;

	
	//标签关联到的对象类型
	public static final int TAG_RELATE_OBJ_TYPE_TOPIC = 98;
	public static final int TAG_RELATE_OBJ_TYPE_QUESTION = 99;
	public static final int TAG_RELATE_OBJ_TYPE_ARTICLE = 100;
	public static final int TAG_RELATE_OBJ_TYPE_BLOG = 101;
	public static final int TAG_RELATE_OBJ_TYPE_GROUP = 102;
	public static final int TAG_RELATE_OBJ_TYPE_DOC = 103;

}
