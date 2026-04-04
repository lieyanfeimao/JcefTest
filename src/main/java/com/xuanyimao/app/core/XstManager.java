package com.xuanyimao.app.core;


import com.xuanyimao.app.common.ApplicationData;
import com.xuanyimao.app.common.Constants;
import com.xuanyimao.app.entity.TabBrowser;
import com.xuanyimao.app.handler.*;
import com.xuanyimao.app.listener.TabCloseListener;
import org.apache.commons.lang3.StringUtils;
import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;
import org.cef.browser.CefMessageRouter.CefMessageRouterConfig;
import org.cef.handler.CefAppHandlerAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import java.util.Vector;


/**
 * XSuperTranslate 浏览器相关内容管理类
 * @author liuming
 *
 */
public class XstManager {

	private final static XstManager mg=new XstManager();
	private XstManager() {}
	public static XstManager getInstance() {
		return mg;
	}
	
	private static CefApp cefApp;
	
	private static CefClient client;
	
	private JFrame frame;
	
	private static JTabbedPane tabbedPane;
	
	private List<TabBrowser> tbList=new Vector<TabBrowser>();
	
	private int tbIndex=0; 
	
	private final static String TITLE_INFO="正在载入...";
	
	/***
	 * 初始化应用
	 * @author liuming
	 * @since 2023年8月18日
	 */
	public void initApp() {
		this.frame=new MainFrame();
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int option= JOptionPane.showConfirmDialog(
						frame, "确定退出系统? ", "提示 ",JOptionPane.YES_NO_OPTION);
				if(option==JOptionPane.YES_OPTION) {
					try {
						closeAllBrowser();
						CefApp.getInstance().dispose();
						frame.dispose();
					} catch (Error | Exception ex) {
						System.exit(0);
					}
				}else {
					return;
				}
			}
		});
		
		CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
            @Override
            public void stateHasChanged(CefAppState state) {
                // 退出应用
                if (state == CefAppState.TERMINATED) System.exit(0);
            }
        });
        //一些设置
		CefSettings settings = new CefSettings();
		//应用程序不使用无窗口渲染，请不要启用此值，因为这可能会降低某些系统上的渲染性能。
		settings.windowless_rendering_enabled = false;
		/** 缓存根目录 */
		settings.root_cache_path= ApplicationData.appPath+ File.separator+"cache";

		/** 远程调试端口，打开开发者工具。用 */
		settings.remote_debugging_port=1025;

		cefApp=CefApp.getInstance(settings);
		
		client=cefApp.createClient();
		//注册handler
		client.addLifeSpanHandler(new XstLifeSpanHandler());
		client.addDisplayHandler(new XstDisplayHandler());
		client.addContextMenuHandler(new XstContextMenuHandler());

		client.addLoadHandler(new XstCefLoadHandler());
		//添加js交互
		jsToJavaActive();
		
		//显示界面
		tabbedPane=new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
        
    	this.frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

    	
    	this.createBrowser(ApplicationData.indexPath(), Constants.TAB_INDEX_ID);
	}
	
	/**
	 * 获取CefClient对象
	 * @return client
	 */
	public static CefClient getClient() {
		return client;
	}

	/**
	 * 获取Frame窗口对象
	 * @return frame
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * 获取TabBrowser对象集合
	 * @return tbList
	 */
	public List<TabBrowser> getTbList() {
		return tbList;
	}
	/**
	 * 关闭所有浏览器窗口
	 * @author:liuming
	 */
	public void closeAllBrowser() {
		for(int i=tbList.size()-1;i>=0;i--) {
			TabBrowser tb=tbList.get(i);
			tb.getBrowser().close(true);
			tabbedPane.removeTabAt(i);
			System.out.println("移除索引为"+i+"的tab...");
		}
	}
	
	/**
	 * 根据url创建一个新的tab页
	 * @author:liuming
	 * @param url
	 * @param id  翻译任务的id，也是当前tab的标识符
	 * @return 最后一个tab的索引
	 */
	public void createBrowser(final String url,String id) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//用于标识浏览器对象的id
				String uuid="b_"+UUID.randomUUID().toString().replace("-", "");
				//url，是否屏幕外渲染，是否透明
				//url追加browserId参数
				String urlEnd="";
