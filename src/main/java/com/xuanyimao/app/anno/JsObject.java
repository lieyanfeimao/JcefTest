package com.xuanyimao.app.anno;

import java.lang.annotation.*;

/**
 * @Description:自动注入扫描器的对象
 * @author liuming
 */
@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface JsObject {

}