REM 如果环境变量配置的jdk不包含jpackage(jdk 14以上才有)，请使用jpackage的绝对路径，如：D:\xxx\jpackage
REM 每次打包时，请修改版本号(--app-version)，否则会无法安装。如果不修改版本号，请先手动卸载已安装的版本
REM --java-options 指定了安装后jcef二进制文件路径。应用安装后，二进制文件和jar包位于 安装目录\app 下。由于启动器是安装目录下的 jcef-test.exe，所以指定的路径是 .\app\win64
jpackage -i bin -n jcef-test --install-dir "jcef-test" --icon logo.ico --java-options "-Djava.library.path=.\app\win64" --app-version 1.0.0 --win-shortcut --win-menu --win-dir-chooser --main-jar jcef-test.jar