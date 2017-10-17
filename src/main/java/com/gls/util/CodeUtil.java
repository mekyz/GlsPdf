package com.gls.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;


public class CodeUtil {
	
	/**
	 * price加密
	 */
	public static String addPassPrice(String price) {
		Random RANDOM = new Random();
		StringBuffer randBuffer = new StringBuffer();  
        for(int i = 1; i <= 15; i++) {
        	randBuffer.append(RANDOM.nextInt(15));  
        }
        return randBuffer.toString();
	}
	/**
	 * price解密
	 */
	public static String plusPassPrice(String price) {
		Random RANDOM = new Random();
		StringBuffer randBuffer = new StringBuffer();  
		return randBuffer.toString();
	}
	/**
	 * 生成短信验证码算法
	 * <br>调用demo：code4phone(4, 10)
	 * @author Ding
	 * @since 2015-06-24
	 * @param bits  相当于循环几次取随机值
	 * @param to 每次在哪个整数范围内取随机值,此值有可能性是多位数
	 * @return 返回验证码
	 */
	public static String code4phone(int bits, int to) {
		Random RANDOM = new Random();
		StringBuffer randBuffer = new StringBuffer();  
		for(int i = 1; i <= bits; i++) {
			randBuffer.append(RANDOM.nextInt(to));  
		}
		return randBuffer.toString();
	}
	
	/**
	 * 生成用户登陆的token算法  (可能也有其他的方法引用到，不过都可以以 user: 开头)
	 * <br>调用demo：generateToken()
	 * @author Ding
	 * @param tokenContent  可以传来ip地址,或者设备序列号
	 * @since 2015-06-25
	 * @return 返回token字符串
	 */
	public static String generateToken(String tokenContent) {
//		Random RANDOM = new Random(); 
		//介个算法到时再优化
		//拿现在的时间tuo / 算出其md5
//		md5(时间tuo + ip/机器序列号)
		Date nowD = new  Date();
		long nowT = nowD.getTime();
		return Constants.REDIS_KEY_TOKEN_USER + string2MD5(nowT + tokenContent);		//bill:user
	}
	
