package com.example.managerv1.Model;

public class Product {
    private String name;
    private String type;
    private String description;
    private String unit;
    private int price;
    private String imageURL;

    public Product(String name, String type, String description, String unit, int price, String imageURL) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.unit = unit;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Product(String name, String type, String unit, int price) {
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getUnit() {
        return unit;
    }

    public int getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }
}
