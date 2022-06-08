package nl.hu.bep.shopping.tests;

import nl.hu.bep.shopping.model.Product;
import nl.hu.bep.shopping.model.Shopper;
import nl.hu.bep.shopping.model.ShoppingList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShopperTest {
    private Shopper dumdum;
    private Shopper alice;
    private ShoppingList initialList, anotherList;

    @BeforeEach
    public void setup() {
        dumdum = new Shopper("Dum-Dum");
        alice = new Shopper("Alice");
        initialList = new ShoppingList("initialList", dumdum);
        anotherList = new ShoppingList("anotherList", dumdum);
        dumdum.addList(initialList);
        dumdum.addList(anotherList);
        initialList.addItem(new Product("Cola Zero"), 4);
        initialList.addItem(new Product("Cola Zero"), 4);
        initialList.addItem(new Product("Toiletpapier 6stk"), 1);
        anotherList.addItem(new Product("Paracetamol 30stk"), 3);
    }

    @Test
    public void shouldHaveTwoLists() {
        assertEquals(2, dumdum.getMyList().size());
    }

    @Test
    public void canRemoveList(){
        dumdum.removeList(initialList);
        assertNull(initialList.getOwner());
    }

    @Test
    public void canMoveList(){
        alice.addList(initialList);
        assertEquals(alice, initialList.getOwner());
        assertTrue(alice.getMyList().contains(initialList));
        assertFalse(dumdum.getMyList().contains(initialList));
    }

    @Test
    public void createListWithOwner(){
        ShoppingList sl = new ShoppingList("VerseList", alice);
        assertTrue(alice.getMyList().contains(sl));
    }
}
