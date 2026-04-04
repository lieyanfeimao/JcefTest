package com.xuanyimao.app.util;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * 文件处理类
 * @author liuming
 *
 */
public class FileUtil {

	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 删除目录
	 * @param folderPath
	 * @throws IOException
	 */
	public static void deleteFolder(String folderPath) throws IOException {
		File file = new File(folderPath);
		if (!file.exists() || file.isFile()) {
			return;
		}
		FileUtils.deleteDirectory(file);
	}

	/**
	 * 复制文件
	 * @param sourcePath 原文件
	 * @param destinationPath 目标文件
	 * @throws IOException
	 */
	public static void copyFile(String sourcePath, String destinationPath) throws Exception {
		Path srcPath = Paths.get(sourcePath);
		Path destPath = Paths.get(destinationPath);

		Files.createDirectories(destPath.getParent());

		Files.copy(srcPath,destPath, REPLACE_EXISTING);
	}

	/**
	 * 复制目录
	 * @param sourceFolder 源目录
	 * @param destinationFolder 目标目录
	 * @throws Exception
	 */
	public static void copyFolder(String sourceFolder, String destinationFolder) throws Exception {

		Path srcFolder = Paths.get(sourceFolder);
		Path destFolder = Paths.get(destinationFolder);

		Files.createDirectories(destFolder);

		// 遍历源目录中的所有文件和子目录
		Files.walk(srcFolder)
				.forEach(path -> {
					// 获取相对于源目录的路径
					Path targetPath = destFolder.resolve(srcFolder.relativize(path));
//                    System.out.println(targetPath);

					try {
						Files.copy(path, targetPath, REPLACE_EXISTING);
					} catch (IOException e) {
						log.error("复制失败：{} -> {}",path,targetPath,e);
					}
				});
	}

	/**
	 * 根据文件路径创建目录
	 * @param filePath 文件路径
	 */
	public static String createFolderByPath(String filePath){
		//如果目标文件目录不存在，需要创建，否则会报错
		int i1=filePath.lastIndexOf("/");
		int i2=filePath.lastIndexOf("\\");
		if(i2>i1) i1=i2;
		if(i1==-1) {
			log.error("创建目录失败:{}",filePath);
			return "";
		}
		File file=new File(filePath.substring(0,i1));
		if(!file.exists() || !file.isDirectory()){
			file.mkdirs();
		}
		return file.getPath();
	}

