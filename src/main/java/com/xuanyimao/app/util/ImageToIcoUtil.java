package com.xuanyimao.app.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * 网上copy来的：https://blog.csdn.net/awei0916/article/details/153773464
 * Java图片转ICO格式工具类
 * 支持生成多尺寸ICO文件，包含抗锯齿处理和完整异常机制
 */
public class ImageToIcoUtil {

    //配置ICO包含的尺寸（常用尺寸：16x16,32x32,48x48,64x64,128x128）
    private static int[] sizes = new int[]{16, 32, 48, 64, 128};

//    public static void main(String[] args) {
//        // 1. 配置输入输出路径（根据实际需求修改）
//        String inputImagePath = "C:\\jcef\\JcefTest\\view\\logo.png";  // 输入图片路径（支持PNG/JPG）
//        String outputIcoPath = "C:\\jcef\\JcefTest\\view\\logo.ico"; // 输出ICO文件路径
//
//        // 2. 配置ICO包含的尺寸（常用尺寸：16x16,32x32,48x48,64x64,128x128）
//        int[] icoSizes = new int[]{16, 32, 48, 64, 128};
//
//        try {
//            // 3. 执行转换
//            convertToIco(inputImagePath, outputIcoPath, icoSizes);
//            System.out.println("✅ ICO转换成功！输出路径：" + outputIcoPath);
//        } catch (IOException e) {
//            System.err.println("❌ ICO转换失败：" + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    /**
     * 核心转换方法：将输入图片转为包含多尺寸的ICO文件
     * @param inputPath 输入图片路径
     * @param outputPath 输出ICO路径
     * @throws IOException 读写文件时抛出异常
     */
    public static void convertToIco(String inputPath, String outputPath)
            throws IOException {

        // 步骤1：读取原始图片
        BufferedImage originalImage = ImageIO.read(new File(inputPath));
        if (originalImage == null) {
            throw new IOException("无法读取输入图片，请检查路径或文件格式");
        }

        // 步骤2：生成多尺寸图像（并添加抗锯齿）
        List<BufferedImage> icoImages = new ArrayList<>();
        for (int size : sizes) {
            BufferedImage resizedImage = resizeImage(originalImage, size, size);
            icoImages.add(resizedImage);
        }

        // 步骤3：写入ICO文件（构建完整ICO结构）
        writeIcoFile(icoImages, new File(outputPath));
    }

    /**
     * 辅助方法：调整图片尺寸，启用抗锯齿提升质量
     * @param original 原始图片
     * @param targetWidth 目标宽度
     * @param targetHeight 目标高度
     * @return 调整后的图片
     */
    private static BufferedImage resizeImage(BufferedImage original, int targetWidth, int targetHeight) {
        // 用ARGB格式创建新图片（支持透明通道，ICO常用）
        BufferedImage resized = new BufferedImage(
                targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB
        );

        // 获取绘图对象，设置抗锯齿参数
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR); // 双线性插值
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY); // 高质量渲染
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON); // 启用抗锯齿

        // 绘制缩放后的图片
        g.drawImage(original, 0, 0, targetWidth, targetHeight, null);
        g.dispose(); // 释放资源

        return resized;
    }

    /**
     * 核心方法：按ICO格式写入文件（构建文件头、目录项、图像数据）
     * @param images 多尺寸图像列表
     * @param outputFile 输出文件
     * @throws IOException 写入文件时抛出异常
     */
    private static void writeIcoFile(List<BufferedImage> images, File outputFile)
            throws IOException {
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            // 1. 写入ICO文件头（6字节）
            writeIcoHeader(fos, images.size());

            // 2. 写入目录项（每个图像16字节）+ 缓存图像数据
            long dataOffset = 6 + images.size() * 16; // 数据偏移量：头(6) + 目录项(n*16)
            List<byte[]> imageDataList = new ArrayList<>();

            for (BufferedImage image : images) {
                // 写入当前图像的目录项
                writeDirectoryEntry(fos, image, dataOffset);

                // 缓存当前图像的PNG数据（后续统一写入）
                byte[] imageData = getPngImageData(image);
                imageDataList.add(imageData);

                // 更新下一个图像的数据偏移量
                dataOffset += imageData.length;
            }

            // 3. 写入所有图像的实际数据
            for (byte[] imageData : imageDataList) {
                fos.write(imageData);
            }
        }
    }

    /**
     * 辅助方法：写入ICO文件头
     * @param os 输出流
     * @param numImages 图像数量
     * @throws IOException 写入异常
     */
    private static void writeIcoHeader(OutputStream os, int numImages) throws IOException {
        // ICO文件头要求小端字节序（Little-Endian）
        ByteBuffer buffer = ByteBuffer.allocate(6).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort((short) 0); // 保留位，固定为0
        buffer.putShort((short) 1); // 类型：1=ICO，0=CUR
        buffer.putShort((short) numImages); // 图像数量
        os.write(buffer.array());
    }

    /**
     * 辅助方法：写入单个图像的目录项（16字节）
     * @param os 输出流
     * @param image 图像对象
     * @param dataOffset 该图像数据在ICO文件中的偏移量
     * @throws IOException 写入异常
     */
    private static void writeDirectoryEntry(OutputStream os, BufferedImage image, long dataOffset)
            throws IOException {
        int width = image.getWidth();
        int height = image.getHeight();
        int bpp = image.getColorModel().getPixelSize(); // 每像素位数（ARGB为32）
        byte[] imageData = getPngImageData(image);

        // 目录项共16字节，小端字节序
        ByteBuffer buffer = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN);
        buffer.put((byte) (width == 256 ? 0 : width)); // 宽度：256时用0表示
        buffer.put((byte) (height == 256 ? 0 : height)); // 高度：256时用0表示
        buffer.put((byte) 0); // 颜色数：0表示256色以上
        buffer.put((byte) 0); // 保留位，固定为0
        buffer.putShort((short) 1); // 颜色平面数，固定为1
        buffer.putShort((short) bpp); // 每像素位数
        buffer.putInt(imageData.length); // 图像数据大小（字节）
        buffer.putInt((int) dataOffset); // 图像数据偏移量
        os.write(buffer.array());
    }

    /**
     * 辅助方法：将BufferedImage转为PNG字节数组
     * @param image 图像对象
     * @return PNG格式的字节数组
     * @throws IOException 转换异常
     */
    private static byte[] getPngImageData(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos); // 以PNG格式写入字节流
        return baos.toByteArray();
    }
}
