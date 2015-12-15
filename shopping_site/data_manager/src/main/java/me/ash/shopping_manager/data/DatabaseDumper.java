package me.ash.shopping_manager.data;

import com.avaje.ebean.Ebean;
import me.ash.shopping_manager.model.Product;
import org.apache.commons.io.FileUtils;
import me.ash.shopping_manager.io.GsonConverter;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ash on 4/3/15.
 */
public class DatabaseDumper {
    public static int numOfProduct = 0;

    public static void dumpProductToDatabase(List<Product> products) {

        for (Product product : products) {

//            Product ebeanProduct = new Product(product.getSite(), product.getName(),
//                    product.getImgUrl(), product.getBrand(), product.getPrice());

            Ebean.save(product);
        }
    }

//    public static void dumpProductDescToDatabase(List<Product> products) { /* from products created from model */
//
//        for (Product product : products) {
//
//            Ebean.save(product);
//        }
//    }

    /**
     *
     * @param pathname : hard drive directory to read from
     */
    public static File[] readFilesFromDisk(String pathname) {
        File directory = FileUtils.getFile(pathname);

        return directory.listFiles();
    }

    /**
     *
     * @param files : array of File read from given hard drive directory
     */
    public static void dumpFilesToDatabase (File[] files) {
        for (File file : files) {
            try {
                String content = FileUtils.readFileToString(file);

                List<Product> products = GsonConverter.jsonToProducts(content);
                numOfProduct += products.size();
                dumpProductToDatabase(products);

            } catch (IOException e) {
                System.out.println("having EXCEPTION here!!!");
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        numOfProduct = 0;

        String pathname = "expdata/"; /* as for relative directory */

        dumpFilesToDatabase(readFilesFromDisk(pathname));

        System.out.println(numOfProduct);
    }
}
