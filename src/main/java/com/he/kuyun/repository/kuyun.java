package com.he.kuyun.repository;

import com.he.kuyun.util.OCRUtil;
import net.sourceforge.tess4j.TesseractException;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class kuyun {

    /*以下是模拟登陆程序*/
    /*输入你的用户名及密码 ，这里输入*/
    private static String userName = "nieli";
    private static String password = "hourushi3.0";
    private static String redirectURL = "http://www.kooyuns.com/login.zul";
    // Don't change the following URL
    private static String renRenLoginURL = "http://www.renren.com/PLogin.do";
    // The HttpClient is used in one session
    CloseableHttpResponse response = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();

    /*输入抓包的参数，即传递的参数*/
    private boolean login() {



//        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//        formparams.add(new BasicNameValuePair("loginid", userName));
//        formparams.add(new BasicNameValuePair("password", password));
//        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
//        HttpPost httppost = new HttpPost(redirectURL);
//        httppost.setEntity(entity);

        HttpGet httpGet = new HttpGet(redirectURL);


        //创建一个返回信息的对象
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            System.out.println("-------------------");
            //打印返回状态
            System.out.println(response.getStatusLine());

            //实体
            HttpEntity httpEntity = response.getEntity();

            if (httpEntity != null) {
                InputStream inputStream = httpEntity.getContent();
                try {
                    inputStream.read();
                } catch (IOException ex) {
                    throw ex;
                } finally {
                    inputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }


    void getVerifyingCode(HttpClient client) {
        HttpGet getVerifyCode = new HttpGet("http://www.kooyuns.com/imgcode");//验证码get
        FileOutputStream fileOutputStream = null;
        HttpResponse response;
        try {
            response = client.execute(getVerifyCode);//获取验证码
//            /*验证码写入文件,当前工程的根目录,保存为verifyCode.jped*/
//            fileOutputStream = new FileOutputStream(new File("verifyCode.jpeg"));
//            response.getEntity().writeTo(fileOutputStream);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            response.getEntity().writeTo(bos);
            ByteArrayInputStream swapInputStream = new ByteArrayInputStream(bos.toByteArray());
            BufferedImage bufferedImage = ImageIO.read(swapInputStream);
            String result = OCRUtil.ocr(bufferedImage);
            System.out.println("aaaaaaaaaaaaaaa:" +  result );

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TesseractException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    private String getRedirectLocation() {
//        /*获取响应的头 url*/
//        Header locationHeader = response.getFirstHeader("Location");
//        if (locationHeader == null) {
//            return null;
//        }
//        return locationHeader.getValue();
//    }

//    /*获取html文本*/
//    private String getText(String redirectLocation) {
//        HttpGet httpget = new HttpGet(redirectLocation);
//        // Create a response handler
//        ResponseHandler<String> responseHandler = new BasicResponseHandler();
//        String responseBody = "";
//        try {
//            responseBody = httpclient.execute(httpget, responseHandler);
//        } catch (Exception e) {
//            e.printStackTrace();
//            responseBody = null;
//        } finally {
//            httpget.abort();
//            httpclient.getConnectionManager().shutdown();
//        }
//        return responseBody;
//    }

//    public void printText() {
//        /*如果注册成功了，输入相应后的html*/
//        if (login()) {
//            String redirectLocation = getRedirectLocation();
//            if (redirectLocation != null) {
//                System.out.println(getText(redirectLocation));
//            }
//        }
//    }

    /*main方法*/
    public static void main(String[] args) {
        kuyun renRen = new kuyun();
        renRen.getVerifyingCode(renRen.httpClient);
    }
}

