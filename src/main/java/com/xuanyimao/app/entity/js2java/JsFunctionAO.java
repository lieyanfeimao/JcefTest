package com.xuanyimao.app.entity.js2java;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 注解方法对象
 * @author liuming
 */
public class JsFunctionAO {
	
	/**方法对象*/
	private Method method;
	
	/**方法注释*/
	private String desc;
	
	/**类对象*/
	private JsClassAO jsClassAO;
	
	/** 方法参数对象列表 */
	private List<JsFunctionParam> jsFunctionParam;

	
	/** 
	 * 获取  方法对象 
	 * @return method 方法对象 
	 */
	public Method getMethod() {
		return method;
	}

	/** 
	 * 设置 方法对象
	 * @param method 方法对象
	 */
	public void setMethod(Method method) {
		this.method = method;
	}

	/** 
	 * 获取  方法注释 
	 * @return desc 方法注释 
	 */
	public String getDesc() {
		return desc;
	}

	/** 
	 * 设置 方法注释
	 * @param desc 方法注释
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/** 
	 * 获取  类对象 
	 * @return annoClass 类对象 
	 */
	public JsClassAO getAnnoClass() {
		return jsClassAO;
	}

	/** 
	 * 设置 类对象
	 * @param jsClassAO 类对象
	 */
	public void setAnnoClass(JsClassAO jsClassAO) {
		this.jsClassAO = jsClassAO;
	}

	/** 
	 * 获取  方法参数对象列表 
	 * @return methodParam 方法参数对象列表 
	 */
	public List<JsFunctionParam> getMethodParam() {
		return jsFunctionParam;
	}

	/** 
	 * 设置 方法参数对象列表
	 * @param jsFunctionParam 方法参数对象列表
	 */
	public void setMethodParam(List<JsFunctionParam> jsFunctionParam) {
		this.jsFunctionParam = jsFunctionParam;
	}

	/**
	 * @param method
	 * @param desc
	 * @param jsClassAO
	 * @param jsFunctionParam
	 */
	public JsFunctionAO(Method method, String desc, JsClassAO jsClassAO, List<JsFunctionParam> jsFunctionParam) {
		super();
		this.method = method;
		this.desc = desc;
		this.jsClassAO = jsClassAO;
		this.jsFunctionParam = jsFunctionParam;
	}

	/**
	 * 
	 */
	public JsFunctionAO() {
		super();
	}

	/**
	 * @param method
	 * @param jsClassAO
	 * @param jsFunctionParam
	 */
	public JsFunctionAO(Method method, JsClassAO jsClassAO, List<JsFunctionParam> jsFunctionParam) {
		super();
		this.method = method;
		this.jsClassAO = jsClassAO;
		this.jsFunctionParam = jsFunctionParam;
	}

	/**
	 * @param method
	 * @param jsClassAO
	 */
	public JsFunctionAO(Method method, JsClassAO jsClassAO) {
		super();
		this.method = method;
		this.jsClassAO = jsClassAO;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AnnoMethod [method=" + method + ", annoClass=" + jsClassAO + ", methodParam=" + jsFunctionParam + "]";
	}
}
