package com.sean.base.result;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class DataResult implements Serializable{

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String message;

    // 响应中的数据
    private Object data;

    public DataResult() {
    }
    public DataResult(Object data) {
        this.code = 200;
        this.message = "OK";
        this.data = data;
    }
    public DataResult(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public DataResult(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // setter getter
    
    public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	// 静态方法
	
	public static DataResult ok() {
        return new DataResult(null);
    }
	
    public static DataResult ok(String message) {
        return new DataResult(message, null);
    }
    public static DataResult ok(Object data) {
        return new DataResult(data);
    }
    public static DataResult ok(String message, Object data) {
        return new DataResult(message, data);
    }

    public static DataResult build(Integer code, String message) {
        return new DataResult(code, message, null);
    }

    public static DataResult build(Integer code, String message, Object data) {
        return new DataResult(code, message, data);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }


    /**
     * JSON字符串转成 MengxueguResult 对象
     * @param json
     * @return
     */
    public static DataResult format(String json) {
        try {
            return JSON.parseObject(json, DataResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
	@Override
	public String toString() {
		return "DataResult [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
    
    

}
