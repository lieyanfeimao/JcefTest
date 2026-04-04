/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月4日
 * @version V1.0 
 */
package com.xuanyimao.app.entity;

import org.cef.browser.CefBrowser;

import javax.swing.*;

/**
 * 应用程序窗口的tab信息
 * @author liuming
 */
public class TabBrowser {
	
	/**索引,用于通过索引获取当前对象*/
	private int index;
	
	/**浏览器对象*/
	private CefBrowser browser;
	/**tab标题*/
	private JLabel title;
	
	/**翻译任务的id,用于调用指定浏览器中的js脚本**/
	private String id;
	
	/** 浏览器id ***/
	private String browserId;

	/**
	 * index
	 * @return index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 设置 index
	 * @param index index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * browser
	 * @return browser
	 */
	public CefBrowser getBrowser() {
		return browser;
	}

	/**
	 * 设置 browser
	 * @param browser browser
	 */
	public void setBrowser(CefBrowser browser) {
		this.browser = browser;
	}

	/**
	 * title
	 * @return title
	 */
	public JLabel getTitle() {
		return title;
	}

	/**
	 * 设置 title
	 * @param title title
	 */
	public void setTitle(JLabel title) {
		this.title = title;
	}

	/** 
	 * 获取  翻译任务的id,用于调用指定浏览器中的js脚本 
	 * @return id 翻译任务的id,用于调用指定浏览器中的js脚本 
	 */
	public String getId() {
		return id;
	}

	/** 
	 * 设置 翻译任务的id,用于调用指定浏览器中的js脚本
	 * @param id 翻译任务的id,用于调用指定浏览器中的js脚本
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param index
	 * @param browser
	 * @param title
	 */
	public TabBrowser(int index, CefBrowser browser, JLabel title) {
		super();
		this.index = index;
		this.browser = browser;
		this.title = title;
	}

	public TabBrowser(int index, CefBrowser browser, JLabel title, String id) {
		super();
		this.index = index;
		this.browser = browser;
		this.title = title;
		this.id = id;
	}

	
	public TabBrowser(int index, CefBrowser browser, JLabel title, String id, String browserId) {
		super();
		this.index = index;
		this.browser = browser;
		this.title = title;
		this.id = id;
		this.browserId = browserId;
	}

	/** 
	 * 获取  浏览器id 
	 * @return browserId 浏览器id 
	 */
	public String getBrowserId() {
		return browserId;
	}

	/** 
	 * 设置 浏览器id
	 * @param browserId 浏览器id
	 */
	public void setBrowserId(String browserId) {
		this.browserId = browserId;
	}
}
