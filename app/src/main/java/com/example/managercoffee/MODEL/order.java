package com.example.managercoffee.MODEL;

import java.util.List;

public class order {
    private String date;
    private String time;
    private List<item_order> orderlist;

    public order() {
    }

    public order(String date, String time, List<item_order> orderlist) {
        this.date = date;
        this.time = time;
        this.orderlist = orderlist;
    }
    public int getTotalAmmount(){
        int ammount=0;
        for(item_order i : orderlist){
            ammount=ammount+i.getAmmount();
        }
        return ammount;
    }
    public int getTotalPrice(){
        int price=0;
        for(item_order i : orderlist){
            price=price+i.getAmmount()*i.getPrice();
        }
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setOrderlist(List<item_order> orderlist) {
        this.orderlist = orderlist;
    }

    public List<item_order> getOrderlist() {
        return orderlist;
    }
}
