package com.xuanyimao.app.core;




import com.xuanyimao.app.common.ApplicationData;
import com.xuanyimao.app.common.Constants;

import javax.swing.*;


/***
 * 主界面
 * @author liuming
 *
 */
public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8061877096368582487L;
	
	public MainFrame() {
    	ImageIcon icon=new ImageIcon(ApplicationData.logoImgPath());
    	this.setIconImage(icon.getImage());
    	this.pack();
    	this.setVisible(true);
    	this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	this.setTitle(Constants.APP_NAME+" - "+Constants.VERSION);
	}
}
