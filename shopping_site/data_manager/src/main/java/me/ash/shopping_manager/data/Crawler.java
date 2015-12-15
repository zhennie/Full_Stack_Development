package me.ash.shopping_manager.data;

import me.ash.shopping_manager.io.GsonConverter;

import me.ash.shopping_manager.model.Product;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ash on 4/1/15.
 */
public class Crawler {
    public static ArrayList<String> findBaseNavs(Document doc) {
        ArrayList<String> baseLinks = new ArrayList<String>();

        Elements baseButtons = doc.select(".accord-header");

        for (Element header : baseButtons) {
            if (header.text().equals("New Arrivals") ||
                    header.text().equals("Editorial")) continue;

            String link = header.attr("abs:href");
            baseLinks.add(link);
        }

        return baseLinks;
    }

    public static ArrayList<String> findLeafPages(ArrayList<String> baseLinks) {
        ArrayList<String> destinationLinks = new ArrayList<String>();

        for (String baseLink : baseLinks) {
            Document doc = getDocument(baseLink);

            Elements partDestLinks = doc.select(".list-unstyled").get(2).select("a");

            for (Element partDestLink : partDestLinks) {
                if (partDestLink.text().toLowerCase().contains("featured")) continue;

                String link = partDestLink.attr("abs:href");
                destinationLinks.add(link);
            }
        }

        return destinationLinks;
    }

    public static int getProductsNum(Document doc){
        int res = 0;

        Elements productsNums = doc.select(".results-hits");

        if (!productsNums.isEmpty()) {
            String ret = productsNums.get(0).text();

            String n = ret.replaceAll("\\D*", "").trim().replaceAll(",","");
            res = Integer.parseInt(n);
        }

        return res;
    }

    public static String generateURL(String url, int timer) {
        String toAddStart = "?start=";
        String toAddEnd = "&sz=48";
        String toAddMid = String.valueOf(timer * 48);
        return url + toAddStart + toAddMid + toAddEnd;
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


    public static void main(String[] args) {
        String site = "http://www.barneys.com";

        Document doc = getDocument(site);

        ArrayList<String> baseNavs = findBaseNavs(doc);
        ArrayList<String> leafPages = findLeafPages(baseNavs);

        for (String leafPage: leafPages){
            Document tmp = getDocument(leafPage);


            String category = tmp.select("body").attr("id");

            leafPage = tmp.baseUri();

            int pageCount = 0;

            while (true) {

                String pagelink = generateURL(leafPage, pageCount);
                String pathname = getOutputFileName(category, pageCount);

                if(System.currentTimeMillis() - new File(pathname).lastModified() < (1000*60*60*24)){
                    System.err.println("Ignoring: " + pagelink);
                    pageCount++;
                    continue;
                }

                if ( crawlAndSaveToDisk(pagelink, pathname)) break;

                pageCount++;
            }
        }
    }

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

    /**
     *
     * @param toCrawl URL to crawl
     * @param toSave File name to save
     * @return if this is the last page.
     */
    private static boolean crawlAndSaveToDisk(String toCrawl, String toSave) {
        ArrayList<Product> productExps = parseAsProducts(toCrawl);
        if (productExps.isEmpty()) {
            return true;
        }


        String productsInJson = GsonConverter.productsToJson(productExps);

        System.out.println("now write to file for " + toSave);
        System.out.println(toCrawl);

        try {

            FileUtils.writeStringToFile(new File(toSave), productsInJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getOutputFileName(String category, int pageCount) {
        return "expdata/" + category + "_page" + String.valueOf(pageCount + 1) + ".txt";
    }
}
