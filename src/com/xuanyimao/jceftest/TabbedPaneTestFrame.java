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
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.OS;
import org.cef.CefApp.CefAppState;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefAppHandlerAdapter;
import com.xuanyimao.jceftest.bean.TabBrowser;
import com.xuanyimao.jceftest.handler.DisplayHandler;
import com.xuanyimao.jceftest.handler.LifeSpanHandler;
import com.xuanyimao.jceftest.handler.MenuHandler;
import com.xuanyimao.jceftest.listener.TabCloseListener;

/**
 * @Description:以tab栏形式显示浏览器窗口
 * @author liuming
 */
public class TabbedPaneTestFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 871314861019393323L;

	private static CefApp cefApp;
	
	private static CefClient cefClient;
	
	private boolean useOSR;
	
	private boolean isTransparent;
	/**tabbedPane对象*/
	private static JTabbedPane tabbedPane;
	/**TabBrowser对象列表**/
	private List<TabBrowser> tbList=new Vector<TabBrowser>();
	/**tab使用的索引。此索引不是tab在tabbedpane中的索引，此索引用来移除tab栏**/
	private int tbIndex=0; 
	/**默认的标题名*/
	private final static String TITLE_INFO="正在载入...";
	
	public TabbedPaneTestFrame(String url) {
		//是否Linux系统
		useOSR=OS.isLinux();
		//是否透明
		isTransparent=false;
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
        cefApp=CefApp.getInstance(settings);
        //创建客户端实例
        cefClient = cefApp.createClient();
        //添加鼠标右键菜单handler
        cefClient.addContextMenuHandler(new MenuHandler());
		//添加浏览器标题更改handler
        cefClient.addDisplayHandler(new DisplayHandler(this));
        //添加浏览器窗口弹出handler
        cefClient.addLifeSpanHandler(new LifeSpanHandler(this));
        
        tabbedPane=new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
        
    	getContentPane().add(tabbedPane, BorderLayout.CENTER);
		pack();
		setTitle("测试JCEF-Tab栏");
        setSize(800, 600);
        setVisible(true);
        //添加一个窗口关闭监听事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	closeAllBrowser();
                CefApp.getInstance().dispose();
                dispose();
            }
        });
        createBrowser("http://www.baidu.com");
	}
	
	/**
	 * 关闭所有浏览器
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
	 * @return 最后一个tab的索引
	 */
	public int createBrowser(String url) {
		CefBrowser browser = cefClient.createBrowser(url, useOSR, isTransparent);
		tabbedPane.addTab(".", browser.getUIComponent());
		int lastIndex=tabbedPane.getTabCount()-1;
		tbIndex++;
		
		//创建自定义tab栏
		JPanel jp=new JPanel();
		
		JLabel ltitle=new JLabel(TITLE_INFO);
		JLabel lclose=new JLabel("X");
		jp.setOpaque(false);
		ltitle.setHorizontalAlignment(JLabel.LEFT);
		lclose.setHorizontalAlignment(JLabel.RIGHT);
		jp.add(ltitle);
		jp.add(lclose);
		//添加关闭按钮监听事件
		lclose.addMouseListener(new TabCloseListener(tbIndex,this));
		//设置tab栏标题的关键句
		tabbedPane.setTabComponentAt(lastIndex, jp);
		
		TabBrowser tb=new TabBrowser(tbIndex, browser, ltitle);
		tbList.add(tb);
		
		tabbedPane.setSelectedIndex(lastIndex);
		return lastIndex;
	}
	
	/**
	 * 修改标题
	 * @author:liuming
	 * @param browser
	 * @param title
	 */
	public void updateTabTitle(CefBrowser browser,String title) {
		if(title!=null && !"".equals(title)) {
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
	
	
	public static void main(String[] args) {
		new TabbedPaneTestFrame("http://www.baidu.com");
	}
}
