package nl.hu.bep.shopping.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Shop {
    private static Shop deShop = new Shop();

    private static Predicate<NamedObject> hasName(String name) {
        return e -> e.getName().equals(name);
    }

    public static Shop getShop() {
        return deShop;
    }

    public Shop() {
        //Initializing dummy data moved to contextinitialization
    }

    public List<ShoppingList> getAllShoppingLists() {
        return ShoppingList.getAllLists();
    }

    public ShoppingList getShoppingListByName(String nm) {
        return ShoppingList.getAllLists().stream().filter(hasName(nm)).findFirst().orElse(null);
    }

    public List<Shopper> getAllPersons() {
        return Shopper.getAllShoppers();
    }

    public Shopper getShopper(String nm) {
        Shopper found = Shopper.getAllShoppers().stream().filter(hasName(nm)).findFirst().orElse(null);
        return found;
    }

    public List<ShoppingList> getListFromPerson(String nm) {
        Shopper found = getShopper(nm);
        return found == null ? null : found.getAllLists();
    }

    public List<Product> getAllProducts() {
        return Product.getAllProducts();
    }

    public Product getProduct(String name) {
        return Product.getAllProducts().stream().filter(hasName(name)).findFirst().orElse(null);
    }
}
