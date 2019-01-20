package com.edityj.trafficcontrol.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpConnnect extends Thread {
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
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "发送成功", Toast.LENGTH_LONG).show();
                }
            });
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

