package com.example.managercoffee.MODEL;

public class DayCount {
    private String day;
    private int income;
    private int itemcount;
    public DayCount() {
    }

    public DayCount(String day, int income, int itemcount) {
        this.day = day;
        this.income = income;
        this.itemcount = itemcount;
    }

    public int getItemcount() {
        return itemcount;
    }

    public void setItemcount(int itemcount) {
        this.itemcount = itemcount;
    }

    public String getDay() {
        return day;
    }

    public int getIncome() {
        return income;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}
