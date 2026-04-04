package com.xuanyimao.app.api.service;

import com.xuanyimao.app.anno.JsClass;
import com.xuanyimao.app.util.LogUtil;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuming
 * @date 2026年04月03日 08:53
 */
@JsClass
public class ThumbnailService {

    /**
     * 递归查询目录下所有文件
     * @param folderFile
     * @return
     */
    public List<String> queryAllImageFile(File folderFile){
        List<String> list = new ArrayList<String>();
        File[] files=folderFile.listFiles();
        if(files!=null){
            for(File f:files){
                if(f.isFile()){
                    String fileName = f.getName().toLowerCase();
                    if(isImageFile(fileName)){
                        list.add(f.getAbsolutePath());
                    }
                }else if(f.isDirectory()){
                    list.addAll(queryAllImageFile(f));
                }
            }
        }
        return list;
    }


    public void create(File folderFile,File saveFolderFile,boolean keepAspectRatio,int width,int height) throws IOException {
        File[] files=folderFile.listFiles();
        if(files!=null){
            for(File f:files){
                if(f.isFile()){
                    String fileName = f.getName().toLowerCase();
                    if(isImageFile(fileName)){
                        File file1 = new File(saveFolderFile.getAbsolutePath()+File.separator+fileName);

                        System.out.println(file1.getAbsolutePath());

                        createThumbnail(f,file1,keepAspectRatio,width,height);
                    }
                }else if(f.isDirectory()){
                    File folder1=new File(saveFolderFile+File.separator+f.getName());

                    //创建目录
                    Path destFolder = Paths.get(folder1.getAbsolutePath());
                    Files.createDirectories(destFolder);

                    create(f,folder1,keepAspectRatio,width,height);
                }
            }
        }
    }

    /**
     * 生成缩略图
     * @param srcFile 原文件
     * @param destFile 生成的文件
     * @param keepAspectRatio 是否保持比例
     * @param width  宽度
     * @param height  高度
     * @throws IOException
     */
    private void createThumbnail(File srcFile,File destFile,boolean keepAspectRatio,int width,int height) {
        try {
//            System.out.println(srcFile.getAbsolutePath());
//            System.out.println(destFile.getAbsolutePath());
//            System.out.println(width+"*"+height);
//            System.out.println(keepAspectRatio);
            BufferedImage image = ImageIO.read(srcFile);
            if(image.getWidth()>width || image.getHeight()>height) {
                Thumbnails.of(srcFile)
                        .size(width, height)
                        .keepAspectRatio(keepAspectRatio)//保持比例
                        .toFile(destFile); // 保存到文件
            }
        } catch (Exception | Error e) {
            LogUtil.getLogger().error("生成缩略图失败:{} -> {}",srcFile.getAbsolutePath(),destFile.getAbsolutePath(),e);
        }
    }

    /**
     * 是否是图片文件
     */
    private final static String[] suffix = {"jpg", "jpeg", "png","bmp"};
    private boolean isImageFile(String fileName){
        for (String suffix : suffix) {
            if(fileName.endsWith(suffix)){
                return true;
            }
        }
        return false;
    }
}
