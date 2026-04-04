package com.xuanyimao.app.anno;

import java.lang.annotation.*;

/**
 * 自动注入的@JsClass标记的对象
 * @author liuming
 */
@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface JsObject {

}