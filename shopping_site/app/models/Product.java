package models;

/**
 * Created by ash on 5/1/15.
 */

import java.sql.Timestamp;
import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="product")
public class Product extends Model {
    private String site;
    private String name;
    private String imgUrl;
    private String brand;
    private int price;


    @Version
    Timestamp lastUpdate;

//    public Product() {
//    }
//
//    public Product(String site, String name, String imgUrl, String brand, int price) {
//        this.site = site;
//        this.name = name;
//        this.imgUrl = imgUrl;
//        this.brand = brand;
//        this.price = price;
//    }


    public String getSite() {
        return site;
    }

//    public void setSite(String site) {
//        this.site = site;
//    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {this.name = name;}

    public String getImgUrl() {
        return imgUrl;
    }

//    public void setImgUrl(String imgUrl) {
////        this.imgUrl = imgUrl;
////    }

    public String getBrand() {
        return brand;
    }

//    public void setBrand(String brand) {
//        this.brand = brand;
//    }

    public int getPrice() {
        return price;
    }

//    public void setPrice(int price) {
//        this.price = price;
//    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

//    public void setLastUpdate(Timestamp lastUpdate) {
//        this.lastUpdate = lastUpdate;
//    }

}
