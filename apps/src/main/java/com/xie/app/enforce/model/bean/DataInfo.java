package com.xie.app.enforce.model.bean;

/**
 * Created by Jie on 2018/1/24.
 * 模拟数据
 */

public class DataInfo {
    private String type; // 类型
    private int number; // 数量

    public DataInfo(String type, int number) {
        this.type = type;
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
