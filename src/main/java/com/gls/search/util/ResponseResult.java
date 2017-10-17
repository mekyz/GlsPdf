package com.gls.search.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.gls.util.Constants;

/**
 * JSON返回结果组装类--单对象返回
 * 
 * @author 詹昌高
 * @version 2016-12-7
 */
@JSONType(orders = {"respCode", "respMsg", "data"})
public class ResponseResult {
    
    /** 返回状态吗 */
    
    private Integer status;
    
//    /** 返回描述信息 */
//    private String respMsg;
    
    /** 查询返回对象 **/
    private Object data;
    
    private Integer total;
    
    protected ResponseResult() {
        
    }
    
    private ResponseResult(final Integer status) {
        super();
        this.status = status;
//        this.respMsg = respMsg;
    }
    
    /**
     * 创建成功的JsonResult对象。
     * 
     * @return
     */
    public static ResponseResult createSuccess() {
        final ResponseResult jsonResult = new ResponseResult(Constants.OK_ERROR_CODE);
        return jsonResult;
    }
    
    /**
     * 创建成功的请求结果对象。
     * 
     * @param 成功提示信息
     * 
     * @return
     */
    public static ResponseResult createSuccess(String msg) {
        final ResponseResult jsonResult = new ResponseResult(Constants.OK_ERROR_CODE);
        return jsonResult;
    }
    
    /**
     * 创建成功的请求结果对象。
     * 
     * @param 成功提示信息
     * 
     * @return
     */
    public static ResponseResult createSuccess(Object obj) {
        final ResponseResult jsonResult =
            new ResponseResult(Constants.OK_ERROR_CODE);
        jsonResult.setData(obj);
        return jsonResult;
    }
    /**
     * 创建成功的请求结果对象。带分页信息
     * 
     * @param 成功提示信息
     * 
     * @return
     */
    public static ResponseResult createSuccess(Object obj,Integer total) {
        final ResponseResult jsonResult =
            new ResponseResult(Constants.OK_ERROR_CODE);
        jsonResult.setData(obj);
        jsonResult.setTotal(total);
        return jsonResult;
    }
    
    /**
     * 创建失败的JsonResult对象。
     * 
     * @param errCode 错误码
     * @param msg 错误提示
     * @return
     */
    public static ResponseResult createFalied(final int errCode) {
        final ResponseResult jsonResult = new ResponseResult(errCode);
        return jsonResult;
    }
    
    
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    
    
    public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @Title: toJson @Description: 生成请求结果Json @author詹昌高 @return @throws
     */
    public String toJson() {
        return JSON.toJSONString(this, new ValueFilter() {
            @Override
            public Object process(Object obj, String key, Object value) {
                try {
                    Field field = obj.getClass().getDeclaredField(key);
                    String type = field.getGenericType().toString();
                    if (value == null || StringUtil.isBlank(value.toString())) {
                        if (type.contains("List") || type.contains("[]")) {
                            return null;
                        } else if (type.contains("Map")) {
                            return new HashMap<>();
                        } else if (type.contains("Integer") || type.contains("Double") || type.contains("Long")
                            || type.contains("Float") || type.contains("Boolean") || type.contains("Decimal")) {
                            return 0;
                        }else if (type.contains("String")) {
                            return "";
                        } else {
                            return null;
                        }
                    }
                } catch (NoSuchFieldException e) {
                } catch (SecurityException e) {
                }
                return value;
            }
        });
    }
    
    /**
     * 转换为json对象时，把不存在集合转换为[]
     * 
     * @author 詹昌高
     * @param keys 要把值转换为[]格式的 key值
     * @return
     */
    public String toJson(final String... keys) {
        String jsonString = JSON.toJSONString(this, new ValueFilter() {
            @Override
            public Object process(Object obj, String key, Object value) {
                
                try {
                    Field field = obj.getClass().getDeclaredField(key);
                    String type = field.getGenericType().toString();
                    if (value == null || StringUtil.isBlank(value.toString())) {
                        if (type.contains("List") || type.contains("[]")) {
                            return new ArrayList<>();
                        } else if (type.contains("Map")) {
                            return new HashMap<>();
                        } else if (type.contains("Integer") || type.contains("Double") || type.contains("Long")
                            || type.contains("Float") || type.contains("Boolean") || type.contains("Decimal")) {
                            return 0;
                        }else if (type.contains("String")) {
                            return "";
                        }
                        else {
                            return null;
                        }
                    }
                } catch (NoSuchFieldException e) {
                } catch (SecurityException e) {
                }
                return value;
            }
        });
        String replace = jsonString.replace("\"[]\"", "[]");
        return replace;
    }
    
}
