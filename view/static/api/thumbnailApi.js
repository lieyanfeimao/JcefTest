var thumbnailApi={
    /**
     * 查询目录下所有图片文件
     * @param folder 目录路径
     * @returns {Promise<unknown>}
     */
    queryAllFile:function(folder){
        let params={folder:folder};
        return execJava("thumbnail.queryAllFile",params);
    },
    /**
     * 缩放图片
     * @param folder 原目录
     * @param saveFolder 存储目录
     * @param keepAspectRatio 是否保持比例缩放
     * @param width 宽度
     * @param height 高度
     */
    create:function(folder, saveFolder, keepAspectRatio, width, height){
        let params={folder:folder, saveFolder:saveFolder, keepAspectRatio:keepAspectRatio, width:width, height:height};
        return execJava("thumbnail.create",params);
    }
}




