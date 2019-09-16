/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月8日
 * @version V1.0 
 */
package com.xuanyimao.jceftest.bean;

import javax.swing.JLabel;

import org.cef.browser.CefBrowser;

/**
 * @Description:保存tab关联的对象
 * @author liuming
 */
public class TabBrowser {
	/**索引，与关闭按钮关联*/
	private int index;
	/**浏览器对象*/
	private CefBrowser browser;
	/**浏览器标题*/
	private JLabel title;
	/**
	 * 获取 索引，与关闭按钮关联
	 * @return index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * 设置 索引，与关闭按钮关联
	 * @param index 索引，与关闭按钮关联
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * 获取浏览器对象
	 * @return browser
	 */
	public CefBrowser getBrowser() {
		return browser;
	}
	/**
	 * 设置 浏览器对象
	 * @param browser browser
	 */
	public void setBrowser(CefBrowser browser) {
		this.browser = browser;
	}
	/**
	 * 设置浏览器标题
	 * @return title
	 */
	public JLabel getTitle() {
		return title;
	}
	/**
	 * 设置 浏览器标题
	 * @param title 浏览器标题
	 */
	public void setTitle(JLabel title) {
		this.title = title;
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
}