//				System.out.println(url);
				String nurl=url;
				int i1=nurl.indexOf("#");
				if(i1!=-1) {
					urlEnd=nurl.substring(i1);
					nurl=nurl.substring(0, i1);
				}
				nurl=nurl+(nurl.indexOf("?")==-1?"?browserId="+uuid:"&browserId="+uuid);
				
//				+(url.indexOf("?")==-1?"?browserId="+uuid:"&browserId="+uuid)
				CefBrowser browser = client.createBrowser(nurl+urlEnd, false, false);
//				System.out.println(uuid);
				tabbedPane.addTab(".", browser.getUIComponent());
				int lastIndex=tabbedPane.getTabCount()-1;
				tbIndex++;
				
				//创建自定义tab栏
				JPanel jp=new JPanel();
				//设置panel为卡片布局
//				jp.setLayout(new GridLayout(1, 1, 10, 0));
				
				JLabel ltitle=new JLabel(TITLE_INFO);
				JLabel lclose=new JLabel("X");
				jp.setOpaque(false);
				ltitle.setHorizontalAlignment(JLabel.LEFT);
				lclose.setHorizontalAlignment(JLabel.RIGHT);
				jp.add(ltitle);
				
				if(!Constants.TAB_INDEX_ID.equals(id)) {
					jp.add(lclose);
				}
				lclose.addMouseListener(new TabCloseListener(tbIndex));
				
				tabbedPane.setTabComponentAt(lastIndex, jp);
				
				TabBrowser tb=new TabBrowser(tbIndex, browser, ltitle,id,uuid);
				tbList.add(tb);
				
				tabbedPane.setSelectedIndex(lastIndex);
			}
		});
	}
	
	/**
	 * 修改标题
	 * @author:liuming
	 * @param browser
	 * @param title
	 */
	public void updateTabTitle(CefBrowser browser,String title) {
		if(StringUtils.isNotBlank(title)) {
			if(title.length()>12) title=title.substring(0, 12)+"...";
			for(TabBrowser tb:tbList) {
				if(tb.getBrowser()==browser) {
					tb.getTitle().setText(title);
					break;
				}
			}
		}
	}
	/**
	 * 移除tab
	 * @author:liuming
	 * @param browser
	 * @param index
	 */
	public void removeTab(CefBrowser browser,int index) {
		if(browser!=null) {
			for(int i=0;i<tbList.size();i++) {
				TabBrowser tb=tbList.get(i);
				if(tb.getBrowser()==browser) {
					tb.getBrowser().close(true);
					tabbedPane.removeTabAt(i);
					tbList.remove(i);
//					System.out.println("移除索引为"+i+"的tab");
					break;
				}
			}
			
		}else {
			
			for(int i=0;i<tbList.size();i++) {
				TabBrowser tb=tbList.get(i);
				if(tb.getIndex()==index) {
					tb.getBrowser().close(true);
					tabbedPane.removeTabAt(i);
					tbList.remove(i);
//					System.out.println("移除索引为"+i+"的tab");
					break;
				}
			}
		}
	}
	
	/**
	 * 获取当前选中的tab栏相关对象
	 * @author liuming
	 * @since 2023年9月8日
	 */
	public TabBrowser getSelectTabBrowser() {
		int index=tabbedPane.getSelectedIndex();
		if(index>=tbList.size()) index=0;
		return tbList.get(index);
	}
	
	/**
	 * 通知前端页面js更新数据
	 * @author liuming
	 * @since 2023年9月25日
	 * @param browserId
	 * @param data
	 * @throws UnsupportedEncodingException 
	 */
	public void notifyTranslateJs(String browserId,String data) {
		for(TabBrowser tb:tbList) {
			if(tb.getBrowserId().equals(browserId)) {
				try {
					tb.getBrowser().executeJavaScript("changeLog(\""+URLEncoder.encode(data,"UTF-8")+"\");", tb.getBrowser().getURL(), 0);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return;
			}
		}
	}
	/**
	 * js与java交互
	 * @author liuming
	 * @since 2023年8月18日
	 */
	public void jsToJavaActive() {
	   	//配置一个查询路由
		CefMessageRouterConfig cmrc=new CefMessageRouterConfig("java","javaCancel");
		//创建查询路由
		CefMessageRouter cmr=CefMessageRouter.create(cmrc);
		cmr.addHandler(new XstCefMessageRouterHandler(), true);
		client.addMessageRouter(cmr);
	}
}
