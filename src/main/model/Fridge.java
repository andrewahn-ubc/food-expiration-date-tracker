package model;

import java.util.ArrayList;
import java.util.Date;

// Represents a fridge - a list of food items.
public class Fridge {
    private ArrayList<Item> allItems;

    // EFFECTS: constructs an empty fridge
    public Fridge() {
        this.allItems = new ArrayList<>();
    }

    public ArrayList<Item> getAllItems() {
        return this.allItems;
    }

    // MODIFIES: this
    // EFFECTS: Adds the given item to the fridge if its name is new.
    public void addItem(Item i) {
        String itemName = i.getName();
        boolean unique = true;

        for (Item originalItem : this.allItems) {
            if (itemName.equalsIgnoreCase(originalItem.getName())) {
                unique = false;
                break;
            }
        }

        if (unique) {
            this.allItems.add(i);
        }
    }

    // EFFECTS: Returns all the items in the fridge of a given category.
    public ArrayList<Item> getItemsInCat(String cat) {
        ArrayList<Item> itemsInCat = new ArrayList<>();

        for (Item i : this.allItems) {
            if (cat.equalsIgnoreCase(i.getCategory())) {
                itemsInCat.add(i);
            }
        }

        return itemsInCat;
    }

    // MODIFIES: this
    // EFFECTS: If an item matching the given name is found in fridge, removes it.
    public void removeItem(String name) {
        this.allItems.removeIf(i -> name.equalsIgnoreCase(i.getName()));
    }

    // EFFECTS: Returns the list of items whose expiry dates are before today's date.
    public ArrayList<Item> viewExpiredItems() {
        ArrayList<Item> expiredItems = new ArrayList<>();
        Date todayDate = new Date();

        for (Item i : this.allItems) {
            if (todayDate.after(i.getExpDate())) {
                expiredItems.add(i);
            }
        }

        return expiredItems;
    }

    // EFFECTS: Returns the expiration date of the item with the given name, if it exists.
    public Date findExpDate(String name) {
        Date desiredDate = null;

        for (Item i : this.allItems) {
            if (name.equalsIgnoreCase(i.getName())) {
                desiredDate = i.getExpDate();
            }
        }

        return desiredDate;
    }
}
