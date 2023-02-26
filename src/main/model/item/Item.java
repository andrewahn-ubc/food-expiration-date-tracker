package model.item;

import java.util.Date;

public abstract class Item {
    private String name;
    private Date expDate;

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: constructs an item with a name and an expiration date
    public Item(String name, Date expDate) {
        this.name = name;
        this.expDate = expDate;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public void setName(String name) {
        this.name = name;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public String getName() {
        return this.name;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    // REQUIRES:
    // MODIFIES:
    // EFFECTS:
    public Date getExpDate() {
        return this.expDate;
    }
}

