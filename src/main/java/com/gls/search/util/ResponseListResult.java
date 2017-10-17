package com.gls.search.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.gls.util.Constants;

/**
 * JSON返回结果组装类--查询列表返回
 * 
 * @author 詹昌高
 * @version 2016-12-7
 */
@JSONType(orders = {"respCode", "respMsg", "list", "total"})
public class ResponseListResult {
    
    /** 返回状态吗 */
    private Integer respCode;
    
    /** 返回描述信息 */
    private String respMsg;
    
    /** 查询数据列表 **/
    private List<Object> list = new ArrayList<Object>();
    
    /** 符合查询条件的总数 **/
    private int total;
    
    protected ResponseListResult() {
        
    }
    
    private ResponseListResult(final Integer respCode, final String respMsg) {
        super();
        this.respCode = respCode;
        this.respMsg = respMsg;
    }
    
    /**
     * 创建成功的请求结果对象。
     * 
     * @return
     */
    public static ResponseListResult createSuccess() {
        final ResponseListResult jsonResult = new ResponseListResult(Constants.OK_ERROR_CODE, "请求成功");
        return jsonResult;
    }
    
    /**
     * 创建成功的请求结果对象。
     * 
     * @return
     */
    public static ResponseListResult createSuccess(List list) {
        final ResponseListResult jsonResult =
            new ResponseListResult(Constants.OK_ERROR_CODE, Constants.OK_ERROR_MSG);
        jsonResult.setList(list);
        return jsonResult;
    }
    
    /**
     * 创建成功的请求结果对象。
     * 
     * @param 成功提示信息
     * 
     * @return
     */
    public static ResponseListResult createSuccess(String msg) {
        final ResponseListResult jsonResult = new ResponseListResult(Constants.OK_ERROR_CODE, msg);
        return jsonResult;
    }
    
    /**
     * 创建失败的JsonResult对象。
     * 
     * @param errCode 错误码
     * @param msg 错误提示
     * @return
     */
    public static ResponseListResult createFalied(final int errCode, final String msg) {
        final ResponseListResult jsonResult = new ResponseListResult(errCode, msg);
        return jsonResult;
    }
    
    
    public List<Object> getList() {
        return list;
    }
    
    public void setList(List<Object> list) {
        this.list = list;
    }
    
    public void addData(Object object) {
        this.list.add(object);
    }
    
    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    
    public Integer getRespCode() {
        return respCode;
    }
    
    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }
    
    public String getRespMsg() {
        return respMsg;
    }
    
    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
    
    /**
     * @Title: toJson @Description: 生成请求结果Json @author 詹昌高 @return @throws
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
                            return new ArrayList<>();
                        } else if (type.contains("Map")) {
                            return new HashMap<>();
                        } else if (type.contains("Integer") || type.contains("Double") || type.contains("Long")
                            || type.contains("Float") || type.contains("Boolean") || type.contains("Decimal")) {
                            return 0;
                        } else {
                            return null;
                        }
                    }
                } catch (NoSuchFieldException | SecurityException e) {
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
                        }  else {
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
