/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月4日
 * @version V1.0 
 */
package com.xuanyimao.app.handler;


import com.xuanyimao.app.core.XstManager;
import com.xuanyimao.app.util.LogUtil;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefDisplayHandlerAdapter;

/**
 * 用于处理显示事件的适配器类
 * @author liuming
 */
public class XstDisplayHandler extends CefDisplayHandlerAdapter {

	@Override
	public void onStatusMessage(CefBrowser browser, String value) {
//		System.out.println("状态消息:"+value);
		super.onStatusMessage(browser, value);
	}

	/**
	 * 浏览器标题已改变
	 */
	@Override
	public void onTitleChange(CefBrowser browser, String title) {
//		System.out.println("标题改变："+title);
		//在此处更改tab栏标题
		XstManager.getInstance().updateTabTitle(browser, title);
		super.onTitleChange(browser, title);
	}

	@Override
	public boolean onConsoleMessage(CefBrowser browser, CefSettings.LogSeverity level, String message, String source, int line) {
		LogUtil.info(source+":[行号:"+line+"]["+level + "]: " + message);
		return super.onConsoleMessage(browser, level, message, source, line);
	}
}
