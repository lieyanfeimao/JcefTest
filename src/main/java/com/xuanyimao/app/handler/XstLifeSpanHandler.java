/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月4日
 * @version V1.0 
 */
package com.xuanyimao.app.handler;


import com.xuanyimao.app.core.XstManager;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLifeSpanHandlerAdapter;

/**
 * 用于处理生命周期事件的适配器类
 * @author liuming
 */
public class XstLifeSpanHandler extends CefLifeSpanHandlerAdapter {

	/**
	 * 在创建新的弹出窗口之前在IO线程上调用
	 * 返回true取消弹出窗口的创建
	 */
	@Override
	public boolean onBeforePopup(CefBrowser browser, CefFrame frame, String targetUrl, String targetFrameName) {
//		System.out.println(getParam(targetUrl, "id"));
		XstManager.getInstance().createBrowser(targetUrl,getParam(targetUrl, "id"));
		return true;
	}
	
	/**
	 * 获得参数的值
	 * @author liuming
	 * @since 2023年9月20日
	 * @param url
	 * @param name
	 * @return
	 */
	private String getParam(String url,String name) {
		int i1=url.indexOf("?");
		if(i1==-1) return "";
		String params=url.substring(i1+1);
		String[] arrs=params.split("&");
		for(String str:arrs) {
			i1=str.indexOf(name+"=");
			if(i1==0) {
				return str.substring(name.length()+2);
			}
		}
		return "";
	}
}
