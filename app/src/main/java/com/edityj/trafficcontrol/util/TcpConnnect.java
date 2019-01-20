package com.edityj.trafficcontrol.util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.edityj.trafficcontrol.config.ConfigOfApp;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TcpConnnect extends Thread {
    private Boolean isOver=false;
    private Context context;
    private Handler handler;
    private Socket s = null;
    private String message;
    private String ip; //远方服务器的IP地址
    private int port;//远方服务器的port

    public TcpConnnect(Context context, Handler handler, String ip, int port, String message) {
        this.context = context;
        this.handler = handler;
        this.message = message;
        this.ip = ip;
        this.port = port;
    }

    public void run() {
        //向远方发起TCP连接
        try {
            s = new Socket(ip, port);
            //第二个参数为True则为自动flush
            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(
                            s.getOutputStream(),"GBK")), true);
            out.println(message);

            //等待接收服务器返回数据
            BufferedInputStream inputStream = new BufferedInputStream(s.getInputStream());
            int i=0;
            while (!isOver) { // 只要不结束，这个线程一直运行
                Log.e("等待次数：", String.valueOf(i));
                i++;
                byte[] bytes = new byte[1024];
                int len;
                StringBuffer stringBuffer = new StringBuffer();
                while (inputStream.available() > 0 && (len = inputStream.read(bytes)) != -1) {
                    stringBuffer.append(new String(bytes, 0, len));
                }
                String fromServer = stringBuffer.toString();
//                Log.e("修剪：", "返回值====="+fromServer);
                if(fromServer.length() >= 3){


                    //Log.e("修剪：", "成功，返回值====="+fromServer);
                    //正则判断
                    Pattern p = Pattern.compile(ConfigOfApp.REGEX);
                    Matcher m = p.matcher(fromServer);
                    if(m.matches()){
                        //System.out.println("验证通过");
                        final String [] arr = fromServer.split("\\s+");
                        if(Integer.valueOf(arr[0])<=100 && Integer.valueOf(arr[0])>=0){
                            InitItemData.getInstance().setBattery(arr[0]);
                            break;
                        }
                    }
                }
                isOver = fromServer.equals(ConfigOfApp.SERVER_RETURN_OK);
                //等待服务器响应
                if(i==100000){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "服务器无响应" , Toast.LENGTH_LONG).show();
                        }
                    });
                    break;
                }
            }
            inputStream.close();
            //判断是否发送成功，并给出提示
            if(isOver){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "发送成功", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (Exception e1) {
            //e1.printStackTrace();
            final String errMsg = e1.getMessage();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "发送失败：" + errMsg, Toast.LENGTH_LONG).show();
                }
            });
        } finally {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

