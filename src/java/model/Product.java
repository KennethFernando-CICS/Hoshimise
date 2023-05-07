package model;

import java.util.Arrays;
import java.util.List;

public class Product {
    
    private String name;
    private double price;
    private int stock;
    private String sizeString;
    private List<String> sizeList;
    private String imgName;
    
    public Product(){}

    public Product(String name, double price, int stock, String sizeString, String imgName) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.sizeString = sizeString;
        this.sizeList = Arrays.asList(sizeString.split("/"));
        this.imgName = imgName;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getSizeString() {
        return sizeString;
    }

    public List<String> getSizeList() {
        return sizeList;
    }

    public String getImgName() {
        return imgName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setSizeString(String sizeString) {
        this.sizeString = sizeString;
    }

    public void setSizeList(List<String> sizeList) {
        this.sizeList = sizeList;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
                
}