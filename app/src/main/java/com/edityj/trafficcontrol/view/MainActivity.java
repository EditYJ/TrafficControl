package com.edityj.trafficcontrol.view;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.edityj.trafficcontrol.R;
import com.edityj.trafficcontrol.adapter.MyAdapter;
import com.edityj.trafficcontrol.application.StartApplication;
import com.edityj.trafficcontrol.config.ConfigOfApp;
import com.edityj.trafficcontrol.pojo.ITEMDATA;
import com.edityj.trafficcontrol.util.InitItemData;
import com.edityj.trafficcontrol.util.TcpConnnect;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editors;

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private OutputStream outputStream;
    private RecyclerView.LayoutManager mLayoutManager;
    //    private List<String> mData = new ArrayList<String>();
    private List<ITEMDATA> data;
    private int iconSourceID = -1;
    private Boolean canSet1 = true;
    private Boolean canSet2 = true;
    private Boolean textCanSetSelf = true;
    private Boolean imgCanSetSelf = true;
    private Boolean isDeleteMode = false;
    private LinearLayout screenOne;
    private LinearLayout screenTwo;

    private TextView textScreenOne;
    private TextView textScreenTwo;
    private ImageView imgScreenOne;
    private ImageView imgScreenTwo;
    private Button reset;
    private Button clear;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = InitItemData.getStartInstance().getInitItemDatas();
        sharedPreferences = getSharedPreferences(ConfigOfApp.APP_SAVEFILE_NAME,0);
        editors=sharedPreferences.edit();
        Log.e("m读取数据d:", data.toString());
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.start:
                //Toast.makeText(this,"获取电量", Toast.LENGTH_SHORT).show();
                new TcpConnnect(this, new Handler(), ConfigOfApp.IP, ConfigOfApp.PORT, ConfigOfApp.CHECK_BATTERY).start();
                try {
                    Thread.currentThread().sleep(2000);//阻断2秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String battery = InitItemData.getInstance().getBattery();
                if(battery.equals("获取失败")){
                    item.setTitle(battery);
                }else{
                    item.setTitle(battery+"%");
                }
                break;
            case R.id.add:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.drawable.add_icon);
                builder.setTitle("添加自定义内容");
                //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog, null);
                //    设置我们自己定义的布局文件作为弹出框的Content
                builder.setView(view);

                final EditText dialog_edit = (EditText) view.findViewById(R.id.dialog_edit);

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String a = dialog_edit.getText().toString().trim();
                        ITEMDATA temp = new ITEMDATA();
                        temp.setRemind(a);
                        if (isDeleteMode) {
                            temp.setDelete(R.drawable.delete);
                        }
                        data.add(2, temp);
                        //存储数据
                        String json = JSON.toJSONString(data);
                        editors.putString("data", json);
                        editors.commit();
                        //此处data指针不能变，变化的话列表不能更新
