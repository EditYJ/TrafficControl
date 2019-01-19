package com.edityj.trafficcontrol.util;

import android.content.Context;
import android.content.res.TypedArray;

import com.edityj.trafficcontrol.R;
import com.edityj.trafficcontrol.pojo.DATA;

public class InitData {
    private DATA data;
    private Context context;

    public InitData(Context context){
        this.data = new DATA();
        this.context=context;
    }

    public DATA readData(){
        int i;
        TypedArray icon = context.getResources().obtainTypedArray(R.array.icon);
        String[] speed = context.getResources().getStringArray(R.array.speed);
        String[] warning = context.getResources().getStringArray(R.array.warning);
        String[] remind = context.getResources().getStringArray(R.array.remind);
        for(i=0;i<icon.length();i++){
            data.getIcon().add(icon.getResourceId(i,0));
        }
        for(i=0;i<speed.length;i++){
            data.getSpeed().add(speed[i]);
        }
        for(i=0;i<warning.length;i++){
            data.getWarning().add(warning[i]);
        }
        for(i=0;i<remind.length;i++){
            data.getRemind().add(remind[i]);
        }
        return data;
    }
}
