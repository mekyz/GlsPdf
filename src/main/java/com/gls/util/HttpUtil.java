package com.gls.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * @author Ding
 * @since 2015-07-26
 *
 */
public class HttpUtil {

	public static String makeHtml(String page) throws MalformedURLException, IOException {
		HttpURLConnection huc = null;
		BufferedReader br = null;
//		BufferedWriter bw = null;
		StringBuilder outStr = new StringBuilder();
		try {
			huc = (HttpURLConnection) new URL(page).openConnection();
	//		System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
	//		 System.setProperty("sun.net.client.defaultReadTimeout", "30000"); 
			huc.connect();
			InputStream stream = huc.getInputStream();
//			bw = new BufferedWriter(new OutputStreamWriter (new FileOutputStream(filePath),chartset));
			br = new BufferedReader(new InputStreamReader(stream, Constants.CHARSET_URL_DEFAULT));
			String line;
            while((line = br.readLine())!= null){
               if(line.trim().length() > 0){
            	   outStr.append(line.trim());
               }
            }
		} finally {
			try {
                br.close();
//                bw.close();
                huc.disconnect();
            }catch (Exception e) {
                e.printStackTrace();
            }
		}
		
		return outStr.toString();
	}
	
	
	//图片长宽高 
	public static int[]  returnImgWH(String imgurl, String pathUrl) {
        boolean b=false;
        
        try {
            //实例化url
            URL url = new URL(imgurl);
            //载入图片到输入流
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            //实例化存储字节数组
            byte[] bytes = new byte[100];
            //设置写入路径以及图片名称
            OutputStream bos = new FileOutputStream(new File(pathUrl));
            int len;
            while ((len = bis.read(bytes)) > 0) {
                bos.write(bytes, 0, len);
            }
            bis.close();
            bos.flush();
            bos.close();
            //关闭输出流
            b=true;
        } catch (Exception e) {
            //如果图片未找到
            b=false;
        }
        int[] a = new int[2];
        if(b){//图片存在
            //得到文件
            File file = new File(pathUrl);
            BufferedImage bi = null;
            boolean imgwrong=false;
            try {
                //读取图片
                bi = ImageIO.read(file);
                try{
                    //判断文件图片是否能正常显示,有些图片编码不正确
                    int i = bi.getType();
                    imgwrong=true;
                }catch(Exception e){
                    imgwrong=false;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if(imgwrong){
                a[0] = bi.getWidth(); //获得 宽度
                a[1] = bi.getHeight(); //获得 高度
            }else{
                a=null;
            }
            //删除文件
            file.delete();
        }else{//图片不存在
            a=null;
        }
       return a;

    }
	//jsonp 用到
	public static ObjectMapper om = new ObjectMapper();

    public static String forResult(String _callback, Map<String, Object> map) throws JsonGenerationException,
            JsonMappingException, IOException {

        String jsonResult = om.writeValueAsString(map);

        if (null == _callback) {
            return jsonResult;
        }

        StringBuffer sb = new StringBuffer(_callback + "(");
        sb.append(jsonResult);
        sb.append(");");
        jsonResult = sb.toString();
        return jsonResult;

    }
    
    //包装 spring mvc exception controller 的request 参数列表
    public static String exceptionParams(Map mm) {
    	return "";
    }
    
    public synchronized static String postData(String url, Map<String, String> params, String codePage) throws Exception {

		final HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10 * 1000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(10 * 1000);

		final PostMethod method = new PostMethod(url);
		if (params != null) {
			method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, codePage);
			method.setRequestBody(assembleRequestParams(params));
		}
		String result = "";
		try {
			httpClient.executeMethod(method);
			result = new String(method.getResponseBody(), codePage);
		} catch (final Exception e) {
			throw e;
		} finally {
			method.releaseConnection();
		}
		return result;
	}

	public synchronized static String postData(String url, String codePage) throws Exception {
		final HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10 * 1000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(10 * 1000);

		final GetMethod method = new GetMethod(url);
		String result = "";
		try {
			httpClient.executeMethod(method);
			result = new String(method.getResponseBody(), codePage);
		} catch (final Exception e) {
			throw e;
		} finally {
			method.releaseConnection();
		}
		return result;
	}

	/**
	 * 组装http请求参数
	 * 
	 * @param params
	 * @param menthod
	 * @return
	 */
	private synchronized static NameValuePair[] assembleRequestParams(Map<String, String> data) {
		final List<NameValuePair> nameValueList = new ArrayList<NameValuePair>();

		Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			nameValueList.add(new NameValuePair((String) entry.getKey(), (String) entry.getValue()));
		}

		return nameValueList.toArray(new NameValuePair[nameValueList.size()]);
	}
	
	
	
	
	public static String doPost(String url, String msg, String contentType) {
		CloseableHttpClient httpClient = null;
		HttpPost post = null;
		CloseableHttpResponse response = null;
		String result = null;
		try {
			httpClient = HttpClients.createDefault();
			post = new HttpPost(url);
			post.addHeader("content-type", contentType);
			StringEntity msgEntity = new StringEntity(msg, ContentType.create(contentType, "utf-8"));
			post.setEntity(msgEntity);
			response = httpClient.execute(post);
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);
			}
//			logger.info("send post method request success, url-->{}, params-->{}", url, msg);
		} catch (Exception e) {
//			logger.error("send post method request error, url-->{}", url, e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
//					logger.error(e.getMessage());
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
//					logger.error(e.getMessage());
				}
			}
		}
		return result;
	}
	

	
}
