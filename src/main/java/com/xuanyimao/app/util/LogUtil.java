package com.xuanyimao.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 */
public class LogUtil {

    private final static Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public final static Logger getLogger(){
        return logger;
    }

    public static void info(String msg,Object... args){
        logger.info(msg,args);
    }

    public static void warn(String msg,Object... args){
        logger.warn(msg,args);
    }

    public static void error(String msg,Object... args){
        logger.error(msg,args);
    }
}
