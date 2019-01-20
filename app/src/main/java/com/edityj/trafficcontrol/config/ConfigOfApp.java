package com.edityj.trafficcontrol.config;

public final class ConfigOfApp {
    public final static String IP = "192.168.31.96";
    public final static int PORT = 2000;
    public final static String SERVER_RETURN_OK = "OK";
    //判断是否为数字两位开头的正则表达式
    public final static String REGEX="^[0-9].*$";

    public final static String SEND_SCREEN_HEAD="2:DISPLAY:";
    public final static String CHECK_BATTERY="1:BATTERY";
    public final static String CHANGE_LIGHT="3:BACKLIGHT:";
    public final static String SEND_SCREEN_POINT=",";
    public final static String SEND_IMG_HEAD="t";
    public final static String SEND_WARNING_MSG_HEAD="$$";
    public final static String SEND_WARNING_MSG_END=",,";




}
