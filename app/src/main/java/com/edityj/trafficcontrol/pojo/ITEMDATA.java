package com.edityj.trafficcontrol.pojo;

public class ITEMDATA {
    private String danger;
    private String remind;
    private String speed;
    private int icon;
    private int play;
    private int delete;

    public ITEMDATA() {
        this.danger = null;
        this.remind = null;
        this.speed = null;
        this.icon = 0;
        this.play = 0;
        this.delete = 0;
    }

    public void clear(){
        this.danger = null;
        this.remind = null;
        this.speed = null;
        this.icon = 0;
        this.play = 0;
        this.delete = 0;
    }

    public String getDanger() {
        return danger;
    }

    public void setDanger(String danger) {
        this.danger = danger;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getPlay() {
        return play;
    }

    public void setPlay(int play) {
        this.play = play;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }
}
