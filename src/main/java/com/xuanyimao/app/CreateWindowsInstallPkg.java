package com.xuanyimao.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * windows打包。执行maven安装后再运行此类。<br>
 *
 * @author liuming
 */
public class CreateWindowsInstallPkg {

    //版本号
    private final static String VERSION="1.0.1";

    //主 jar 名
    private final static String JAR_FILE_NAME="jcef-test.jar";

    //安装在电脑上的位置
    private final static String INSTALL_FOLDER="jcef-test";

    //安装包名
    private final static String INSTALL_FILE_NAME="jcef-test";


    public static void main(String[] args) throws Exception {
        //使用jpackage进行打包，jdk1.8等低版本无此文件，则无法使用此功能
        String javaHome = System.getProperty("java.home");
        String jpackagePath=javaHome+ File.separator+"bin"+File.separator+"jpackage.exe";
        File file=new File(jpackagePath);
        if(!file.exists()){
            throw new RuntimeException("jpackage.exe 不存在，无法打包。jpackage仅存在于高版本jdk，建议使用openjdk 17及以上版本。");
        }
        //软件图标，windows上建议用ico图标
        String logoPath=System.getProperty("user.dir")+File.separator+"logo.ico";
        //文件所在路径
        String folder=System.getProperty("user.dir")+File.separator+"bin";

        System.out.println(logoPath);
        System.out.println(folder);

        //示例
        //jpackage -i bin -n jcef-test --install-dir "jcef-test" --icon logo.ico --java-options "-Djava.library.path=.\app\win64" --app-version 1.0.0 --win-shortcut --win-menu --win-dir-chooser --main-jar jcef-test.jar
        ProcessBuilder builder = new ProcessBuilder(jpackagePath, "-i", folder,
                "-n",INSTALL_FILE_NAME, "--install-dir", INSTALL_FOLDER,
                //指定软件图标
                "--icon", logoPath,
//                //指定安装目录下的二进制文件的路径。使用jcefmaven加载二进制文件不需要这个配置
//                "--java-options","-Djava.library.path=.\\app\\win64",
                //指定版本号
                "--java-options", "-DappVersion="+VERSION,"--app-version",VERSION,
                "--win-shortcut","--win-menu","--win-dir-chooser",
                //指定主jar文件
                "--main-jar",JAR_FILE_NAME);
        builder.redirectErrorStream(true); // 合并标准错误和标准输出
        Process process = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
        String line="";
        System.out.println("开始打包，安装包将生成在项目根目录下");
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        System.out.println("打包完成");

        System.out.println("清理jar文件。再次打包请先执行maven安装。");
        File binFolder=new File(folder);
        File[] files=binFolder.listFiles();
        for (File f:files){
            //保留目录
            if(f.isDirectory()){
                continue;
            }
            if(f.getName().toLowerCase().endsWith(".jar")) f.delete();
        }
        System.out.println("清理jar文件完成");

    }
}
