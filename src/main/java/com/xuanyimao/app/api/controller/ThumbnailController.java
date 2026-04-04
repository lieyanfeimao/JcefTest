package com.xuanyimao.app.api.controller;

import com.xuanyimao.app.anno.JsClass;
import com.xuanyimao.app.anno.JsFunction;
import com.xuanyimao.app.anno.JsObject;
import com.xuanyimao.app.api.service.ThumbnailService;
import com.xuanyimao.app.entity.Message;
import com.xuanyimao.app.util.LogUtil;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * 缩略图
 * @author liuming
 * @date 2026年04月03日 08:52
 */
@JsClass(name="thumbnail")
public class ThumbnailController {

    @JsObject
    ThumbnailService thumbnailService;

    /**
     * 查询所有图片文件
     * @param folder
     * @return
     */
    @JsFunction
    public Message queryAllFile(String folder){
        try {
            File folderFile = new File(folder);
            if(!folderFile.exists() || !folderFile.isDirectory()){
                return Message.error("请选择一个目录");
            }
            return Message.success("操作成功",thumbnailService.queryAllImageFile(folderFile));
        } catch (Exception e) {
            LogUtil.getLogger().error("获取目录下所有图片文件失败：{}",e.getMessage(),e);
            return Message.error("获取目录下所有图片文件失败："+e.getMessage());
        }
    }

    /**
     * 生成缩略图
     * @param folder 原目录
     * @param saveFolder 存储目录
     * @param keepAspectRatio 是否保持
     * @param width 宽度
     * @param height 高度
     * @return
     */
    @JsFunction
    public Message create(String folder,String saveFolder,Boolean keepAspectRatio,Integer width,Integer height) {
        try{
            File folderFile = new File(folder);
            if(!folderFile.exists() || !folderFile.isDirectory()){
                return Message.error("请选择一个目录");
            }
            if(StringUtils.isBlank(saveFolder)){
                saveFolder=folder;
            }

            File saveFolderFile = new File(saveFolder);
            if(!saveFolderFile.exists() || !saveFolderFile.isDirectory()){
                return Message.error("无效的存储目录");
            }
            //默认保持长宽比
            if(keepAspectRatio==null){
                keepAspectRatio=false;
            }
            if(width==null && height==null){
                return Message.error("长度和宽度一个都不给，我很难办！难办，那就不要办了！");
            }
            if(width==null){
                width=99999;
            }
            if(height==null){
                height=99999;
            }

            thumbnailService.create(folderFile,saveFolderFile,keepAspectRatio,width,height);
            return Message.success();
        } catch (Exception e) {
            LogUtil.getLogger().error("生成缩略图失败：{}",e.getMessage(),e);
            return Message.error("生成缩略图失败："+e.getMessage());
        }
    }
}