	//copy网上的 //生成md5
	public static String string2MD5(String inStr) {
        MessageDigest md5 = null;  
        try{
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }
        return hexValue.toString();  
    }
	//生成售后申请编号, apply_sn码
		public static String string2ApplySN(String token) {	//这里后来传了 userId
			Date nowD = new  Date();
			long nowT = nowD.getTime();
//			return token + String.valueOf(nowT) ;
			return (token.length() > 5 ? token.substring(token.length() - 5, token.length()) : token) + String.valueOf(nowT) ;
		}
	//生成订单编号, order_sn码
	public static String string2OrderSN(String token) {	//这里后来传了 userId
		Date nowD = new  Date();
		long nowT = nowD.getTime();
		
		new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date());
//		return token + String.valueOf(nowT) ;
		return (token.length() > 5 ? token.substring(token.length() - 5, token.length()) : token) + String.valueOf(nowT) ;
	}
	//生成商品sn号, sn码
	public static String string2ProductSN(String token) {
		Date nowD = new  Date();
		long nowT = nowD.getTime();
		return Constants.PRODUCT_SN_START + string2MD5(nowT + token);
	}
	//生成sku sn号, sn码 //介贷目前用不着重, 因为sku不需要sn码 , 只需要hash码
	public static String string2SkuSN(String token) {
		Date nowD = new  Date();
		long nowT = nowD.getTime();
		return Constants.SKU_SN_START + string2MD5(nowT + token);
	}
	
	//根据 
    //UPSTOCK_SORT_DAYSPLICE  上架商品时 每隔3天一个档期
    public static long product2sortIndexLong(int start, int splice) {
    	long sortIndex = 0;
    	
//    	Constants.UPSTOCK_SORT_DAYSPLICE
    	//三天前0点的时间tuo
    	long startTime = System.currentTimeMillis() - (start + splice) * 24 * 60 * 60 * 1000;
    	//现在的时间
    	long endTime = System.currentTimeMillis() - start * 24 * 60 * 60 * 1000;
    	//今天23:59:59点的时间tuo
    	//产生1000到9999的随机数
		//原理 ： Math.round(Math.random()*(Max-Min) + Min)
		sortIndex = Math.round(Math.random()* (endTime - startTime) + startTime);
    	return sortIndex;
    }
  //根据 
    //UPSTOCK_SORT_DAYSPLICE  上架商品时 每隔3天一个档期
    public static int product2sortIndexLong1(int splice) {
    	int sortIndex = 0;
    	int start = 0;
    	
//    	Constants.UPSTOCK_SORT_DAYSPLICE
    	//三天前0点的时间tuo
    	int min = (int) ((System.currentTimeMillis()/1000 - ((start + splice) * 24 * 60 * 60 )));
    	//现在的时间
    	int max = (int) ((System.currentTimeMillis()/1000 - (start * 24 * 60 * 60 )));
    	//今天23:59:59点的时间tuo
    	//产生1000到9999的随机数
    	//原理 ： Math.round(Math.random()*(Max-Min) + Min)
//    	sortIndex = Math.round(Math.random()* (endTime - startTime)+ startTime);
    	Random random = new Random();
    	sortIndex = random.nextInt(max)%(max-min+1) + min;
    	return sortIndex;
    }
    //根据 
    //UPSTOCK_SORT_DAYSPLICE  上架商品时 每隔3天一个档期
    public static int product2sortIndexLong12(long timestamp, int splice) {
    	int sortIndex = 0;
    	int start = 0;
    	
//    	Constants.UPSTOCK_SORT_DAYSPLICE
    	//三天前0点的时间tuo
    	int min = (int) ((timestamp/1000 - ((start + splice) * 24 * 60 * 60 )));
    	//现在的时间
    	int max = (int) ((timestamp/1000 - (start * 24 * 60 * 60 )));
    	//今天23:59:59点的时间tuo
    	//产生1000到9999的随机数
    	//原理 ： Math.round(Math.random()*(Max-Min) + Min)
//    	sortIndex = Math.round(Math.random()* (endTime - startTime)+ startTime);
    	Random random = new Random();
    	sortIndex = random.nextInt(max)%(max-min+1) + min;
    	return sortIndex;
    }

    
    //拿到用户ip
    public static String clientIp(HttpServletRequest request) {
    	String allIp = request.getHeader("x-forwarded-for");
    	String ip = "";
        if (null != allIp) {
        	String[] ipStr = allIp.split(",");
        	if (null != ipStr) {		//取第一个ip
        		ip = ipStr[0];
        	}
        }
    	return ip;
    }
	/**
	 * 解密
	 * @param str
	 * @param pos1
	 * @param pos2
	 * @return
	 */
    public static  String Decrypt(String str,int pos1,int pos2) {
    	
        int nLength;
        String strReturn = "";
        char nTemp;
        char cTemp;
        nLength = str.length();
        for (int i = 0; i <= nLength - 2; i = i + 2) {
            cTemp = str.charAt(i + 1);
            if ((cTemp >= 48) && (cTemp <= 57)) {
                nTemp = (char) (cTemp - 48);
                nTemp = (char) ((nTemp + 10 - pos1) % 10);
                cTemp = (char) (nTemp + 48);
            } else if ((cTemp >= 65) && (cTemp <= 90)) {
                nTemp = (char) (cTemp - 65);
                nTemp = (char) ((nTemp + 26 - pos2) % 26);
                cTemp = (char) (nTemp + 97);
            } else if ((cTemp >= 97) && (cTemp <= 122)) {
                nTemp = (char) (cTemp - 97);
                nTemp = (char) ((nTemp + 26 - pos2) % 26);
                cTemp = (char) (nTemp + 65);
            }
            strReturn = strReturn + cTemp;
        }
        return strReturn;
    }
    /** 
     * @param str 需要加密的字符串 
     * @param encName 加密种类  MD5 SHA-1 SHA-256 
     * @return 
     * @Author:lulei   
     * @Description: 实现对字符串的加密 
     */  
    public static String encrypt(String str, String encName){  
        String reStr = null;  
        try {  
            MessageDigest md5 = MessageDigest.getInstance(encName);  
            byte[] bytes = md5.digest(str.getBytes());  
            StringBuffer stringBuffer = new StringBuffer();  
            for (byte b : bytes){  
                int bt = b&0xff;  
                if (bt < 16){  
                    stringBuffer.append(0);  
                }   
                stringBuffer.append(Integer.toHexString(bt));  
            }  
            reStr = stringBuffer.toString();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        return reStr;  
    }  
    /**
     * 当前周
     * @return
     */
    public static int getCurrentWeek(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyww");
    	Calendar ca = Calendar.getInstance();
    	return Integer.parseInt(sdf.format(ca.getTime()));
    }
    /** 
     * 得到某年某周的第一天 
     * 
     * @param year 
     * @param week 
     * @return 
     */ 
     public static Date getFirstDayOfWeek(String yearWeek) {
	     int year = Integer.parseInt(yearWeek.substring(0, 4));
	     int week = Integer.parseInt(yearWeek.substring(4, 6));
	     Calendar c = new GregorianCalendar(); 
	     c.set(Calendar.YEAR, year); 
	     c.set (Calendar.MONTH, Calendar.JANUARY); 
	     c.set(Calendar.DATE, 1);
	     Calendar cal = (GregorianCalendar) c.clone(); 
	     cal.add(Calendar.DATE, (week-1) * 7);
	     return getFirstDayOfWeek(cal.getTime ()); 
     }
     /** 
     * 得到某年某周的最后一天 
     * 
     * @param year 
     * @param week 
     * @return 
     */ 
     public static Date getLastDayOfWeek(String yearWeek) {
	     int year = Integer.parseInt(yearWeek.substring(0, 4));
	     int week = Integer.parseInt(yearWeek.substring(4, 6));
	     Calendar c = new GregorianCalendar(); 
	     c.set(Calendar.YEAR, year); 
	     c.set(Calendar.MONTH, Calendar.JANUARY); 
	     c.set(Calendar.DATE, 1);
	     Calendar cal = (GregorianCalendar) c.clone(); 
	     cal.add(Calendar.DATE , (week-1) * 7);
	     return getLastDayOfWeek(cal.getTime()); 
     }
     /** 
      * 取得指定日期所在周的第一天 
      * 
      * @param date 
      * @return 
      */ 
      public static Date getFirstDayOfWeek(Date date) { 
	      Calendar c = new GregorianCalendar(); 
	      c.setFirstDayOfWeek(Calendar.MONDAY); 
	      c.setTime(date); 
	      c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday 
	      return c.getTime (); 
      }
      /** 
      * 取得指定日期所在周的最后一天 
      * 
      * @param date 
      * @return 
      */ 
      public static Date getLastDayOfWeek(Date date) { 
	      Calendar c = new GregorianCalendar(); 
	      c.setFirstDayOfWeek(Calendar.MONDAY); 
	      c.setTime(date); 
	      c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday 
	      return c.getTime(); 
      } 
    
      /** 
       * 方法名称:transMapToString 
       * 传入参数:map 
       * 返回值:String 形如 username'chenziwen^password'1234 
      */  
     @SuppressWarnings("rawtypes")
	public static String transMapToString(Map map){  
        Entry entry;  
        StringBuffer sb = new StringBuffer();  
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)  
        {  
          entry = (Entry)iterator.next();  
            sb.append(entry.getKey().toString()).append( "'" ).append(null==entry.getValue()?"":  
            entry.getValue().toString()).append (iterator.hasNext() ? "^" : "");  
        }  
        return sb.toString();  
      } 
      /** 
       * 方法名称:transStringToMap 
       * 传入参数:mapString 形如 username'chenziwen^password'1234 
       * 返回值:Map 
      */  
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map transStringToMap(String mapString){  
        Map map = new HashMap();  
        java.util.StringTokenizer items;  
        for(StringTokenizer entrys = new StringTokenizer(mapString, "^");entrys.hasMoreTokens();   
          map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))  
            items = new StringTokenizer(entrys.nextToken(), "'");  
        return map;  
      }  
      
    public static void main(String[] args) throws Exception {
//		System.out.println(Encrypt("dbz150801",8,8));
//		System.out.println(Decrypt("S3R3Y9Q0D6W2",8,8));
		for (int i = 0; i < 200 ; i ++) {
//			System.out.println(product2sortIndexLong2(4500));
		}
		
//		BiMap<Integer,String> biMap = HashBiMap.create();
		int a=20;
		
		double newsShareRate = a*1.0/100;
		System.out.println(newsShareRate);
		
		System.out.println(MathUtils.multiplyTwoBigDecimal(newsShareRate,2933));
		
		
//		StringBuffer getAccessMpTokenUrl = new StringBuffer();
//			getAccessMpTokenUrl.append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=");
//			getAccessMpTokenUrl.append("wx73fd5478577863b3");
//			getAccessMpTokenUrl.append("&secret=");
//			getAccessMpTokenUrl.append("b66440bfd2e649e6cf57adc666ec2b99");
//			String jsonMpResult = HttpUtil.makeHtml(getAccessMpTokenUrl.toString());
//			JSONObject jsonMpObj = JSONObject.parseObject(jsonMpResult);
//
//			String accessToken = jsonMpObj.getString("access_token");
//			System.out.println(accessToken);
			
			
//			StringBuffer getAccessMpTokenUrl = new StringBuffer();
//	    	getAccessMpTokenUrl.append("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=").append("gb9zO4YffZCRbRXpnLjf1YpVshk_ZwLTZNsQHH9Rclg8kmuzC7EWATf6ZWI6Wiznn5emeri_lrVg1VycP6tz-a-n6cx0coORUVWpWb-xQj7nl6iviM5LiQKztHDu6QDHACFbAHAOQT");
//	    	String pram = "{'expire_seconds': 604800, 'action_name': 'QR_SCENE', 'action_info': {'scene': {'scene_id': 123}}}";
//	    	RestTemplate restTemplate = new RestTemplate();
//	    	Map map = new HashMap();
//	    	map.put("expire_seconds", 604800);
//	    	map.put("action_name", "QR_SCENE");
//	    	Map actionInfoMap = new HashMap();
//	    	Map sceneMap = new HashMap();
//	    	sceneMap.put("scene_id", 123);
//	    	actionInfoMap.put("scene", sceneMap);
//	    	map.put("action_info", actionInfoMap);
//	    	String result = restTemplate.postForObject(getAccessMpTokenUrl.toString(), map, String.class);	
//	    	System.out.println(result);
		
//		String  timestamp = "1470054868";
//		String nonce = "697629384";
//		String signature = "4a0a4f7c529832e2ca3cbb137b2bfbf78c2c3719";
////		signature:timestamp:nonce:echostr:7528594157502156247
//		 String[] arr = new String[] { Constants.WECHATTOKEN, timestamp, nonce };  
//         //按字典排序  
//         Arrays.sort(arr);  
//         StringBuilder content = new StringBuilder();    
//         for (int i = 0; i < arr.length; i++) {    
//             content.append(arr[i]);    
//         }  
//         System.out.println(CodeUtil.encrypt(content.toString(), "SHA-1"));
//         //加密并返回验证结果  
//         boolean flag = signature == null ? false : signature.equals(CodeUtil.encrypt(content.toString(), "SHA-1"));
//         System.out.println(flag);
//         System.out.println(signature == null ? false : signature.equals(CodeUtil.encrypt(content.toString(), "SHA-1")));
//         
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int year= 2016;
		int month= 1;
		Calendar ca = Calendar.getInstance();
		System.out.println(sdf.format(ca.getTime()));
		ca.set(Calendar.YEAR, year);
		ca.set(Calendar.WEEK_OF_YEAR, month);
		ca.setFirstDayOfWeek(Calendar.MONDAY);
		System.out.println(sdf.format(ca.getTime()));
		System.out.println(ca.getFirstDayOfWeek());
		ca.add(Calendar.DATE, -ca.getFirstDayOfWeek());
		System.out.println(sdf.format(ca.getTime()));
		ca.add(Calendar.DATE, 6);
		System.out.println(sdf.format(ca.getTime()));
		
		Calendar ca2 = Calendar.getInstance();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-ww");
		System.out.println(sdf2.format(ca2.getTime()));
		System.out.println(sdf.format(ca2.getTime()));
		System.out.println(ca2.get(Calendar.DAY_OF_WEEK));
		ca2.set(Calendar.DATE, ca2.get(Calendar.DATE)-ca2.get(Calendar.DAY_OF_WEEK)+1);
		System.out.println(sdf.format(ca2.getTime()));
		ca2.set(Calendar.DATE, ca2.get(Calendar.DATE)+6);
		System.out.println(sdf.format(ca2.getTime()));
		
		Calendar c = new GregorianCalendar(); 
		 c.set(Calendar.YEAR, year); 
		 c.set (Calendar.MONTH, Calendar.JANUARY); 
		 c.set(Calendar.DATE, 1);
		 Calendar cal = (GregorianCalendar) c.clone(); 
		 cal.add(Calendar.DATE, 22 * 7);
		 System.out.println(sdf.format(getFirstDayOfWeek(cal.getTime ()))); 
		 
		System.out.println(MathUtils.multiplyTwoBigDecimal(299, 35.0/100));
		
		System.out.println(getCurrentWeek());
		System.out.println(getFirstDayOfWeek(getCurrentWeek()+""));
		
		
		Calendar c2 =  Calendar.getInstance(); 
		c2.setFirstDayOfWeek(Calendar.MONDAY); 
		System.out.println(c2.get(Calendar.WEEK_OF_YEAR)); ;
		
		System.out.println(CodeUtil.string2MD5("john.123456"));
		System.out.println(CodeUtil.string2MD5("jeff123"));
		System.out.println(CodeUtil.string2MD5("bocny.123"));
		System.out.println(CodeUtil.string2MD5("test.12345"));
		System.out.println(CodeUtil.string2MD5("gls888888"));
	}
    
}
