package model.fridge;

import model.item.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Fridge {
    private ArrayList<Item> fridge;

    // EFFECTS: constructs an empty fridge
    public Fridge() {
        this.fridge = new ArrayList<>();
    }

    // EFFECTS: returns the fridge
    public ArrayList<Item> getFridge() {
        return this.fridge;
    }

    // MODIFIES: this
    // EFFECTS: adds the given item to the fridge
    public void addItem(Item i) {
        this.fridge.add(i);
    }

    // MODIFIES: this
    // EFFECTS: If an item matching the given name is found in fridge, removes it and prints out "Item removed".
    //          If not (as in the fridge size remains unchanged), prints out "Item not found".
    public void removeItem(String name) {
        int initialSize = this.fridge.size();

        for (Item i: this.fridge) {
            if (Objects.equals(i.getName(), name)) {
                this.fridge.remove(i);
            }
        }

        if (initialSize == this.fridge.size()) {
            System.out.println("Item not found");
        } else {
            System.out.println("Item removed");
        }
    }

    // EFFECTS: returns the list of items whose expiry dates are before today's date
    public ArrayList<Item> viewExpiredItems() {
        ArrayList<Item> expiredItems = new ArrayList<>();

        for (Item i: this.fridge) {
            if (after(i.getExpDate())) {
                expiredItems.add(i);
            }
        }

        return expiredItems;
    }

    // EFFECTS: If an item with the given name is found in the fridge, returns its expiration date.
    //          If not, prints out "Item not found".
    public Date findExpDate(String name) {
        for (Item i: this.fridge) {
            if (Objects.equals(i.getName(), name)) {
                return i.getExpDate();
            }
        }

        // will the following line always be printed? or will it not be executed if the return statement
        // above executes?
        System.out.println("Item not found.");
    }
}
