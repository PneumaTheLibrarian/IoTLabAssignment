package com.example.myapplication.Domain;

import java.util.ArrayList;

public class domain {
    private String name;
    private Integer current;
    private String changeAmount;
    private ArrayList<Integer> lineData;
    public domain(String name, Integer current, String changeAmount, ArrayList<Integer> listData) {
        this.name = name;
        this.current = current;
        this.changeAmount = changeAmount;
        this.lineData = listData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public String getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(String changeAmount) {
        this.changeAmount = changeAmount;
    }

    public ArrayList<Integer> getLineData() {
        return lineData;
    }

    public void setLineData(ArrayList<Integer> lineData) {
        this.lineData = lineData;
    }
}
