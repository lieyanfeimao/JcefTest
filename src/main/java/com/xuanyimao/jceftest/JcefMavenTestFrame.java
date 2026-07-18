package com.xuanyimao.jceftest;

import me.friwi.jcefmaven.*;
import me.friwi.jcefmaven.impl.progress.ConsoleProgressHandler;
import org.cef.*;
import org.cef.browser.CefBrowser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

/**
 * 用于测试 jcefmaven 库。不需要手动添加二进制文件到项目库
 * @author liuming
 * @date 2026年07月17日 14:20
 */
public class JcefMavenTestFrame extends JFrame {

    public static void main(String[] args) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        new JcefMavenTestFrame();

//        System.out.println(new File("bin").getAbsolutePath());
    }

    public JcefMavenTestFrame() throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {

        //是否Linux系统
        boolean useOSR=OS.isLinux();

        //创建一个CefAppBuilder实例
        CefAppBuilder builder = new CefAppBuilder();

        //可以设置此值跳过jcef二进制文件下载，否则会删除原目录重新下载并解压
        builder.setSkipInstallation(true);

        //配置 builder 示例
        //设置二进制文件所在目录
        builder.setInstallDir(new File("bin"+File.separator+"win64"));
        //下载用
        builder.setProgressHandler(new ConsoleProgressHandler());
        //设置参数的示例
        builder.addJcefArgs("--disable-gpu");
        //这个值(离屏渲染)如果设置为true，会导致窗口无法点击
        builder.getCefSettings().windowless_rendering_enabled = useOSR;

        //Set an app handler. Do not use CefApp.addAppHandler(...), it will break your code on MacOSX!
        //设置maven app  handler，不要使用CefApp.addAppHandler(...)。
        //这个时候CefApp的二进制库还没有加载
        builder.setAppHandler(new MavenCefAppHandlerAdapter(){});

        CefApp cefApp =builder.build();

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
