package com.xuanyimao.app.entity;

/**
 * @Description: 处理结果对象
 * @author liuming
 */
public class Message {
	/**是否成功*/
	private Boolean ok;
	/**消息描述*/
	private String msg;
	/**数据内容*/
	private Object data;
	/**
	 * @param ok 是否成功
	 * @param msg 消息描述
	 * @param data 数据内容
	 */
	public Message(Boolean ok, String msg, Object data) {
		super();
		this.ok = ok;
		this.msg = msg;
		this.data = data;
	}
	/**
	 * 操作成功
	 * @author:liuming
	 * @return
	 */
	public static Message success() {
		return success("操作成功",null);
	}
	/**
	 * 操作成功
	 * @author:liuming
	 * @param msg 消息
	 * @return
	 */
	public static Message success(String msg) {
		return success(msg,null);
	}
	/**
	 * 操作成功
	 * @author:liuming
	 * @param msg 消息
	 * @param data 数据
	 * @return
	 */
	public static Message success(String msg,Object data) {
		return new Message(true, msg, data);
	}
	
	/**
	 * 操作失败
	 * @author:liuming
	 * @return
	 */
	public static Message error() {
		return error("操作失败",null);
	}
	/**
	 * 操作失败
	 * @author:liuming
	 * @param msg 消息
	 * @return
	 */
	public static Message error(String msg) {
		return error(msg,null);
	}
	/**
	 * 操作失败
	 * @author:liuming
	 * @param msg 消息
	 * @param data 数据
	 * @return
	 */
	public static Message error(String msg,Object data) {
		return new Message(false, msg, data);
	}
	/** 
	 * 获取  是否成功 
	 * @return ok 是否成功 
	 */
	public Boolean getOk() {
		return ok;
	}
	/** 
	 * 设置 是否成功
	 * @param ok 是否成功
	 */
	public void setOk(Boolean ok) {
		this.ok = ok;
	}
	/** 
	 * 获取  消息描述 
	 * @return msg 消息描述 
	 */
	public String getMsg() {
		return msg;
	}
	/** 
	 * 设置 消息描述
	 * @param msg 消息描述
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/** 
	 * 获取  数据内容 
	 * @return data 数据内容 
	 */
	public Object getData() {
		return data;
	}
	/** 
	 * 设置 数据内容
	 * @param data 数据内容
	 */
	public void setData(Object data) {
		this.data = data;
	}
}
