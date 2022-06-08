package nl.hu.bep.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Shopper implements NamedObject {
    private String name;
    private static List<Shopper> allShoppers = new ArrayList<>();
    private List<ShoppingList> myList = new ArrayList<>();

    public Shopper(String nm) {
        this.name = nm;
        if (!allShoppers.contains(this)) allShoppers.add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shopper shopper = (Shopper) o;
        return name.equals(shopper.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public static List<Shopper> getAllShoppers() {
        return Collections.unmodifiableList(allShoppers);
    }

    public static void removeShopper(Shopper shopper){
        allShoppers.remove(shopper);
    }

    public boolean addList(ShoppingList newList) {
        if (!myList.contains(newList)) {
            newList.getOwner().removeList(newList);
            newList.setOwner(this);
            return myList.add(newList);
        }
        return false;
    }

    @JsonIgnore
    public List<ShoppingList> getMyList() {
        return Collections.unmodifiableList(myList);
    }

    public int getAmountOfLists() {
        return myList.size();
    }

    public void removeList(ShoppingList list) {
        list.setOwner(null);
        this.myList.remove(list);

    }

    @Override
    public String toString() {
        return "Shopper{" +
                "name='" + name + '\'' +
                '}';
    }
}
