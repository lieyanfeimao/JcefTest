## JcefTest
JCEF测试工程，包含JAVA与JS交互，鼠标右键菜单，以Tab形式展示浏览器

## 使用方式
编译环境：JDK1.8
将lib下的jar包Add to build path，解压binary_win64目录下的binary_win64.zip，解压时请选择“解压到当前文件夹”（所有二进制文件加起来160M）
项目>Properties(属性)>Java Build Path，展开Jdk，选中Native library location，点击Edit，选择当前项目目录下的binary_win64
随便选择一个测试类运行
注意，本工程只在Windows 64位电脑上测试，如需其他版本的，请自行编译JCEF。
编译教程：https://bitbucket.org/chromiumembedded/java-cef/wiki/BranchesAndBuilding#markdown-header-building-from-source
jcef含中文翻译的帮助文档，请访问 http://www.xuanyimao.com/jcef/index.html 获取下载链接