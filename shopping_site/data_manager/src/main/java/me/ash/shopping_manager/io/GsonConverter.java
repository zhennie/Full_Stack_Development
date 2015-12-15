package me.ash.shopping_manager.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import me.ash.shopping_manager.model.*;
/**
 * Created by ash on 4/1/15.
 */
public class GsonConverter {

    public static Gson gson = new Gson();

    private GsonConverter() { /* private empty constructor to avoid accidental constructions */
    }

    public static String productToJson(Product product){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(product);
    }

    public static String productsToJson(List<Product> products){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(products);
    }

    public static Product jsonToProduct(String str){
        return gson.fromJson(str, Product.class);
    }

    public static List<Product> jsonToProducts(String str) {
        return gson.fromJson(str, new TypeToken<List<Product>>(){}.getType());
    }

}
