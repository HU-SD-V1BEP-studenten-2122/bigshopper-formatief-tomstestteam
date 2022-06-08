package nl.hu.bep.shopping.model;

import java.util.ArrayList;
import java.util.List;
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

    public List<ShoppingList> getAllShoppingLists(String owner){
        List<ShoppingList> lists = new ArrayList<>();
        for(ShoppingList list: Shop.getShop().getAllShoppingLists()){
            if(list.getOwner().getName().equals(owner)){
                lists.add(list);
            }
        }
        return lists;
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
        return found == null ? null : found.getMyList();
    }

    public List<Product> getAllProducts() {
        return Product.getAllProducts();
    }

    public Product getProduct(String name) {
        return Product.getAllProducts().stream().filter(hasName(name)).findFirst().orElse(null);
    }

    public void removeShopper(Shopper shopper) {
        Shopper.removeShopper(shopper);
    }
}
