package com.xuanyimao.app;

import com.xuanyimao.app.common.ApplicationData;
import com.xuanyimao.app.core.XstManager;
import com.xuanyimao.app.util.LogUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.cef.CefApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class StartupApp {

    /** 开发环境目录 **/
    public final static String JCEF_DEV_BIN_FOLDER_PATH="bin"+File.separator+"win64";
    /** windows 上安装后的目录 */
    public final static String JCEF_WINDOWS_INSTALL_BIN_FOLDER_PATH="app"+File.separator+"win64";

    //资源文件路径。以jar包方式运行时，这些目录如果不存在，则会从jar包中解压。
    private final static String[] res=new String[]{"view"};

    public static void main(String[] args) throws Exception {

        try {
            copyResources();
        }catch (Throwable e){
            e.printStackTrace();
            LogUtil.error(e.getMessage(),e);
        }


        ApplicationData.initData();

//        //必须在main()方法的开头调用此方法才能执行特定于平台的启动初始化。在Linux上，这将初始化Xlib多线程，而在macOS上，这会动态加载CEF框架。
//        if (!CefApp.startup(args)) {
//            LogUtil.info("JCEF初始化失败!");
//            return;
//        }

        //用于定位到二进制文件所在目录
        File binFolder=new File(JCEF_DEV_BIN_FOLDER_PATH);
        if(!binFolder.exists()){
            binFolder=new File(JCEF_WINDOWS_INSTALL_BIN_FOLDER_PATH);
        }

        LogUtil.info("软件目录："+System.getProperty("user.dir"));
        LogUtil.info("二进制文件目录：{}",binFolder.getAbsolutePath());
        //初始化并显示应用窗口
        XstManager.getInstance().initApp(binFolder);
    }

    /**
     * 复制资源到项目目录
     * @throws IOException
     */
    public static void copyResources() throws IOException {
        String dir = System.getProperty("user.dir");
        for (String path : res) {
            File file = new File(dir + File.separator + path.replace("/", File.separator));
            if (!file.exists()) {
                FileUtils.forceMkdirParent(file);
                FileUtils.forceMkdir(file);

                URL resource = StartupApp.class.getClassLoader().getResource(path);
                if ("file".equals(resource.getProtocol())) {//目前用不上
                    copyJcefBinFileByResources(path, dir + File.separator + path.replace("/", File.separator));
                } else if ("jar".equals(resource.getProtocol())) {
                    copyJcefBinFileByJar(
                            resource,
                            dir + File.separator,
                            res
                    );
                }

            }
        }
    }


    /**
     * 将所有jcef二进制文件复制到指定目录
     * @throws IOException
     */
    public static void copyJcefBinFileByResources(String resourcesFolder,String binFolder) throws IOException{
        Enumeration<URL> resources = StartupApp.class.getClassLoader().getResources(resourcesFolder);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();

            if ("file".equals(resource.getProtocol())) {
                File file = new File(resource.getFile());
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    for (File subFile : files) {
                        if(subFile.isDirectory()){
                            FileUtils.forceMkdir(new File(binFolder+subFile.getName()+File.separator));
                            copyJcefBinFileByResources(resourcesFolder+subFile.getName()+"/",binFolder+File.separator+subFile.getName()+File.separator);
                        }else{
                            copyResourcesFile( resourcesFolder+ subFile.getName(),binFolder+subFile.getName());
                        }
                    }
                }
            }
        }
    }

    /**
     * 复制文件到指定目录
     * @param resource   URL对象
     * @param destFolder 目标目录
     * @param prefix     用于匹配的目录前缀
     * @throws IOException
     */
    public static void copyJcefBinFileByJar(URL resource,String destFolder,String[] prefix) throws IOException{
        String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!")); // 去掉 "jar:file:" 和 "!/"
        jarPath=jarPath.replace("%20"," ");

        JarFile jarFile = new JarFile(jarPath);
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            boolean match = false;
            for (String p:prefix){
                if(entry.getName().startsWith(p)){
                    match = true;
                    break;
                }
            }
            if(!match || entry.isDirectory()){
                continue;
            }
            //创建父级目录
            FileUtils.forceMkdirParent(new File(destFolder+entry.getName().replace("/",File.separator)));
            //复制资源文件
            copyResourcesFile( entry.getName(),destFolder+entry.getName().replace("/",File.separator));
            LogUtil.info("生成文件: {}",entry.getName());
        }
        jarFile.close();
    }

    /**
     * 复制resources目录下的文件到指定目录
     * @param resourcesFilePath 资源文件路径
     * @param destFile 目标文件路径
     * @throws IOException
     */
    public static void copyResourcesFile(String resourcesFilePath, String destFile) throws IOException {
        InputStream is=StartupApp.class.getClassLoader().getResourceAsStream(resourcesFilePath);
        FileOutputStream fos=new FileOutputStream(destFile);
        IOUtils.copy(is, fos);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(fos);
    }
}