//                        data.clear();
//                        data.addAll(InitItemData.getInstance().getInitItemDatas());
                        myAdapter.notifyDataSetChanged();
                        //Toast.makeText(MainActivity.this, "自定义内容: " + a, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                break;
            case R.id.delete:
                if (!isDeleteMode) {
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getRemind() != null && data.get(i).getDelete() == 0 && !isDeleteMode) {
                            data.get(i).setDelete(R.drawable.delete);

                        }
                    }
                    isDeleteMode = true;
                } else {
                    for (int j = 0; j < data.size(); j++) {
                        data.get(j).setDelete(0);
                    }
                    isDeleteMode = false;
                }
                //存储数据
                String json = JSON.toJSONString(data);
                editors.putString("data", json);
                editors.commit();
                myAdapter.notifyDataSetChanged();
                //Toast.makeText(this,"删除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.change_light:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setIcon(R.drawable.add_icon);
                builder2.setTitle("输入亮度值(1 ~ 10)");
                //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                View view2 = LayoutInflater.from(MainActivity.this).inflate(R.layout.light_dialog, null);
                //    设置我们自己定义的布局文件作为弹出框的Content
                builder2.setView(view2);

                final EditText dialog_edit2 = (EditText) view2.findViewById(R.id.light_dialog_edit);

                builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(Integer.valueOf(dialog_edit2.getText().toString().trim())<1 || Integer.valueOf(dialog_edit2.getText().toString().trim())>10){
                            Toast.makeText(MainActivity.this, "输入有误，请重新输入！", Toast.LENGTH_SHORT).show();
                        }else{
                            new TcpConnnect(MainActivity.this, new Handler(), ConfigOfApp.IP, ConfigOfApp.PORT, ConfigOfApp.CHANGE_LIGHT+dialog_edit2.getText().toString().trim()).start();
                        }
                    }
                });
                builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder2.show();
        }
        return false;
    }

    //TODO EditYJ 获取电量
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Toast.makeText(this, "选项菜单显示之前", Toast.LENGTH_LONG).show();
        // 如果返回false，此方法就把用户点击menu的动作给消费了，onCreateOptionsMenu方法将不会被调用
        return true;
    }


    private void init() {
        screenOne = findViewById(R.id.screen_one);
        screenTwo = findViewById(R.id.screen_two);
        textScreenOne = findViewById(R.id.text_screen_one);
        textScreenTwo = findViewById(R.id.text_screen_two);
        imgScreenOne = findViewById(R.id.img_screen_one);
        imgScreenTwo = findViewById(R.id.img_screen_two);
        reset = findViewById(R.id.b_reset);
        clear = findViewById(R.id.b_clear);
        send = findViewById(R.id.b_send);

        screenOne.setOnClickListener(this);
        screenTwo.setOnClickListener(this);
        reset.setOnClickListener(this);
        clear.setOnClickListener(this);
        send.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycle);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        myAdapter = new MyAdapter(data);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.im_delete:
                        data.get(position).setDelete(0);
                        data.remove(position);

                        String json = JSON.toJSONString(data);
                        editors.putString("data", json);
                        editors.commit();

                        myAdapter.notifyDataSetChanged();
                        break;
                    case R.id.list_item_view:
                        String text = null;
                        int iconSource = 0;
                        ITEMDATA tempData = data.get(position);
                        if (tempData.getDanger() != null && (canSet2 || canSet1)) {
                            //TODO EditYJ 屏幕1无显示，屏幕二有显示的情况还没考虑到
                            text = tempData.getDanger();
                            textScreenOne.setText(text);
                            textScreenOne.setTextColor(getResources().getColor(R.color.colorPrimary));
                            textScreenTwo.setText(null);
                            //imgScreenTwo.setImageResource(Integer.parseInt(null));
                            textScreenTwo.setVisibility(View.GONE);
                            imgScreenTwo.setVisibility(View.GONE);
                            imgScreenOne.setVisibility(View.GONE);
                            textScreenOne.setVisibility(View.VISIBLE);
                            canSet1 = false;
                            canSet2 = false;
                        } else if (tempData.getRemind() != null && (canSet2 || canSet1)) {
                            text = tempData.getRemind();
                            if (canSet1) {
                                textScreenOne.setText(text);
                                textScreenOne.setTextColor(getResources().getColor(R.color.black));
                                textScreenOne.setVisibility(View.VISIBLE);
                                canSet1 = false;
                            } else {
                                textScreenTwo.setText(text);
                                textScreenTwo.setTextColor(getResources().getColor(R.color.black));
                                textScreenTwo.setVisibility(View.VISIBLE);
                                canSet2 = false;
                            }
                        } else if (tempData.getSpeed() != null && textCanSetSelf && (canSet2 || canSet1)) {
                            text = tempData.getSpeed();
                            if (canSet1) {
                                textScreenOne.setText(text);
                                textScreenOne.setTextColor(getResources().getColor(R.color.black));
                                textScreenOne.setVisibility(View.VISIBLE);
                                canSet1 = false;
                                textCanSetSelf = false;
                            } else {
                                textScreenTwo.setText(text);
                                textScreenTwo.setTextColor(getResources().getColor(R.color.black));
                                textScreenTwo.setVisibility(View.VISIBLE);
                                canSet2 = false;
                                textCanSetSelf = false;
                            }
                        } else if (tempData.getIcon() != 0 && imgCanSetSelf && (canSet2 || canSet1)) {
                            iconSourceID = tempData.getIconID();
                            iconSource = tempData.getIcon();
                            //Toast.makeText(MainActivity.this, "点击位置："+iconSourceID, Toast.LENGTH_SHORT).show();
                            if (canSet1) {
                                imgScreenOne.setImageResource(iconSource);
                                imgScreenOne.setVisibility(View.VISIBLE);
                                canSet1 = false;
                                imgCanSetSelf = false;
                            } else {
                                imgScreenTwo.setImageResource(iconSource);
                                imgScreenTwo.setVisibility(View.VISIBLE);
                                canSet2 = false;
                                imgCanSetSelf = false;
                            }
                        }

