/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月8日
 * @version V1.0 
 */
package com.xuanyimao.jceftest;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.cef.CefApp;
import org.cef.CefSettings;
import org.cef.OS;
import org.cef.browser.CefBrowser;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.handler.CefAppHandlerAdapter;

/**
 * @Description: 简单的JCEF测试
 * @author liuming
 */
public class TestFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7410082787754606408L;

	public static void main(String[] args) {
		new TestFrame();
	}
	
	public TestFrame() {
		//是否Linux系统
		boolean useOSR=OS.isLinux();
		//是否透明
		boolean isTransparent=false;
		//添加Handler，在CEFAPP状态为终止时退出程序
		CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
            @Override
            public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                // Shutdown the app if the native CEF part is terminated
                if (state == CefAppState.TERMINATED) System.exit(0);
            }
        });
		
        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = useOSR;
		//获取CefApp实例
        CefApp cefApp=CefApp.getInstance(settings);
        //创建客户端实例
        CefClient cefClient = cefApp.createClient();
        //创建浏览器实例
        CefBrowser cefBrowser = cefClient.createBrowser("http://www.baidu.com", useOSR, isTransparent);
		
        //将浏览器UI添加到窗口中
		
		getContentPane().add(cefBrowser.getUIComponent(), BorderLayout.CENTER);
		
		pack();
		setTitle("测试JCEF打开百度");
        setSize(800, 600);
        setVisible(true);
        //添加一个窗口关闭监听事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
                dispose();
            }
        });
	}
}