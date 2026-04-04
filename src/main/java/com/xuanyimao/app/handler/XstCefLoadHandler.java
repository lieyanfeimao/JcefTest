package com.xuanyimao.app.handler;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLoadHandlerAdapter;

/**
 * 加载状态改变
 */
public class XstCefLoadHandler extends CefLoadHandlerAdapter {

    @Override
    public void onLoadEnd(CefBrowser browser, CefFrame frame, int httpStatusCode) {
        System.out.println(frame.getURL()+"  "+frame.getIdentifier()+" "+frame.getName());
//        String url=frame.getURL();
//        if(url.matches("^https://baidu.com/.*?")) {
//            frame.executeJavaScript("console.log('打开了百度');",null,0);
//        }

        super.onLoadEnd(browser, frame, httpStatusCode);
    }
}
