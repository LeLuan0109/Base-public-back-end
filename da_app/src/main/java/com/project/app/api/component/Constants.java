package com.project.app.api.component;

public class Constants {
	public static final String REGEX_DATE = "^\\d{4}-((1[0-2])|(0[1-9]))-((0[1-9])|([1-2][0-9])|(3[0-1]))$";
	public static final String REGEX_CODE = "^[0-9\\w-!@#$%^&*]{6,10}$";
	public static final String REGEX_EMAIL = "^[a-zA-Z]+[a-zA-Z0-9]*(\\.[a-zA-Z0-9]+)*@{1}[a-zA-Z]+(\\.[a-zA-Z0-9]+)*(\\.[a-zA-Z]{2,})$";
	public static final String REGEX_PHONE = "^[\\d]{11}$";
	public static final int MAX_SAME_TYPE_DIPLOMA = 3;
	public static final float AREA_MIN = 0f;
	public static final Byte MIN_AGE = 0;
	//	url path
	public static final String BASE_PATH = "src/store/file/";
	public  static  String  LOGO_ABP = "src/assest/img/logo_1.png";

	//	url
//	public static final  String DOMAIN_URL = "http://103.97.125.64:8088/";

	public static final String DOMAIN_URL = "http://103.97.125.64:8091/";
//	public static final String DOMAIN_URL_SLL = "https://103.97.125.64:8091/";
//	public static final  String DOMAIN_URL = "http://localhost:8091/";
	//	spam
	public static final Boolean spam = true ;
	public static final Boolean noSpam = false ;
	//	completed
	public static final Boolean completed = true ;
	public static final Boolean noCompleted = false ;

	//	sentiment
	public static final String positive = "Tích cực";
	public static final String negative = "Tiêu cực";
	public static final String neutral = "Trung tính";


	//	trans
	public static final String SPAM = "SPAM";
	public static final String COMPLETED = "COMPLETED";
	public static final String SENTIMENT = "SENTIMENT";
	public static final String TAG = "TAG";

	//regex
	public static final String regexTag1 = ".*_tag1$";
	public static final String regexTag2 = ".*_tag2$";
	public static final String regexTag3 = ".*_tag3$";

	//	status
	public static final Integer NEW = 0;
	public static final Integer UPDATE = 1;
	public static final Integer ACCEPT = 2;

	//	number defalue
	public static final Integer DEFAULT_SPAM = 0;
	public static final Integer DEFAULT_NO_SPAM = 1;
	public static final Integer DEFAULT_COMPLETED = 1;
	public static final Integer DEFAULT_NO_COMPLETED = 0;


	public static final Integer DEFAULT_POSITIVE = 1;
	public static final Integer DEFAULT_NEGATIVE = 2;
	public static final Integer DEFAULT_NEUTRAL = 0;

	private String ADMIN = "ADMIN";
	private String CLIENT = "CLIENT";
	private String USER = "USER";
}