//                        if(text!=null){
//                            Toast.makeText(MainActivity.this, "点击位置："+text, Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(MainActivity.this, "点击位置："+iconSource, Toast.LENGTH_SHORT).show();
//                        }
                        //Toast.makeText(MainActivity.this, "点击位置："+position, Toast.LENGTH_SHORT).show();
                        break;
                }
//                ImageView isSelect = view.findViewById(R.id.im_is_true);
//                TextView textView = view.findViewById(R.id.text_traffic_code);
//
//
//                TextView textView_copy = findViewById(R.id.screen_one);
//                textView_copy.setText(textView.getText());
//
//
//                if(isSelect.getVisibility()==View.GONE){
//                    isSelect.setVisibility(View.VISIBLE);
//                    //Log.e("设置图片","显示");
//                }else{
//                    isSelect.setVisibility(View.GONE);
//                    //Log.e("设置图片","消失");
//                }
            }
        });


    }

    //配置下方按钮监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_reset:
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setDelete(0);
                }
                data.clear();
                data.addAll(InitItemData.getInstance().getInitItemDatas());
                //存储数据
                String json = JSON.toJSONString(data);
                editors.putString("data", json);
                editors.commit();

                myAdapter.notifyDataSetChanged();
                textScreenTwo.setText(null);
                textScreenOne.setText(null);
                textScreenOne.setVisibility(View.GONE);
                textScreenTwo.setVisibility(View.GONE);
                imgScreenOne.setVisibility(View.GONE);
                imgScreenTwo.setVisibility(View.GONE);
                canSet1 = true;
                canSet2 = true;
                textCanSetSelf = true;
                imgCanSetSelf = true;
                isDeleteMode = false;
                break;
            case R.id.b_clear:
                if (isDeleteMode) {
                    for (int j = 0; j < data.size(); j++) {
                        data.get(j).setDelete(0);
                    }
                }
                myAdapter.notifyDataSetChanged();
                textScreenTwo.setText(null);
                textScreenOne.setText(null);
                textScreenOne.setVisibility(View.GONE);
                textScreenTwo.setVisibility(View.GONE);
                imgScreenOne.setVisibility(View.GONE);
                imgScreenTwo.setVisibility(View.GONE);
                canSet1 = true;
                canSet2 = true;
                textCanSetSelf = true;
                imgCanSetSelf = true;
                isDeleteMode = false;
                break;
            case R.id.b_send:
                String sendText = null;
                //警告发送
                if (textScreenOne.getVisibility() == View.VISIBLE && Arrays.asList(StartApplication.warning).contains(textScreenOne.getText().toString().trim())) {
                    sendText = ConfigOfApp.SEND_SCREEN_HEAD + ConfigOfApp.SEND_WARNING_MSG_HEAD + textScreenOne.getText().toString().trim() + ConfigOfApp.SEND_WARNING_MSG_END;
                    //提醒文字组合发送
                } else if (textScreenOne.getVisibility() == View.VISIBLE && textScreenTwo.getVisibility() == View.VISIBLE) {
                    sendText = ConfigOfApp.SEND_SCREEN_HEAD + textScreenOne.getText().toString().trim() + ConfigOfApp.SEND_SCREEN_POINT + textScreenTwo.getText().toString().trim();
                    //文字图片组合发送
                }else if(textScreenOne.getVisibility() == View.VISIBLE && imgScreenTwo.getVisibility() == View.VISIBLE || imgScreenOne.getVisibility() == View.VISIBLE && textScreenTwo.getVisibility() == View.VISIBLE){
                    if(textScreenOne.getVisibility() == View.VISIBLE){
                        sendText=ConfigOfApp.SEND_SCREEN_HEAD + textScreenOne.getText().toString().trim() + ConfigOfApp.SEND_SCREEN_POINT + ConfigOfApp.SEND_IMG_HEAD+String.valueOf(iconSourceID);
                    }
                    if(textScreenOne.getVisibility() == View.GONE){
                        sendText=ConfigOfApp.SEND_SCREEN_HEAD+ ConfigOfApp.SEND_IMG_HEAD+String.valueOf(iconSourceID)+ ConfigOfApp.SEND_SCREEN_POINT+ textScreenTwo.getText().toString().trim();
                    }
                    //但文字发送
                }else if(textScreenOne.getVisibility() == View.VISIBLE && textScreenTwo.getVisibility() == View.GONE || textScreenOne.getVisibility() == View.GONE && textScreenTwo.getVisibility() == View.VISIBLE){
                    if(textScreenOne.getVisibility() == View.VISIBLE){
                        sendText=ConfigOfApp.SEND_SCREEN_HEAD + textScreenOne.getText().toString().trim();
                    }else{
                        sendText=ConfigOfApp.SEND_SCREEN_HEAD + textScreenTwo.getText().toString().trim();
                    }
                    //单图片发送
                }else if(imgScreenOne.getVisibility() == View.VISIBLE && imgScreenTwo.getVisibility() == View.GONE || imgScreenOne.getVisibility() == View.GONE && imgScreenTwo.getVisibility() == View.VISIBLE){
                    sendText=ConfigOfApp.SEND_SCREEN_HEAD + ConfigOfApp.SEND_IMG_HEAD + String.valueOf(iconSourceID);
                }

                new TcpConnnect(this, new Handler(), ConfigOfApp.IP, ConfigOfApp.PORT, sendText).start();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Socket socket = new Socket(ConfigOfApp.IP,ConfigOfApp.PORT);
