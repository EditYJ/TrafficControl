package com.edityj.trafficcontrol.application;

import android.app.Application;
import android.content.res.TypedArray;
import android.util.Log;

import com.edityj.trafficcontrol.R;
import com.edityj.trafficcontrol.pojo.ITEMDATA;
import com.edityj.trafficcontrol.util.InitItemData;


public class StartApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }
    private void init(){
        int i;
        TypedArray icon = this.getResources().obtainTypedArray(R.array.icon);
        String[] speed = this.getResources().getStringArray(R.array.speed);
        String[] warning = this.getResources().getStringArray(R.array.warning);
        String[] remind = this.getResources().getStringArray(R.array.remind);
        for(i=0;i<warning.length;i++){
            ITEMDATA itemdata=new ITEMDATA();
            itemdata.setDanger(warning[i]);
            InitItemData.getInstance().getInitItemDatas().add(itemdata);
            InitItemData.getStartInstance().getInitItemDatas().add(itemdata);
            Log.e("初始化数据：", itemdata.getDanger());
        }
        for(i=0;i<remind.length;i++){
            ITEMDATA itemdata=new ITEMDATA();
            itemdata.setRemind(remind[i]);
            InitItemData.getInstance().getInitItemDatas().add(itemdata);
            InitItemData.getStartInstance().getInitItemDatas().add(itemdata);
            Log.e("初始化数据：", itemdata.getRemind());
        }
        for(i=0;i<speed.length;i++){
            ITEMDATA itemdata=new ITEMDATA();
            itemdata.setSpeed(speed[i]);
            InitItemData.getInstance().getInitItemDatas().add(itemdata);
            InitItemData.getStartInstance().getInitItemDatas().add(itemdata);
            Log.e("初始化数据：", itemdata.getSpeed());
        }
        for(i=0;i<icon.length();i++){
            ITEMDATA itemdata=new ITEMDATA();
            Log.e("执行次数：", String.valueOf(i));
            Log.e("资源id：", String.valueOf(icon.getResourceId(i,0)));
            itemdata.setIcon(icon.getResourceId(i,0));
            InitItemData.getInstance().getInitItemDatas().add(itemdata);
            InitItemData.getStartInstance().getInitItemDatas().add(itemdata);
        }
    }
}
