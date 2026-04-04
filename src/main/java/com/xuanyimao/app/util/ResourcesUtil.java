package com.xuanyimao.app.util;

import java.net.URL;

/**
 * Resources 目录资源
 * @author liuming
 * @date 2025年12月22日 15:58
 */
public class ResourcesUtil {

    /***
     * 根据资源路径获取URL
     * @param path
     * @return
     */
    public static URL getURLByResourcesPath(String path){
        return Thread.currentThread().getContextClassLoader().getResource(path);
    }

    /***
     * 根据资源路径获取路径
     * @param path
     * @return
     */
    public static String getPathByResourcesPath(String path){
        return Thread.currentThread().getContextClassLoader().getResource(path).toExternalForm();
    }
}
