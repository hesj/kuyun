package com.he.kuyun.util;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OCRUtil {

    public static String ocr(String path) throws TesseractException, IOException {
        File pic = new File (path);//图片位置
        //图片二值化，转化为黑白图
        BufferedImage grayImage = ImageHelper.convertImageToBinary(ImageIO.read(pic));
//        ImageIO.write(grayImage, "jpeg", new MemoryCacheImageOutputStream(pic));
        ImageIO.write(grayImage, "jpeg", pic);

        ITesseract instance = new Tesseract();//新建实例
        instance.setLanguage("eng");//选择字库文件（只需要文件名，不需要后缀名）
        String result = instance.doOCR(pic);//开始识别
        //天坑注意！result自带两个空格！
        return result;
    }

    public static String ocr(BufferedImage bufferedImage) throws TesseractException, IOException {
        ITesseract instance = new Tesseract();//新建实例
//        instance.setDatapath("/Users/hesj/IdeaProjects/tessdata");//设置训练库的位置
        instance.setLanguage("eng");//选择字库文件（只需要文件名，不需要后缀名）
        String result = instance.doOCR(bufferedImage);//开始识别
        //天坑注意！result自带两个空格！
        return result;
    }

//    public static void main(String[] args) throws TesseractException {
//        OCRUtil.ocr();
//    }
}
