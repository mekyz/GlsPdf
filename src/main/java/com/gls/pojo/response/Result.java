package com.gls.pojo.response;

/**
 * 接口返回对象
 * @author willian
 * @created 2017-07-05 15:09
 * @email 18702515157@163.com
 **/
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
