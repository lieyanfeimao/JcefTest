package com.xuanyimao.app.handler;


import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefContextMenuParams;
import org.cef.callback.CefMenuModel;
import org.cef.callback.CefMenuModel.MenuId;
import org.cef.handler.CefContextMenuHandlerAdapter;

/**
 * 菜单
 * @author liuming
 */
public class XstContextMenuHandler extends CefContextMenuHandlerAdapter{
	
//	/**查看源码**/
//	private final static int MENU_ID_VIEWSOURCE=26501;
//	
//	/**添加一段js脚本**/
//	private final static int MENU_ID_ADDJS=26502;

	/** 开发者工具 **/
	private final static int MENU_ID_DEVTOOLS=26501;

	/* 
	 * 在显示上下文菜单之前调用
	 * @see org.cef.handler.CefContextMenuHandler#onBeforeContextMenu(org.cef.browser.CefBrowser, org.cef.browser.CefFrame, org.cef.callback.CefContextMenuParams, org.cef.callback.CefMenuModel)
	 */
	@Override
	public void onBeforeContextMenu(CefBrowser browser, CefFrame frame, CefContextMenuParams params, CefMenuModel model) {
		//清除菜单项
		model.clear();

		model.addItem(MenuId.MENU_ID_BACK, "后退");
		model.addItem(MenuId.MENU_ID_FORWARD, "前进");
		model.addSeparator();
		//剪切、复制、粘贴
		model.addItem(MenuId.MENU_ID_COPY, "复制");
		model.addItem(MenuId.MENU_ID_CUT, "剪切");
		model.addItem(MenuId.MENU_ID_PASTE, "粘贴");
		model.addSeparator();
        
        model.addItem(MenuId.MENU_ID_RELOAD, "刷新");

		model.addSeparator();

		model.addItem(MENU_ID_DEVTOOLS,"开发者工具");


	}

	/* 
	 * @see org.cef.handler.CefContextMenuHandler#onContextMenuCommand(org.cef.browser.CefBrowser, org.cef.browser.CefFrame, org.cef.callback.CefContextMenuParams, int, int)
	 */
	@Override
	public boolean onContextMenuCommand(CefBrowser browser, CefFrame frame, CefContextMenuParams params, int commandId, int eventFlags) {
		if(commandId==MENU_ID_DEVTOOLS) {
			browser.openDevTools();
			return true;
		}
		return false;
	}


}
