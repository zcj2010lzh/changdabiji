package com.example.zhangdabiji;

public class MainviewBean {
    public MainviewBean(String groupName, String bili) {
        this.groupName = groupName;
        this.bili = bili;
    }

    public MainviewBean(int max, int progress, String groupName, String bili) {
        this.max = max;
        this.progress = progress;
        this.groupName = groupName;
        this.bili = bili;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBili() {
        return bili;
    }

    public void setBili(String bili) {
        this.bili = bili;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    private  int max;
    private  int progress;
    private String groupName;
    private String bili;
}
