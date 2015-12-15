package me.ash.shopping_manager;

import me.ash.shopping_manager.io.*;

import me.ash.shopping_manager.model.Product;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ash on 3/31/15.
 */
public class Experiment{

    private static Document getDocument(String url) {
        Document tmp = null;
        for (int i = 0; i < 5; i++) {
            if(tmp != null) break;
            try {
                tmp = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tmp;
    }

    public static ArrayList<Product> parseAsProducts(String site) {
        System.out.println("start parsing @ " + site);
        ArrayList<Product> productExps = new ArrayList<Product>();

        Document doc = getDocument(site);

        Elements links = doc.select(".product-tile");

        for (Element link: links){
            String productName = link.select("a").get(0).attr("title");
            String imgUrl = link.select("img").first().attr("src");
            String brand = link.select(".brand-link").text();
            int price = Integer.parseInt(link.select(".product-sales-price").text().substring(1).replaceAll(",", ""));

            Product item = new Product("Barneys", productName, imgUrl, brand, price);
            productExps.add(item);

        }

        return productExps;
    }

    public static ArrayList<Product> parseAsProductDescs(String site) {
        System.out.println("start parsing @ " + site);
        ArrayList<Product> products = new ArrayList<Product>();

        Document doc = getDocument(site);

        Elements links = doc.select(".product-tile");

        for (Element link: links){
            String productName = link.select("a").get(0).attr("title");
            String imgUrl = link.select("img").first().attr("src");
            String brand = link.select(".brand-link").text();
            int price = Integer.parseInt(link.select(".product-sales-price").text().substring(1).replaceAll(",", ""));

            Product item = new Product("Barneys", productName, imgUrl, brand, price);
            products.add(item);

        }

        return products;
    }

    public static void main(String[] args) {

        String site = "http://www.barneys.com/barneys-new-york/men/clothing";

        ArrayList<Product> ps = parseAsProducts(site);
        String p2j = GsonConverter.productsToJson(ps);


        System.out.println(p2j);
        System.out.println("===============================");


//        String pathname = "expdata/"; /* as for relative directory */
//
//        File[] fs = readFilesFromDisk(pathname);
//
//        try {
//            String content = FileUtils.readFileToString(fs[0]);
//            System.out.println(content);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
