/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月8日
 * @version V1.0 
 */
package com.xuanyimao.jceftest.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.xuanyimao.jceftest.TabbedPaneTestFrame;

/**
 * @Description: tab关闭事件监听器
 * @author liuming
 */
public class TabCloseListener implements MouseListener{
	
	private int index;
	
	private TabbedPaneTestFrame frame;
	
	
	public TabCloseListener(int index,TabbedPaneTestFrame frame) {
		this.index=index;
		this.frame=frame;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("点击了关闭事件...");
		
		frame.removeTab(null, index);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
