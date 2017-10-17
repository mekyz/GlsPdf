package com.gls.util;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
  	//serialVersionUID
  	public static final String JSON_MAP_SERIALVERSIONUID = "serialVersionUID";
    //基本返回参数
    public static final String HTTP_METHOD = "method";
    public static final String DATA = "result";
   
    public static final String PAGE = "page";
    public static final String TOKEN = "token";
    public static final String UID = "userId";
    public static final String UNAME = "userName";
    public static final String PIMAGEURL = "profileImageUrl";
    public static final String ERROR_CODE = "status";
    public static final String PRAISECOUNTS = "praiseCounts";
    public static final String ERROR_MSG = "errorInfo";
    public static final String INFO_MSG = "warnInfo";

    public static final String RESPONSE_TOTALCOUNT = "totalCount";
    public static final String RESPONSE_TOTALPAGE = "totalPage";
    public static final String RESPONSE_CURRENTPAGE = "currentPage";
    public static final String RESPONSE_NUMBER = "number";	//一页多少行记录
    public static final String RESPONSE_STARTINDEX = "startIndex";	//从第几条记录开始
    public static final String RESPONSE_ENDINDEX = "endIndex";	//到第几条记录
    
    // api返回异常
    public static final int API_RETURN_ERROR = -2;


    public static final int OK_ERROR_CODE = 0;
    public static final String OK_ERROR_MSG = "OK";

    public static final int DB_ERROR_CODE = -3;
    public static final String DB_ERROR_MSG = "数据库错误";
    public static final int DB_EXCEPTION_CODE = -33;
    public static final String DB_EXCEPTION_MSG = "数据库异常";
    public static final int REDIS_EXCEPTION_CODE = -44;
    public static final String REDIS_EXCEPTION_MSG = "缓存连接失败";
    public static final int IO_EXCEPTION_CODE = -55;
    public static final String IO_EXCEPTION_MSG = "IO错误";
    public static final int SERVER_EXCEPTION_CODE = -22;
    public static final String SERVER_EXCEPTION_MSG = "server exception!";
    public static final int MYSQLINTEGRITYCONSTRAINTVIOLATIONEXCEPTION_CODE = -222;
    public static final String MYSQLINTEGRITYCONSTRAINTVIOLATIONEXCEPTION_MSG = "数据库异常";
    public static final int SKU_EXCEPTION_CODE = -88;
    public static final String SKU_EXCEPTION_MSG = "库存不足!";
    public static final int SAVEORDER_EXCEPTION_CODE = -89;
    public static final String SAVEORDER_EXCEPTION_MSG = "新增订单错误!";
    public static final String SAVEORDER_ORDER_HACK_MSG = "订单失效, 请重新购买";
    public static final String SKU_EXCEPTION_SKUPROS = "skuProps";
    public static final String SKU_EXCEPTION_PRODUCTID = "productId";
    
    public static final int AUTHENTICATIONEXCEPTION_CODE = -311;
    public static final String AUTHENTICATIONEXCEPTION_MSG = "支付权限错误";
    public static final int INVALIDREQUESTEXCEPTION_CODE = -312;
    public static final String INVALIDREQUESTEXCEPTION_MSG = "支付验证错误";
    public static final int APICONNECTIONEXCEPTION_CODE = -313;
    public static final String APICONNECTIONEXCEPTION_MSG = "支付连接错误";
    public static final int APIEXCEPTION_CODE = -314;
    public static final String APIEXCEPTION_MSG = "支付API错误!";
    

    public static final int NO_LOGIN_CODE_61 = -61;
    public static final String NO_LOGIN_61_MSG = "权限不足";
    
    public static final int NO_LOGIN_CODE = -6;
    public static final String NO_LOGIN_MSG = "请重新登陆";
    public static final String NO_LOGIN_WEB_MSG = "请重新登陆";
    
    public static final int FAIL_LOGIN_CODE = -66;
    public static final String FAIL_LOGIN_MSG = "登陆失败";
    
    public static final int STATUS_LOGIN_CODE = -67;
    public static final String STATUS_LOGIN_MSG = "用户审核中";
    
    public static final int STATUS1_LOGIN_CODE = -68;
    public static final String STATUS1_LOGIN_MSG = "用户审核不通过";

    public static final int ERROR_VALIDATE_CODE = -9;
    public static final String ERROR_VALIDATE_MSG = "参数错误";

    public static final int ERROR_PWD_CODE = -10;
    public static final String ERROR_PWD_MSG = "账号或密码输入错误";
    public static final int ERROR_NOUSER_CODE = -101;
    public static final String ERROR_NOUSER_MSG = "没此用户";
    public static final int ERROR_REG_CODE = -31;
    public static final String ERROR_REG_MSG = "用户名已存在";
    
    public static final int ERROR_REDO_CODE = -11;
    public static final String ERROR_REDO_MSG = "请重试";
    public static final int ERROR_UPFILE_ERROR = -12;
    public static final String ERROR_UPFILE_ERROR_MSG = "上传文件错误";

    
    public static final int ERROR_USER_WITHDRAW_COUNT_CODE = -16;
    public static final String ERROR_USER_WITHDRAW_COUNT_MSG = "操作频繁，请稍后再试";
    
    public static final int ERROR_USER_WITHDRAWCASH_PRICE_CODE = -41;
    public static final String ERROR_USER_WITHDRAWCASH_PRICE_MSG = "余额不足";
  
    
    public static final String JSON = "application/json; charset=utf-8";
    public static final String FORM_DATA = "multipart/form-data; charset=utf-8";
    public static final String JSON_JS = "application/javascript; charset=utf-8";
    
    
 // jdbc sql返回值
    public static final int JDBC_SQL_REURN_OK = 1;
    // 默认单页条数
    public static final int PAGE_COUNT = 20;
    // 默认单页条数 //用在最新动态那里
    public static final int PAGE_COUNT_DYSTAT = 30;
    // 默认第几页
    public static final int PAGE_NO = 1;
    // 默认拿出的最大多数 //用在最新动态那里
    public static final int PAGE_MAXCOUNT_DYSTAT = 50;
    
    // 默认排序 
    public static final String ORDER_BY_DEFAULT = "update_time desc";
    //订单默认排序
    public static final String ORDER_BY_DEFAULT_ORDERS = "create_time desc";
    //商品排序
