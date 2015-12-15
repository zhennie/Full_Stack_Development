package me.ash.shopping_manager.model;

import java.sql.Timestamp;

import javax.persistence.*;

/**
 * Created by ash on 4/2/15.
 */

/**
 * A simple entity bean. This table does not have to exist if
 * you use Ebean's DDL generation.
 */
@Entity
@Table(name="product")
public class Product {

    private String site;
    private String name;
    private String imgUrl;
    private String brand;
    private int price;


    @Version
    Timestamp lastUpdate;

    public Product() {
    }

    public Product(String site, String name, String imgUrl, String brand, int price) {
        this.site = site;
        this.name = name;
        this.imgUrl = imgUrl;
        this.brand = brand;
        this.price = price;
    }


    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


}
