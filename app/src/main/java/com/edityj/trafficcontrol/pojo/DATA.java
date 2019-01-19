package com.edityj.trafficcontrol.pojo;

import java.util.List;

public class DATA {
    private List<String> warning;
    private List<String> remind;
    private List<String> speed;
    private List<Integer> icon;

    public List<String> getWarning() {
        return warning;
    }

    public void setWarning(List<String> warning) {
        this.warning = warning;
    }

    public List<String> getRemind() {
        return remind;
    }

    public void setRemind(List<String> remind) {
        this.remind = remind;
    }

    public List<String> getSpeed() {
        return speed;
    }

    public void setSpeed(List<String> speed) {
        this.speed = speed;
    }

    public List<Integer> getIcon() {
        return icon;
    }

    public void setIcon(List<Integer> icon) {
        this.icon = icon;
    }
}
