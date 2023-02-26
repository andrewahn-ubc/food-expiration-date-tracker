package model.item;

import java.util.Date;

// Represents an item having a name, date and category (eg. dairy, meat, etc.).
public class Item {
    private final String name;
    private final Date expDate;
    private final String category;

    // EFFECTS: constructs an item with a name and an expiration date
    public Item(String name, Date expDate, String category) {
        this.name = name;
        this.expDate = expDate;
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public Date getExpDate() {
        return this.expDate;
    }

    public String getCategory() {
        return this.category;
    }
}

