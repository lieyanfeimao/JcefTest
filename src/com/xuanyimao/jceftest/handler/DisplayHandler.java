/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月8日
 * @version V1.0 
 */
package com.xuanyimao.jceftest.handler;

import org.cef.browser.CefBrowser;
import org.cef.handler.CefDisplayHandlerAdapter;

import com.xuanyimao.jceftest.TabbedPaneTestFrame;

/**
 * @Description:用于更新tab标题
 * @author liuming
 */
public class DisplayHandler extends CefDisplayHandlerAdapter {
	
	private TabbedPaneTestFrame frame;
	
	
	public DisplayHandler(TabbedPaneTestFrame frame) {
		this.frame=frame;
	}
	
	/* (non-Javadoc)
	 * @see org.cef.handler.CefDisplayHandlerAdapter#onTitleChange(org.cef.browser.CefBrowser, java.lang.String)
	 */
	@Override
	public void onTitleChange(CefBrowser browser, String title) {
		this.frame.updateTabTitle(browser, title);
//		super.onTitleChange(arg0, arg1);
	}
	
	
}
