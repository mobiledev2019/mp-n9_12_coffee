package com.example.managercoffee.MODEL;

import android.os.Parcel;
import android.os.Parcelable;

public class item_order implements Parcelable {
    private String name;
    private int price;
    private int ammount;

    public item_order() {
    }

    public item_order(String name, int price, int ammount) {
        this.name = name;
        this.price = price;
        this.ammount = ammount;
    }

    protected item_order(Parcel in) {
        name = in.readString();
        price = in.readInt();
        ammount = in.readInt();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    public static final Creator<item_order> CREATOR = new Creator<item_order>() {
        @Override
        public item_order createFromParcel(Parcel in) {
            return new item_order(in);
        }

        @Override
        public item_order[] newArray(int size) {
            return new item_order[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getAmmount() {
        return ammount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeInt(ammount);
    }
}
