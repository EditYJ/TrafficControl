package com.edityj.trafficcontrol.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.edityj.trafficcontrol.R;
import com.edityj.trafficcontrol.config.ConfigOfApp;
import com.edityj.trafficcontrol.pojo.ITEMDATA;
import com.edityj.trafficcontrol.util.InitItemData;

import java.util.List;


public class StartApplication extends Application {
    //判断是否是第一次启动相关变量
    private static final String FIRST = "first";
    private static final String FIRST_FLAG = "flag";
    private boolean isFirst ;

    private List<ITEMDATA> itemdata;
    private SharedPreferences save;
    private SharedPreferences.Editor editors;

    public static TypedArray icon;
    public static String[] speed;
    public static String[] warning;
    public static String[] remind;
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sp = getSharedPreferences(FIRST,0);
        save = getSharedPreferences(ConfigOfApp.APP_SAVEFILE_NAME,0);
        editors=save.edit();
        isFirst = sp.getBoolean(FIRST_FLAG,true);
        init();
    }
    private void init(){
        int i;
        icon = this.getResources().obtainTypedArray(R.array.icon);
        speed = this.getResources().getStringArray(R.array.speed);
        warning = this.getResources().getStringArray(R.array.warning);
        remind = this.getResources().getStringArray(R.array.remind);
        for(i=0;i<warning.length;i++){
            ITEMDATA itemdata=new ITEMDATA();
            itemdata.setDanger(warning[i]);
            InitItemData.getInstance().getInitItemDatas().add(itemdata);
            if(isFirst)
                InitItemData.getStartInstance().getInitItemDatas().add(itemdata);
            Log.e("初始化数据：", itemdata.getDanger());
        }
        for(i=0;i<remind.length;i++){
            ITEMDATA itemdata=new ITEMDATA();
            itemdata.setRemind(remind[i]);
            InitItemData.getInstance().getInitItemDatas().add(itemdata);
            if(isFirst)
                InitItemData.getStartInstance().getInitItemDatas().add(itemdata);
            Log.e("初始化数据：", itemdata.getRemind());
        }
        for(i=0;i<speed.length;i++){
            ITEMDATA itemdata=new ITEMDATA();
            itemdata.setSpeed(speed[i]);
            InitItemData.getInstance().getInitItemDatas().add(itemdata);
            if(isFirst)
                InitItemData.getStartInstance().getInitItemDatas().add(itemdata);
            Log.e("初始化数据：", itemdata.getSpeed());
        }
        for(i=0;i<icon.length();i++){
            ITEMDATA itemdata=new ITEMDATA();
            Log.e("执行次数：", String.valueOf(i));
            Log.e("资源id：", String.valueOf(icon.getResourceId(i,0)));
            itemdata.setIcon(icon.getResourceId(i,0));
            itemdata.setIconID(i+1);
            InitItemData.getInstance().getInitItemDatas().add(itemdata);
            if(isFirst)
                InitItemData.getStartInstance().getInitItemDatas().add(itemdata);
        }
        //第一次打开App存入原始数据
        Log.e("第判断", String.valueOf(isFirst));
        if(isFirst){
            itemdata=InitItemData.getInstance().getInitItemDatas();
            String json = JSON.toJSONString(itemdata);
            editors.putString("data", json);
            boolean isSave=editors.commit();
            Log.e("第一次打开初始化数据", isSave+json);
        }else{
            Log.e("第二次打开初始化数据", "111111");
            String getdata = save.getString("data", "");
            Log.e("第二次打开初始化数据", "数据"+getdata);
            if(!getdata.equals("")){
//                Log.e("第二次设置数据", "success");
                itemdata = JSON.parseArray(getdata, ITEMDATA.class);
                InitItemData.getStartInstance().setInitItemDatas(itemdata);
                Log.e("第二次设置数据", "success");
            }
        }

        SharedPreferences settings = getSharedPreferences(FIRST,0);
        SharedPreferences.Editor editor = settings.edit();
        if(isFirst){
            editor.putBoolean(FIRST_FLAG,false);
        }
        editor.commit();

    }

}
