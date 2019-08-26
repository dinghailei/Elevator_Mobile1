package com.example.a57617.elevator_mobile.utils;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.a57617.elevator_mobile.bean.News;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class NewsApiUtil {
    private static final int GET_NEWS = 11;
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream instream = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = JSONObject.parseObject(jsonText);
            return json;
        } finally {
            instream.close();
        }
    }

    public void getNews(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://api01.idataapi.cn:8000/article/idataapi?kw=IT&sourceRegion=中国&KwPosition=2&kwMode=or&apikey=ZROGwENs4WnZRVVVuumFCDxzEI3xtT95M7dmDXR8ugh4zzptphs055FKUNrHb6NO";
                JSONObject json = null;
                try {
                    json = readJsonFromUrl(url);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                }
                Object retcode = json.get("retcode");
                ArrayList<News> list = new ArrayList<>();

                if (retcode != null) {
                    if (retcode.equals("000000")) { //返回状态码"000000"表示正常
                        //接口返回正常 打印数据内容
                        JSONArray jsonArray = json.getJSONArray("data");
                        for (int i = 0; i < jsonArray.size(); ++i) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String title = obj.getString("title");
                            String sourceRegion = obj.getString("sourceRegion");
                            News item = new News();
                            item.setContent(title);
                            list.add(item);
                            System.out.println(title);
                        }
                    } else {  // 其它返回状态码,表示无法继续
                        System.out.println("error:"+json.getString("message")); //打印状态信息
                    }
                }
                Message msg = new Message();
                msg.what = GET_NEWS;
                msg.obj = list;
                handler.sendMessage(msg);

            }
        }).start();

    }
}




