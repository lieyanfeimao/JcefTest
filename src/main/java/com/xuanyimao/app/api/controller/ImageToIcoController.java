package com.xuanyimao.app.api.controller;

import com.xuanyimao.app.anno.JsClass;
import com.xuanyimao.app.anno.JsFunction;
import com.xuanyimao.app.entity.Message;
import com.xuanyimao.app.util.ImageToIcoUtil;
import com.xuanyimao.app.util.LogUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * png图片转ico文件
 * @author liuming
 * @date 2026年04月03日 16:09
 */
@JsClass(name="imageToIco")
public class ImageToIcoController {

    /**
     * 转换图片为ico文件
     * @param path
     * @param savePath
     * @return
     */
    @JsFunction
    public Message convert(String path,String savePath){
        try{
            if(StringUtils.isBlank(path) || StringUtils.isBlank(savePath)){
                return Message.error("原文件路径和ico文件存储路径不能为空");
            }
            if(!savePath.endsWith(".ico")){
                savePath+=".ico";
            }
            ImageToIcoUtil.convertToIco(path,savePath);
            return Message.success();
        } catch (Exception e) {
            LogUtil.getLogger().error("生成ico图标失败：{}",e.getMessage(),e);
            return Message.error("生成ico图标失败："+e.getMessage());
        }
    }
}
