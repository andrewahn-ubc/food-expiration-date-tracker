package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// Unit tests for the Fridge class.
public class FridgeTest {
    private Fridge fridge;
    private Date date1;
    private Date date2;
    private Item item1;
    private Item item2;
    private Item item3;

    @BeforeEach
    public void setUp() {
        fridge = new Fridge();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(200 + 1900, Calendar.JANUARY, 1);
        c2.set(100 + 1900, Calendar.JANUARY, 1);

        date1 = c1.getTime();
        date2 = c2.getTime();
        item1 = new Item("Beef", date1, "Meat");
        item2 = new Item("Pork", date1, "Meat");
        item3 = new Item("Milk", date2, "Dairy");
    }

    @Test
    public void testAddItemNoDupes() {
        fridge.addItem(item1);
        fridge.addItem(item2);
        fridge.addItem(item3);

        ArrayList<Item> allItems = fridge.getAllItems();

        assertEquals(item1, allItems.get(0));
        assertEquals(item2, allItems.get(1));
        assertEquals(item3, allItems.get(2));
    }

    @Test
    public void testAddItemOneDupe() {
        fridge.addItem(item1);
        fridge.addItem(item2);
        fridge.addItem(item2);
        fridge.addItem(item3);

        ArrayList<Item> allItems = fridge.getAllItems();

        assertEquals(3, allItems.size());
        assertEquals(item1, allItems.get(0));
        assertEquals(item2, allItems.get(1));
        assertEquals(item3, allItems.get(2));
    }

    @Test
    public void testGetItemsInCatNoItems() {
        fridge.addItem(item1);
        fridge.addItem(item2);
        fridge.addItem(item3);

        ArrayList<Item> itemsInCat = fridge.getItemsInCat("VEGETABLES");

        assertEquals(0, itemsInCat.size());
    }

    @Test
    public void testGetItemsInCatTwoItems() {
        fridge.addItem(item1);
        fridge.addItem(item2);
        fridge.addItem(item3);

        ArrayList<Item> itemsInCat = fridge.getItemsInCat("meat");

        assertEquals(2, itemsInCat.size());
        assertEquals(item1, itemsInCat.get(0));
        assertEquals(item2, itemsInCat.get(1));
    }

    @Test
    public void testRemoveItemItemFound() {
        fridge.addItem(item1);
        fridge.addItem(item2);

        fridge.removeItem("Beef");

        ArrayList<Item> allItems = fridge.getAllItems();

        assertEquals(1, allItems.size());
    }

    @Test
    public void testRemoveItemItemNotFound() {
        fridge.addItem(item1);
        fridge.addItem(item2);

        fridge.removeItem("Carbohydrate");

        ArrayList<Item> allItems = fridge.getAllItems();

        assertEquals(2, allItems.size());
    }

    @Test
    public void testViewExpiredItemsNoneExpired() {
        fridge.addItem(item1);
        fridge.addItem(item2);

        ArrayList<Item> expiredItems = fridge.viewExpiredItems();

        assertEquals(0, expiredItems.size());
    }

    @Test
    public void testViewExpiredItemsOneItemExpired() {
        fridge.addItem(item1);
        fridge.addItem(item2);
        fridge.addItem(item3);

        ArrayList<Item> result = fridge.viewExpiredItems();

        assertEquals(1, result.size());
        assertEquals(item3, result.get(0));
    }

    @Test
    public void testFindExpDateNotFound() {
        fridge.addItem(item1);
        fridge.addItem(item2);
        fridge.addItem(item3);

        Date result = fridge.findExpDate("Apple");

        assertNull(result);
    }

    @Test
    public void testFindExpDateFound() {
        fridge.addItem(item1);
        fridge.addItem(item2);
        fridge.addItem(item3);

        Date result1 = fridge.findExpDate("bEEF");

        assertEquals(date1, result1);

        Date result2 = fridge.findExpDate("MILK");

        assertEquals(date2, result2);
    }
}

