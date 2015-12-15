package controllers;

import com.avaje.ebean.Ebean;
import play.*;
import play.mvc.*;
import views.html.*;
import models.Product;
import java.util.List;
import play.db.ebean.*;

import play.libs.Json;


public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result trial() {
        return ok(trial.render());
    }

    public static Result readData(String input){
        List<Product> products = Ebean.find(Product.class).
                where().
                ilike("brand", "%" + input + "%")
                .findList();

        return ok(Json.toJson(products));
    }

    public static Result ag() {return ok(ag.render());}

}
