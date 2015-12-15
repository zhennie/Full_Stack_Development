package me.ash.shopping_manager;

import com.avaje.ebean.Ebean;
import me.ash.shopping_manager.model.Product;

import java.util.List;
import me.ash.shopping_manager.io.GsonConverter;


/**
 * Created by ash on 4/5/15.
 */
public class QueryExp {

    public static List<Product> getProductsByBrand(String brandName){
        List<Product> productsOfGivenBrand = Ebean.find(Product.class).
                where().
                ilike("brand", "%" + brandName + "%")
                .findList();

        return productsOfGivenBrand;
    }

    public static String getProductsByBrandInJson(String brandName){
        String result = GsonConverter.productsToJson(getProductsByBrand(brandName));
        return result;
    }

//    public static void main(String[] args) {
//        System.out.print(getProductsByBrandInJson("Tom Ford"));
//    }
}
