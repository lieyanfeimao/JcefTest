/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月8日
 * @version V1.0 
 */
package com.xuanyimao.jceftest.handler;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLifeSpanHandlerAdapter;

import com.xuanyimao.jceftest.TabbedPaneTestFrame;

/**
 * @Description: 监听弹出窗口
 * @author liuming
 */
public class LifeSpanHandler extends CefLifeSpanHandlerAdapter {
	
	private TabbedPaneTestFrame frame;
	
	public LifeSpanHandler(TabbedPaneTestFrame frame) {
		this.frame=frame;
	}
	
	/* (non-Javadoc)
	 * @see org.cef.handler.CefLifeSpanHandlerAdapter#onBeforePopup(org.cef.browser.CefBrowser, org.cef.browser.CefFrame, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean onBeforePopup(CefBrowser browser, CefFrame frame, String target_url, String target_frame_name) {
		this.frame.createBrowser(target_url);
		//返回true表示取消弹出窗口
		return true;
	}
}
