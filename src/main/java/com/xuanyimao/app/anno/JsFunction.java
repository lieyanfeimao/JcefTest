package com.xuanyimao.app.anno;

import java.lang.annotation.*;

/**
 * javascript函数注解，程序启动时扫描器会扫描指定包下的包含JsClass注解的类，收集包含JsFunction注解的方法
 * 
 * @author liuming
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface JsFunction {
	/**函数名 - 不填使用方法名*/
    String name() default "";
    /**方法的描述*/
    String desc() default "";
}
