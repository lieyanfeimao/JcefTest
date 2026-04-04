var imageToIcoApi={
    /**
     * 转换图片为ico文件
     * @param folder 目录路径
     * @returns {Promise<unknown>}
     */
    convert:function(path,savePath){
        let params={path:path,savePath:savePath};
        return execJava("imageToIco.convert",params);
    }
}




