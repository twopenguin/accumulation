package com.zcd.accumulation.thirdparty.dingding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DingDingSendMessageToRobot {

    private static final Logger logger = LoggerFactory.getLogger(DingDingSendMessageToRobot.class);

    /**
     * 通过钉钉群的指定的机器人在 钉钉群里发消息
     * @param url 顶顶群机器人的url：比如 https://oapi.dingtalk.com/robot/send?access_token=dc94e179000f310514d6ef8e255ab74c33bfa0382526075a64842fad6c6b1997
     * @param dingdingMessage 待发送的钉钉消息
     * @return 发送消息后的返回消息
     */
    private String sendPostJSON(String url, String dingdingMessage) {
        BufferedReader in = null;
        OutputStream out = null;
        HttpURLConnection conn = null;
        String result="";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = conn.getOutputStream();
            // 发送请求参数
            out.write(dingdingMessage.getBytes());
            // flush输出流的缓冲
            out.flush();
            if(200 == conn.getResponseCode()) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            }
        } catch(Exception e) {
            logger.error(e.getMessage());
        } finally {// 使用finally块来关闭输出流、输入流
            try {
                if(out != null) {
                    out.close();
                }
                if(in != null) {
                    in.close();
                }
            } catch(IOException ex) {
                logger.error(ex.getMessage());
            }
        }
        return result;
    }

}