//                            outputStream = new BufferedOutputStream(socket.getOutputStream());
//                            String toServer = "你好";
//                            outputStream.write(toServer.getBytes());
//                            outputStream.flush();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
                break;
            case R.id.screen_one:
                if (!canSet1) {
                    if (imgScreenOne.getVisibility() == View.VISIBLE) {
                        imgCanSetSelf = true;
                    }
                    if (Arrays.asList(StartApplication.speed).contains(textScreenOne.getText().toString().trim())) {
                        textCanSetSelf = true;
                    }
                    if (Arrays.asList(StartApplication.warning).contains(textScreenOne.getText().toString().trim())) {
                        canSet2 = true;
                    }
                    textScreenOne.setText(null);
                    textScreenOne.setVisibility(View.GONE);
                    //textScreenTwo.setVisibility(View.GONE);
                    imgScreenOne.setVisibility(View.GONE);
                    //imgScreenTwo.setVisibility(View.GONE);
                    canSet1 = true;
                }
                break;
            case R.id.screen_two:
                if (!canSet2) {
                    if (imgScreenTwo.getVisibility() == View.VISIBLE) {
                        imgCanSetSelf = true;
                    }
                    if (Arrays.asList(StartApplication.speed).contains(textScreenTwo.getText().toString().trim())) {
                        //Toast.makeText(this, "设置textCanSetSelf为true",Toast.LENGTH_SHORT).show();
                        textCanSetSelf = true;
                    }
                    textScreenTwo.setText(null);
                    textScreenTwo.setVisibility(View.GONE);
                    //textScreenTwo.setVisibility(View.GONE);
                    imgScreenTwo.setVisibility(View.GONE);
                    //imgScreenTwo.setVisibility(View.GONE);
                    canSet2 = true;
                }
                break;
        }
    }
}
