package nl.hu.bep.setup;

import nl.hu.bep.shopping.model.Product;
import nl.hu.bep.shopping.model.Shop;
import nl.hu.bep.shopping.model.Shopper;
import nl.hu.bep.shopping.model.ShoppingList;
import nl.hu.bep.shopping.webservices.AuthenticationResource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("initializing application");
        Shopper p = new Shopper("Dum-Dum");
        AuthenticationResource.userpassword.put("Dum-Dum", "test");
        ShoppingList il = new ShoppingList("initialList", p);
        ShoppingList al = new ShoppingList("anotherList", p);
        p.addList(il);
        p.addList(al);
        il.addItem(new Product("Cola Zero"), 4);
        il.addItem(new Product("Cola Zero"), 4);
        il.addItem(new Product("Toiletpapier 6stk"), 1);
        al.addItem(new Product("Paracetamol 30stk"), 3);

        Shopper tom = new Shopper("tom");
        AuthenticationResource.userpassword.put("tom", "test");
        AuthenticationResource.userpassword.put("admin", "test");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("terminating application");
    }

}
