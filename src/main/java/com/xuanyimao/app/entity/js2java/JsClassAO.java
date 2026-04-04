package com.xuanyimao.app.entity.js2java;

/**
 * 注解类对象
 * @author liuming
 */
public class JsClassAO {
	/**类文件对象*/
	private Class<?> cls;
	/**类实例对象*/
	private Object obj;
	/**类实例名称，如果注解未指定，则用类名（小写开头）*/
	private String name;
	/**js函数名前缀，如果未指定，不使用前缀*/
	private String prefix;
	/**完整类名，包名.类名*/
	private String clsName;
	
	/**
	 * 获取类文件对象
	 * @return cls 类文件对象
	 */
	public Class<?> getCls() {
		return cls;
	}
	/**
	 * 设置 类文件对象
	 * @param cls 类文件对象
	 */
	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
	/**
	 * 获取 类实例对象
	 * @return obj 类实例对象
	 */
	public Object getObj() {
		return obj;
	}
	/**
	 * 设置 类实例对象
	 * @param obj 类实例对象
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}
	/**
	 * 获取 类实例名称，如果注解未指定，则用类名（小写开头）
	 * @return name 类实例名称，如果注解未指定，则用类名（小写开头）
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 类实例名称，如果注解未指定，则用类名（小写开头）
	 * @param name 类实例名称，如果注解未指定，则用类名（小写开头）
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 完整类名，包名.类名
	 * @return clsName 完整类名，包名.类名
	 */
	public String getClsName() {
		return clsName;
	}
	/**
	 * 设置 完整类名，包名.类名
	 * @param clsName 完整类名，包名.类名
	 */
	public void setClsName(String clsName) {
		this.clsName = clsName;
	}

	/**
	 * 获取 js函数名前缀，如果未指定，不使用前缀
	 *
	 * @return prefix js函数名前缀，如果未指定，不使用前缀
	 */
	public String getPrefix() {
		return this.prefix;
	}

	/**
	 * 设置 js函数名前缀，如果未指定，不使用前缀
	 *
	 * @param prefix js函数名前缀，如果未指定，不使用前缀
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AnnoClass [cls=" + cls + ", obj=" + obj + ", name=" + name + ", clsName=" + clsName+", prefix="+prefix + "]";
	}
	public JsClassAO() {}
	/**
	 * @param cls 类文件对象
	 * @param obj 类实例对象
	 * @param name 类实例名称，如果注解未指定，则用类名（小写开头）
	 */
	public JsClassAO(Class<?> cls, Object obj, String name) {
		super();
		this.cls = cls;
		this.obj = obj;
		this.name = name;
	}
	/**
	 * @param cls 类文件对象
	 * @param obj 类实例对象
	 * @param name 类实例名称，如果注解未指定，则用类名（小写开头）
	 * @param clsName 完整类名，包名.类名
	 */
	public JsClassAO(Class<?> cls, Object obj, String name, String clsName) {
		super();
		this.cls = cls;
		this.obj = obj;
		this.name = name;
		this.clsName = clsName;
	}
	/**
	 * 
	 * @param cls 类文件对象
	 * @param obj 类实例对象
	 * @param name 类实例名称，如果注解未指定，则用类名（小写开头）
	 * @param clsName 完整类名，包名.类名
	 * @param prefix js对象名，如果未指定，浏览器不注入此JAVA对象
	 */
	public JsClassAO(Class<?> cls, Object obj, String name, String clsName, String prefix) {
		super();
		this.cls = cls;
		this.obj = obj;
		this.name = name;
		this.clsName = clsName;
		this.prefix=prefix;
	}


}