//    public static final String ORDER_BY_DEFAULT_PRODUCT = "sort_index desc, pp.create_time desc";
    public static final String ORDER_BY_DEFAULT_PRODUCT = "sort_index desc, pp.up_time desc";
    public static final String ORDER_BY_HOME_PRODUCT = "recomment_time desc";
    public static final String ORDER_BY_DEFAULT_PRODUCT_SORTINIT = "create_time desc";
    
    //luosimao短信接口 
    public static final String LUOSIMAO_REDIS_VARIACODE = "variaCode";
    public static final String LUOSIMAO_REDIS_VARIACODE_VALUE = "1";
    public static final String LUOSIMAO_SEND_KEY_API = "api";
    public static final String LUOSIMAO_SEND_KEY = "";//没用
    public static final String LUOSIMAO_SEND_JSON = "https://sms-api.luosimao.com/v1/send.json";
    public static final String LUOSIMAO_SEND_BATCH_JSON = "http://sms-api.luosimao.com/v1/send_batch.json";
    public static final String LUOSIMAO_KEY_MOBILE = "mobile";
    public static final String LUOSIMAO_KEY_MOBILE_LIST = "mobile_list";
    public static final String LUOSIMAO_KEY_MESSAGE = "message";
    public static final String LUOSIMAO_KEY_VALIA_NAME = "您的验证码为 ";
    public static final String LUOSIMAO_KEY_COMPANY_NAME = "【格罗斯产业链】";
    
    public static final int LUOSIMAO_RESULT_OK = 0;
    public static final int LUOSIMAO_RESULT_ERROR_PHONE = -40;
    public static final int LUOSIMAO_RESULT_ERROR_SOFAST = -41;
    public static final int LUOSIMAO_RESULT_ERROR_OTHER = -999;
    
    public static final String REDIS_PHONE_CODE = "variaCode:";
    public static final String REDIS_PHONE_COUNT_CODE = "variaCodeCount:";
    
    public static final String REDIS_USER_WITHDRAW_CASH = "userWithDrawCash:";//用户提现记录
    
    public static final String REDIS_USER_CUST_ID = "userCustId:";//用户推荐人ID，记录扫码进来的推荐人id
    
    public static final int REDIS_DATA_CACHE_TIME = 60*60*24*300;
    
    public static final String REDIS_USER_UPDATE_CUSTID_COUNT = "updateCustIdCount:";//用户修改推荐人的次数
    public static final String REDIS_TRADE_GETINDUSTRYTOP10 = "getIndustryTop10:";//首页【TOP10行业】柜量/重量排名，第一个位置
    public static final String REDIS_TRADE_GETINDUSTRYTPRODUCTOP10 = "getIndustryProductTop10:";//行业下【TOP10产品排名】重量/柜量，第二个位置
    public static final String REDIS_TRADE_GETINDUSTRYAREATOP10 = "getIndustryAreaTop10:";//行业进出口饼图重量/柜量，第三个位置
    public static final String REDIS_TRADE_GETINDUSTRYPRODUCTAREATOP10 = "getIndustryProductAreaTop10:";//产品进出口图重量/柜量 第四个图
    public static final String REDIS_TRADE_GETINDUSTRYSUPPLIERTOP10 = "getIndustrySupplierTop10:";//采购商供应商数量分布，第5个图
    public static final String REDIS_TRADE_GETTURNOVERDISTRIBUTED = "getTurnoverDistributed:";//重量/柜量分布图，第6个图
    public static final String REDIS_TRADE_GETAREATURNOVERTREND = "getAreaTurnoverTrend:";//重量/柜量分布图，右上第一个图
    public static final String REDIS_TRADE_GETINDUSTRYTRENDTOP10 = "getIndustryTrendTop10:";//
    public static final String REDIS_TRADE_GETAGGREGATEAMOUNT = "getAggregateAmount:";//
    public static final String REDIS_TRADE_CATEGORYLIST = "categoryList";//
    
    public static final String REDIS_TRADE_MAPINFO = "findMapInfo:";//
    public static final String REDIS_TRADE_FINDMAPRELATION = "findMapRelation:";//
    
    public static final String REDIS_DATA_LOGIN_COOKIE = "DataLoginCookie";
    
    //public static final String DATA_COM_USER_NAME = "973033340@qq.com";
    
    public static final String DATA_COM_USER_NAME = "T973033345@126.com";
    
    public static final String DATA_COM_PASS_WORD = "Ggls2017";
    
    public static final String[] DATA_COM_COM_LIST = {"UNITED STATES","UNITED KINGDOM","SOUTH AFRICA","SINGAPORE","NEW ZEALAND","IRELAND","INDIA","CANADA","BRAZIL","AUSTRALIA"};
    
    
    
    
    
    
    //上传文件后缀名
	public static final String UPLOAD_FILE_END = ".jpg";
	
	//pay_type 用户支付方式 0:还没支付, 1:支付宝,2:微信,31:到付
	public static final int PAY_TYPE_NOT_PAY = 0;
	public static final int PAY_TYPE_ALIPY = 1;
	public static final int PAY_TYPE_WEIXIN = 2;
	public static final int PAY_TYPE_HEREPAY = 31;
	
	public static final int MAX_HOT_TAG_LIST = 5;
	
    //生成订单sn码 前缀
	public static final String ORDER_SN_START = "OR";
	//生成商品sn码 前缀
	public static final String PRODUCT_SN_START = "PR";
	//生成sku sn码 前缀
	public static final String SKU_SN_START = "SK";
	
	
	//redis 空间名
    public static final String REDIS_SMS = "SMS:";	//SMS namespace
    public static final String REDIS_SMS_MAXDAY_COUNT_KEYNAME = "leftCount";	//每天自定义条件下的发送的SMS最多条数 10条
    public static final String REDIS_SMS_MAXDAY_COUNT_VALUE = "100";	//每天自定义条件下的发送的SMS最多条数 10条

    //Ping++ 对应的key
    //body里的orerSn 超过多少位 就把其放到redis里
    public static final int PING_BODY_SIZE = 80;
    //前缀
    public static final String PING_BODY_SIZE_START = "sn:";
    
    public static final String PING_TOKEN = "token";
    public static final String PING_AMOUNT = "amount";
    public static final String PING_CURRENCY = "currency";
    public static final String PING_EXTRA = "extra";
    public static final String PING_SUCCESS_URL = "success_url";
    public static final String PING_SUCCESS_URL_STR = "http://www.com/paySuccess";
    public static final String PING_SUBJECT = "subject";
    public static final String PING_BODY = "body";
    public static final String PING_META_BODY = "meta_body";
    public static final String PING_ORDER_NO = "order_no";
    public static final String PING_CHANNEL = "channel";
    public static final String PING_CLIENT_IP = "client_ip";
    public static final String PING_ID = "id";
    public static final String PING_APP = "app";
    public static final String PING_CHARGE = "charge";
    public static final String TOKEN_ID = "tokenId";
    public static final String PING_METADATA = "metadata";
    
    public static final String PING_USERID = "userId";
    public static final String PING_ORDERID = "orderId";
    public static final String PING_ORDERSN = "orderSn";
    public static final String PING_PRODUCTID = "productId";
    public static final String PING_ORDERPLUSPRICE = "orderPlusPrice";
    public static final String PING_SKUPROPS = "skuProps";
    public static final String PING_BUYMAX = "buyMax";
    public static final String PING_POSTPRICE = "postPrice";
    public static final String PING_ISFLASH = "isFlash";
    
    public static final String PING_DATA = "data";	
    public static final String PING_OBJECT = "object";	
    public static final String PING_TYPE = "type";	//支付类型
    public static final String PING_TYPE_ALIPAY = "alipay";
    public static final String PING_TYPE_TENPAY = "tenpay";
    public static final String PING_TYPE_WX = "wx";	
    public static final String PING_TYPE_UPACP = "upacp";	
    public static final String PING_TYPE_UPMP = "upmp";	
    public static final String PING_TYPE_BFB = "bfb";		//百度钱包 
    public static final String PING_TYPE_ALIPAY_WAP = "alipay_wap";	
    public static final String PING_TYPE_WX_PUB = "wx_pub"; 
    
    public static final String PING_PAY_CHARGE_SUCCEEDED = "charge.succeeded"; 
    public static final int PING_PAY_OK_ERROR_CODE = 288;
    
    
    //kuaidi100 物流信息
    //http://api.kuaidi100.com/api?id=10f0650dbcbc8661&com=tiantian&nu=666103484658&valicode=[]&show=0&muti=1&order=desc
    public static final String WULIU_KUAIDI100_API_KEY = "7ccad019a54d091c";
    public static final String WULIU_KUAIDI100_URL = "http://api.kuaidi100.com/api?id=" + WULIU_KUAIDI100_API_KEY; 
    
    //退贷时原上传图片 最大上传多少张
    public static final int BACKAPP_UPIMG_MAXLEN = 8; 
    public static final int BACKAPP_UPIMG_MAXLEN_NEW = 3; //新版本
    
    //upyun app img上传
    public static final String BUCKET_NAME_IMAGES = "xiaomeihome-image";
  	public static final String BUCKET_NAME_STATIC = "xiaomeihome-static";
  	public static final String BUCKET_NAME = "xiaomeihome-image";
  	public static final String OPERATOR_NAME = "shenqi";
  	public static final String OPERATOR_PWD = "!2345qwert";
  	public static final String UPYUN_URL_PRODUCT_XXX = "xxx";
  	public static final String UPYUN_URL_DEVELOP_YYY = "yyy";
  	public static final String UPYUN_URL_FILES_LOGO = "/logo.png";
  	public static final String UPYUN_URL_IMAGES = "http://image.xiaomeihome.com";
  	public static final String UPYUN_URL_STATIC = "http://static.xiaomeihome.com";
  	public static final String WULIU_SYS_NAME = "小妹家物流助手";
  	public static final String WULIU_SYS_NAME_0 = "小妹家消息助手";
  	//图片存在  image.xiaomeihome.com  等价于 xiaomeihome-image.b0.upaiyun.com	
  	//static.xiaomeihome.com  等价于xiaomeihome-static.b0.upaiyun.com  我们存html文件 放在static 域下
  	public static final String UPYUN_DIR_ROOT = "/";	/** 根目录 */
  	public static final String UPYUN_DIR_PRODUCT = "/images/product/thumbnails/";		//商品上传的缩略图片目录
  	public static final String UPYUN_DIR_PRODUCT_TOP = "/images/product/top/";		//商品上传的TOP图片目录
  	public static final String UPYUN_DIR_TAG = "/images/tag/";  //商品标签图片目录
  	public static final String UPYUN_DIR_USER = "/images/user/";			//用户上传的目录,如头像
  	public static final String UPYUN_DIR_USER_COM = "/images/user/com/";			//用户上传的目录,如头像
  	public static final String UPYUN_DIR_USER_backapply = "/images/backapply/";			//用户退款图片
  	public static final String UPYUN_DIR_PRODUCT_HTML = "/html/product/";	//static域 商品上传的html目录, 存放图文详情
  	public static final String UPYUN_DIR_NEWS_HTML = "/html/news/";	//static域 心晴html目录
	public static final String CHARSET_URL_DEFAULT = "UTF-8";
	public static final SimpleDateFormat UPYUN_DIR_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd/"); //上传到Upyun的日期目录的format
	
	//v1 还是v2版本
	public static final String APP_VERSION_V1 = "v1";
	public static final String APP_VERSION_V2 = "v2";
	
	//web版本图形验证码的宽度
	public static final int IMG_CODE_WIDTH = 200;
	//web版本图形验证码的高度
	public static final int IMG_CODE_HEIGHT = 41;
	
	//redis key ================================================================== Start
	//用户 token redis的key
	public static final String REDIS_KEY_TOKEN_USER = "user:";		//这个是 2015-12-08加的

	//发送手机号的ip set的key
	public static final String REDIS_KEY_SEND_IP_HASH = "phoneip";
	//phoneip里的ip 每个ip一天允许发多少条短信，  500条短信大概 40元， 一条短信大概8分
	public static final int REDIS_KEY_SEND_IPCOUNT_HASH_SIZE = 5;
	
	
	//订单未付款 15分钟后 恢复sku(15分钟后 恢复完的话，把此orderId放到redis里(时间是24小时)
	//一下订单saveOrder,就把orderSn 放到此key 是一个hash, 每10分钟迭代一下，查看orderId对应的订单是否付款了，15分钟内没有付款则 加上sku ,且删除此orderSn key 
	//删除成功的话，把此key orderSn 放到 另一个key value对里(以 ORSKU: 开头)，有效期是24小时, 方便用户订单取消时， 判断是否增加库存, 如果有此orderSn的key, 则用户取消订单时不需要增加回库存
	public static final String REDIS_KEY_ORDERID_SKUADD_HASH = "orderskuadd";
	public static final int REDIS_KEY_ORDERID_SKUADD_HASH_MINNUS = 15;	//15分钟增加sku
	public static final String REDIS_KEY_ORDERID_SKUADD_HASH_START = "orsku:";
	//闪购列表   //已经不用了
	public static final String REDIS_KEY_FLASHLIST_HASH = "flashlist";
	
	//渠道监控key start
	public static final String REDIS_KEY_QUDAO_DOWNANDCLICK = "qudao:downclick";
	//渠道监控key end
	
	//sku redis 
	public static final String REDIS_KEY_PRODUCTID_START = "productId:";
	public static final String REDIS_KEY_MARKETPRICE_START = "marketPrice:";
	public static final String REDIS_KEY_BUYMAX_START = "buyMax:";
	public static final String REDIS_KEY_MARKETPRICE = "marketPrice";
	public static final String REDIS_KEY_POSTPRICE = "postPrice";		//暂时没用到， 这个数据还是用 用户传过来 的
	public static final String REDIS_KEY_BUYMAX = "buyMax";
	public static final String REDIS_KEY_FLASHMARKETPRICE = "flashMarketPrice";
	public static final String REDIS_KEY_FLASHBUYMAX = "flashBuyMax";
	public static final String REDIS_KEY_ISGROUND = "isGround";
	public static final String REDIS_KEY_ISGROUND_TIME = "isGroundTime";
	public static final String REDIS_KEY_ISFLASH = "isFlash";
	public static final String REDIS_KEY_ISFLASHGROUND = "isFlashGround";
	public static final String REDIS_KEY_ISFLASHGROUND_TIME = "isFlashGroundTime";
	
	//#orders:1283334:129    //1283334是orderSn, 129是用户id  , 防止orderAdd与orderPayre的商品数量不一
	public static final String REDIS_KEY_ORDERSN_PROS = "orders:";
	//数量 前缀 redis key
	public static final String REDIS_KEY_ORDERSN_PROS_BUYMAX = "buyMax:";
	//总价 前缀 redis key	//目前没有用
	public static final String REDIS_KEY_ORDERSN_PROS_ALLPRICE = "allPrice:";

	//redis key ================================================================== End

	

	//罗斯猫 监控 前缀
	public static final String LUOSIMAO_APIERROR_START = "接口告警:";
	//api发送手机 信息 error
	public static final String[] API_CODER_ERROR_SENDPHONE_LIST = {
															"15889734575"
														};
	//api发送手机 信息 info
	public static final String[] API_CODER_INFO_SENDPHONE_LIST = {
															"15889734575"
														};
	//api发送手机 信息 info  发给运营君的
	public static final String[] API_YUNYING_INFO_SENDPHONE_LIST = {
															"15889734575"
														};

}
