var commonApi={
    /**
     * 打开文件对话框
     * @param mode 模式：0,选择文件(单选)。1，选择文件(多选)。2，另存为，3，选择目录
     * @param title 标题。可以为空以显示默认标题（“打开”或“保存”取决于模式）
     * @param filePath 默认路径
     * @param filters 文件类型过滤器。用于限制可选择的文件类型，可以是：
     * （a）有效的小写MIME类型（例如"text/*"或"image/*"）
     * （b）单个文件扩展名（例如".txt"或".png"）
     * （c）使用“|”和“；”分隔的组合描述和文件扩展名的任意组合（例如“Image Types|.png;.gif;.jpg”）
     * @param selectedFilter 默认选择的文件类型过滤器索引
     * @param callback
     * @returns {Promise<unknown>}
     */
    fileDialog:function(mode,title,filePath,filters,selectedFilter){
        let modeStr="FILE_DIALOG_OPEN";
        if(mode==1){
            modeStr="FILE_DIALOG_OPEN_MULTIPLE";
        }else if(mode==2){
            modeStr="FILE_DIALOG_SAVE";
        }else if(mode==3){
            modeStr="FILE_DIALOG_OPEN_FOLDER";
        }

        let params={mode:modeStr,title:title,filePath:filePath,filters:filters,selectedFilter:selectedFilter};
        return execJava("common.fileDialog",params);
    },
    /**
     * 打开文件资源管理器
     * @param filePath 文件路径
     */
    openExplorer:function(filePath){
        execJava("common.openExplorer",{path:filePath});
    }
}




