package com.edityj.trafficcontrol.util;

import com.edityj.trafficcontrol.pojo.ITEMDATA;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EditYJ
 * @Email 158392613@qq.com
 * Create at 2019/1/20
 * description: 双实例模式，用于加载原始数据，和修改后的数据。
 */
public class InitItemData {
    private InitItemData initItemDataStart; //原始数据
    private InitItemData initItemData;  //修改后的数据

    private List<ITEMDATA> ItemDatas;

    private InitItemData(){
        this.ItemDatas = new ArrayList<>();
    }

    private static class SingletonInstance {
        private static final InitItemData initItemDataStart = new InitItemData();
        private static final InitItemData initItemData = new InitItemData();
    }

    public static InitItemData getStartInstance() {
        return SingletonInstance.initItemDataStart;
    }

    public static InitItemData getInstance() {
        return SingletonInstance.initItemData;
    }

    public List<ITEMDATA> getInitItemDatas() {
        return ItemDatas;
    }

    public void setInitItemDatas(List<ITEMDATA> ItemDatas) {
        this.ItemDatas = ItemDatas;
    }
}
