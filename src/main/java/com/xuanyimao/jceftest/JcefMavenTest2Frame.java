package com.xuanyimao.jceftest;

import me.friwi.jcefmaven.impl.step.init.CefInitializer;
import org.cef.*;
import org.cef.browser.CefBrowser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * jcefmaven 测试类，适用于自己下载好了二进制文件，不需要手动添加二进制文件到项目库
 * @author liuming
 * @date 2026年07月18日 10:05
 */
public class JcefMavenTest2Frame extends JFrame {

    public static void main(String[] args) throws Exception {
        new JcefMavenTest2Frame();
    }

    public JcefMavenTest2Frame() throws Exception {
        //是否Linux系统
        boolean useOSR= OS.isLinux();

        File installDir=new File("bin"+File.separator+"win64");
        List<String> cefArgs=new ArrayList<>();
        CefSettings cefSettings=new CefSettings();
        cefSettings.windowless_rendering_enabled=useOSR;

        //非官方推荐方式，是否稳定有待验证
        //这段代码摘取自CefAppBuilder的build()方法
        CefApp cefApp= CefInitializer.initialize(installDir,cefArgs,cefSettings);

        //是否透明
        boolean isTransparent=false;

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
