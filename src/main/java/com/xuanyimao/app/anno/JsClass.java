package com.xuanyimao.app.anno;

import java.lang.annotation.*;

/**
 * javascript类注解，程序启动时扫描器会扫描指定包下的包含JsClass注解的类
 * jsname为前端公共JS函数调用java方法时的前缀名  如@JsClass(prefix="Test")，JS调用方式为window.java({request:"Test.xx"});
 * @author liuming
 */
@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface JsClass {
	/**类实例名，可根据这个名称获取保存在扫描器的类实例对象*/
	String name() default "";
	/**JS函数名称前缀 - 不填使用类名 **/
	String prefix() default "";
}