	/**
	 * 使用密码压缩文件
	 * @param filePath 原文件路径
	 * @param zipPath  压缩后的文件路径
	 * @param pwd      密码
	 */
	public static void zipByPwd(String filePath,String zipPath,String pwd){
		try(ZipFile zipFile = new ZipFile(zipPath,pwd.toCharArray());) {
			// 创建ZIP文件参数，并设置压缩方法、压缩级别和加密方法
			ZipParameters zipParameters = new ZipParameters();
			zipParameters.setEncryptFiles(true);
			zipParameters.setEncryptionMethod(EncryptionMethod.AES);
			zipParameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);

			zipFile.addFile(filePath,zipParameters);

		} catch (Exception e) {
			log.error("文件加密压缩失败",e);
		}
	}

	/**
	 * 解压文件
	 * @param filePath zip文件路径
	 * @param unzipPath  解压的文件夹目录
	 * @param pwd  密码
	 */
	public static boolean unzipByPwd(String filePath,String unzipPath,String pwd){
		try(ZipFile zipFile = new ZipFile(filePath,pwd.toCharArray());){
			zipFile.extractAll(unzipPath);
		} catch (Exception e) {
			log.error("文件解压缩失败",e);
			return false;
		}
		return true;
	}

	/**
	 * 解压文件
	 * @param filePath 文件路径
	 * @param unzipPath  文件解压路径
	 * @return
	 */
	public static boolean unzip(String filePath,String unzipPath){
		try(ZipFile zipFile = new ZipFile(filePath);){
			zipFile.extractAll(unzipPath);
		} catch (Exception e) {
			log.error("文件解压缩失败",e);
			return false;
		}
		return true;
	}

	/**
	 * 压缩单个文件/目录，目录优先
	 * @param filePath 文件路径
	 * @param zipPath  zip文件路径
	 * @return
	 */
	public static boolean zip(String filePath, String zipPath) {
		try(ZipFile zipFile = new ZipFile(zipPath);) {
			// 创建ZIP文件参数，并设置压缩方法、压缩级别和加密方法
			ZipParameters zipParameters = new ZipParameters();
			zipParameters.setCompressionMethod(CompressionMethod.DEFLATE); // 设置压缩方式
			zipParameters.setCompressionLevel(CompressionLevel.NORMAL); // 设置压缩级别

			File file=new File(filePath);
			if(!file.exists()){
				log.info("文件/目录不存在:{}",filePath);
				return false;
			}
			if(file.isDirectory()){
				zipFile.addFolder(file, zipParameters);
			}else {
				zipFile.addFile(file, zipParameters);
			}
		} catch (Exception e) {
			log.error("文件压缩失败",e);
			return false;
		}
		return true;
	}

	/**
	 * 压缩多个文件和目录
	 * @param filePathList  要压缩的列表，可包含 文件，文件目录
	 * @param zipPath       压缩的文件地址
	 * @return
	 */
	public static boolean zip(List<String> filePathList, String zipPath){
		try(ZipFile zipFile = new ZipFile(zipPath);) {
			// 创建ZIP文件参数，并设置压缩方法、压缩级别和加密方法
			ZipParameters zipParameters = new ZipParameters();
			zipParameters.setCompressionMethod(CompressionMethod.DEFLATE); // 设置压缩方式
			zipParameters.setCompressionLevel(CompressionLevel.NORMAL); // 设置压缩级别

			for (String filePath : filePathList) {
				File file=new File(filePath);
				if(!file.exists()){
					log.error("{}不存在,跳过压缩",filePath);
					continue;
				}
				if(file.isDirectory()){
					zipFile.addFolder(file, zipParameters);
				}else {
					zipFile.addFile(file, zipParameters);
				}
			}
		} catch (Exception e) {
			log.error("文件压缩失败",e);
			return false;
		}
		return true;
	}

	/**
	 * 压缩文件
	 * @param fileList 文件对象列表
	 * @param zipPath  zip文件路径
	 * @return
	 */
	public static boolean zip(File[] fileList, String zipPath) {
		try(ZipFile zipFile = new ZipFile(zipPath);) {
			// 创建ZIP文件参数，并设置压缩方法、压缩级别和加密方法
			ZipParameters zipParameters = new ZipParameters();
			zipParameters.setCompressionMethod(CompressionMethod.DEFLATE); // 设置压缩方式
			zipParameters.setCompressionLevel(CompressionLevel.NORMAL); // 设置压缩级别

			for (File file : fileList) {
				if(!file.exists()){
					log.error("{}不存在,跳过压缩",file);
					continue;
				}
				if(file.isDirectory()){
					zipFile.addFolder(file, zipParameters);
				}else {
					zipFile.addFile(file, zipParameters);
				}
			}
		} catch (Exception e) {
			log.error("文件压缩失败",e);
			throw new RuntimeException(e);
		}
		return true;
	}
	
	/**
     * 保存文件 - 使用utf-8编码
     * @param path 文件路径
     * @param content 文件内容
     * @return
     */
    public static boolean saveFile(String path,String content){
        return saveFile(path,content,"utf-8");
    }

	/**
	 * 保存文件 - 使用utf-8编码
	 * @param file    文件对象
	 * @param content 文件内容
	 * @return
	 */
	public static boolean saveFile(File file,String content){
		return saveFile(file,content,"utf-8");
	}

    /**
     * 保存文件
     * @param path 文件路径
     * @param content 文件内容
     * @param charset 字符编码
     * @return
     */
    public static boolean saveFile(String path,String content,String charset){
        return saveFile(new File(path),content,charset);
    }

	/**
	 * 保存文件
	 * @param file 文件对象
	 * @param content 内容
	 * @param charset 编码
	 * @return
	 */
	public static boolean saveFile(File file,String content,String charset){
		//创建文件目录
		createFolderByPath(file.getAbsolutePath());
		try(
				FileOutputStream fos=new FileOutputStream(file)
		){
			IOUtils.write(content, fos, charset);
			return true;
		} catch (Exception e) {
			log.error("保存文件异常",e);
		}
		return false;
	}

    /**
     * 读取文件,文件不存在时创建新文件 - 使用utf-8编码
     * @param path 文件路径
     * @return
     */
    public static String readFile(String path){
        return readFile(path,true,"utf-8");
    }

	/**
	 * 读取文件,文件不存在时创建新文件 - 使用utf-8编码
	 * @param file 文件对象
	 * @return
	 */
	public static String readFile(File file){
		return readFile(file,true,"utf-8");
	}

    /**
     * 读取文件 - 使用utf-8编码
     * @param path 文件路径
     * @param create 文件不存在时是否创建空文件。true:创建,false:不创建
     * @return
     */
    public static String readFile(String path,boolean create){
        return readFile(path,create,"utf-8");
    }

	/**
	 * 读取文件 - 使用utf-8编码
	 * @param file 文件对象
	 * @param create 文件不存在时是否创建空文件。true:创建,false:不创建
	 * @return
	 */
	public static String readFile(File file,boolean create){
		return readFile(file,create,"utf-8");
	}

	/**
	 * 读取文件
	 * @param path 文件路径
	 * @param create 文件不存在时是否创建空文件。true:创建,false:不创建
	 * @param charset 文件编码
	 * @return
	 */
	public static String readFile(String path,boolean create,String charset){
		return readFile(new File(path),create,charset);
	}
    /**
     * 读取文件
     * @param file 文件对象
     * @param create 文件不存在时是否创建空文件。true:创建,false:不创建
     * @param charset 文件编码
     * @return
     */
    public static String readFile(File file,boolean create,String charset){
		try{
			if(!file.exists()){
				if(create) {
					createFolderByPath(file.getAbsolutePath());
					file.createNewFile();
				}
				return "";
			}
		}catch(Exception e){
			log.error("读取文件异常",e);
			return "";
		}

        try(FileInputStream fis = new FileInputStream(file);){
            String data=IOUtils.toString(fis,charset);
            IOUtils.closeQuietly(fis);
            return data;
        }catch(Exception e){
			log.error("读取文件异常",e);
        }
        return "";
    }
}
