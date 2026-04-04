package com.xuanyimao.app.entity.js2java;

import java.util.List;
import java.util.Map;

/**
 * 注解数据类，保存所有注解信息，请用 ApplicationData.annoData 获取此实例对象
 * @author liuming
 */
public class AnnoData {
	
//	/** 注解扫描包配置，多个包以;号隔开  */
//    private String scannerPackage;
//    /**引入的jar文件列表*/
//    private List<String> extraJars;
    /** 注解类对象集合 */
    private List<JsClassAO> jsClassAOList;
    /** 方法集合 */
    private Map<String, JsFunctionAO> methodMap;
    
    /**
	 * 方法集合
	 * @return methodMap
	 */
	public Map<String, JsFunctionAO> getMethodMap() {
		return methodMap;
	}
	/**
	 * 设置 方法集合
	 * @param methodMap 方法集合
	 */
	public void setMethodMap(Map<String, JsFunctionAO> methodMap) {
		this.methodMap = methodMap;
	}
	/**
	 * 注解类对象集合
	 * @return annoClassList 注解类对象集合
	 */
	public List<JsClassAO> getAnnoClassList() {
		return jsClassAOList;
	}
	
	/**
	 * 设置 注解类对象集合
	 * @param jsClassAOList 注解类对象集合
	 */
	public void setAnnoClassList(List<JsClassAO> jsClassAOList) {
		this.jsClassAOList = jsClassAOList;
	}
//	/**
//     * 获取注解扫描包配置，多个包以;号隔开
//     * @return scannerPackage 注解扫描包配置，多个包以;号隔开
//     */
//    public String getScannerPackage() {
//        return scannerPackage;
//    }
//    /**
//     * 设置注解扫描包配置，多个包以;号隔开
//     * @param scannerPackage 注解扫描包配置，多个包以;号隔开
//     */
//    public void setScannerPackage(String scannerPackage) {
//        this.scannerPackage = scannerPackage;
//    }
//	/**
//	 * 获取引入的jar文件列表
//	 * @return extraJars 引入的jar文件列表
//	 */
//	public List<String> getExtraJars() {
//		return extraJars;
//	}
//	/**
//	 * 设置 引入的jar文件列表
//	 * @param extraJars 引入的jar文件列表
//	 */
//	public void setExtraJars(List<String> extraJars) {
//		this.extraJars = extraJars;
//	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AnnoRepertory [ annoClassList=" + jsClassAOList + "]";
	}
}
