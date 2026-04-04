package com.xuanyimao.app.entity.js2java;

/**
 * 方法参数
 * @author liuming
 */
public class JsFunctionParam {
	/**参数类型*/
	private Class<?> cls;
	/**参数名*/
	private String name;
	
	/** 
	 * 获取  参数类型 
	 * @return cls 参数类型 
	 */
	public Class<?> getCls() {
		return cls;
	}
	/** 
	 * 设置 参数类型
	 * @param cls 参数类型
	 */
	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
	/** 
	 * 获取  参数名 
	 * @return name 参数名 
	 */
	public String getName() {
		return name;
	}
	/** 
	 * 设置 参数名
	 * @param name 参数名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 */
	public JsFunctionParam() {
		super();
	}
	/**
	 * @param cls
	 * @param name
	 */
	public JsFunctionParam(Class<?> cls, String name) {
		super();
		this.cls = cls;
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MethodParam [cls=" + cls + ", name=" + name + "]";
	}
	
}
