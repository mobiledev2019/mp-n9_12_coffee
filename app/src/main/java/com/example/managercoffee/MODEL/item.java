package com.example.managercoffee.MODEL;

public class item {
    private String id;
    private String name;
    private String menu;
    private int price;
    private String des;
    private String URL;
    private int time;

    public item(String id, String name, String menu, int price, String des, String URL, int time) {
        this.id = id;
        this.name = name;
        this.menu = menu;
        this.price = price;
        this.des = des;
        this.URL = URL;
        this.time = time;
    }

    public item() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMenu() {
        return menu;
    }

    public int getPrice() {
        return price;
    }

    public String getDes() {
        return des;
    }

    public String getURL() {
        return URL;
    }

    public int getTime() {
        return time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
