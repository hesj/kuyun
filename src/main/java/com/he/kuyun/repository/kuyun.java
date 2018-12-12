package com.he.kuyun.repository;

import com.alibaba.fastjson.JSON;
import com.he.kuyun.util.OCRUtil;
import net.sourceforge.tess4j.TesseractException;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class kuyun {

    /*以下是模拟登陆程序*/
    /*输入你的用户名及密码 ，这里输入*/
    private static String userName = "nieli";
    private static String password = "hourushi3.0";
    private static String redirectURL = "http://www.kooyuns.com/login.zul";
    // Don't change the following URL
    private static String renRenLoginURL = "http://www.renren.com/PLogin.do";
    // The HttpClient is used in one session
//    CloseableHttpResponse response = null;
    CloseableHttpClient httpClient = HttpClients.createDefault();

//    private List<Cookie> cookieList;
    private CookieStore cookieStore = new BasicCookieStore();
    CloseableHttpClient loginHttpClient = null;


    public String getYanZhengMa() throws IOException {
        String yanzhengmaURL = "http://www.kooyuns.com/imgcode";
        HttpGet getVerifyCode = new HttpGet(yanzhengmaURL);//验证码get
        FileOutputStream fileOutputStream = null;
        HttpResponse response;
        response = httpClient.execute(getVerifyCode);//获取验证码
        /*验证码写入文件,当前工程的根目录,保存为verifyCode.jped*/
        fileOutputStream = new FileOutputStream(new File("verifyCode.jpeg"));
        response.getEntity().writeTo(fileOutputStream);
        Scanner sc = new Scanner(System.in);
        System.out.println("ScannerTest, Please Enter code:");
        String name = sc.nextLine();//读取字符串型输入
        return name;
    }

    public void login(String yanzhengma) throws IOException {
        HttpPost httpPost = new HttpPost("http://www.kooyuns.com/zkau");
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("loginid", userName);
        hashMap.put("password", password);
        hashMap.put("yzm", yanzhengma);
        hashMap.put("isSave", "false");
        hashMap.put("isAgree", "true");
        Map<String, Object> hashMap1 = new HashMap<>();
        hashMap1.put("",hashMap);
        String jsonData = JSON.toJSONString(hashMap1);
        formparams.add(new BasicNameValuePair("data_0", jsonData));
        formparams.add(new BasicNameValuePair("dtid", "z_8xq"));
        formparams.add(new BasicNameValuePair("cmd_0", "onSubmitForm"));
        formparams.add(new BasicNameValuePair("uuid_0", "sPGP0"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(entity);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36 Vivaldi/1.1.453.52");


//        CookieStore cookieStore = new BasicCookieStore();
        loginHttpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse response = loginHttpClient.execute(httpPost);

        System.out.println(JSON.toJSONString(response));
//        cookieList = cookieStore.getCookies();

//        for(Cookie cookie:cookieStore.getCookies()){
//            cookieStore.addCookie(cookie);
//        }

    }

    public String getHtml(String url) throws IOException {
//        CookieStore cookieStore = new BasicCookieStore();
//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        CloseableHttpResponse response = loginHttpClient.execute(new HttpPost(url));

        String html = EntityUtils.toString(response.getEntity(), "utf-8");
        return html;
    }

    public void getShangpin(String huohao) throws IOException {

        System.out.println(JSON.toJSONString(cookieStore));

//        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("value", huohao);
        hashMap.put("start", 6);
        String jsonData = JSON.toJSONString(hashMap);
        formparams.add(new BasicNameValuePair("dtid", "z_1oo"));
        formparams.add(new BasicNameValuePair("cmd_0", "onChange"));
        formparams.add(new BasicNameValuePair("uuid_0", "e89P_8"));
        formparams.add(new BasicNameValuePair("data_0", jsonData));
        formparams.add(new BasicNameValuePair("cmd_1", "onClick"));
        formparams.add(new BasicNameValuePair("uuid_1", "aCDPk2"));


        Map<String, Object> hashMap2 = new HashMap<>();
        hashMap2.put("pageX", 444);
        hashMap2.put("pageY", 164);
        hashMap2.put("which", 1);
        hashMap2.put("x", 56);
        hashMap2.put("y", 17);
        String data_1 = JSON.toJSONString(hashMap2);
        formparams.add(new BasicNameValuePair("data_1", data_1));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        HttpPost httpPost = new HttpPost("http://www.kooyuns.com/zkau");
        httpPost.setEntity(entity);
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36 Vivaldi/1.1.453.52");
        CloseableHttpResponse response = loginHttpClient.execute(httpPost);


        String html = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(html);
        System.out.println();
        System.out.println(JSON.toJSONString(response));

    }
    /*输入抓包的参数，即传递的参数*/
    private boolean login2() {



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

    String getVerifyingCode1(HttpClient client) {
        HttpGet getVerifyCode = new HttpGet("http://www.kooyuns.com/imgcode");//验证码get
        FileOutputStream fileOutputStream = null;
        HttpResponse response;
        try {
            response = client.execute(getVerifyCode);//获取验证码
            /*验证码写入文件,当前工程的根目录,保存为verifyCode.jped*/
            fileOutputStream = new FileOutputStream(new File("verifyCode.jpeg"));
            response.getEntity().writeTo(fileOutputStream);
            Scanner sc = new Scanner(System.in);
            System.out.println("ScannerTest, Please Enter code:");
            String name = sc.nextLine();//读取字符串型输入
            Header[] headers = response.getHeaders("Set-Cookie");
//            Header[] headers = response.getAllHeaders();

            HttpPost httpPost = new HttpPost("http://www.kooyuns.com/zkau");
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//            formparams.add(new BasicNameValuePair("loginid", userName));
//            formparams.add(new BasicNameValuePair("password", password));
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("loginid", userName);
            hashMap.put("password", password);
            hashMap.put("yzm", name);
            hashMap.put("isSave", "false");
            hashMap.put("isAgree", "true");
            Map<String, Object> hashMap1 = new HashMap<>();
            hashMap1.put("",hashMap);
            String jsonData = JSON.toJSONString(hashMap1);
            formparams.add(new BasicNameValuePair("data_0", jsonData));
            formparams.add(new BasicNameValuePair("dtid", "z_8xq"));
            formparams.add(new BasicNameValuePair("cmd_0", "onSubmitForm"));
            formparams.add(new BasicNameValuePair("uuid_0", "sPGP0"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            httpPost.setEntity(entity);
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36 Vivaldi/1.1.453.52");


            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            httpPost.setHeaders(headers);
            response = httpClient.execute(httpPost);

            System.out.println(JSON.toJSONString(response));
            List<Cookie> cookies = cookieStore.getCookies();


            CookieStore cookieStore2 = new BasicCookieStore();
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore2)
                    .build();
            for(Cookie cookie:cookies){
                cookieStore2.addCookie(cookie);
            }
            response = httpClient.execute(new HttpPost("http://www.kooyuns.com/main.zul"));

            String html = EntityUtils.toString(response.getEntity(), "utf-8");

            System.out.println("html:\n"+html);
            return name;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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
    public static void main(String[] args) throws IOException {
        kuyun kuyun = new kuyun();
        String yanZhengMa = kuyun.getYanZhengMa();
        kuyun.login(yanZhengMa);
        String shouyeHtml = kuyun.getHtml("http://www.kooyuns.com/main.zul");
        System.out.println(shouyeHtml);

        kuyun.getShangpin("BB3659");
    }
}

