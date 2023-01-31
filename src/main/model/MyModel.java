package model;

import java.util.Date;

public class Item {
    public String name;
    public Date expDate;

    public void Item(String name, Date expDate) {
        setName(name);
        setExpDate(expDate);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate
    }

    public static class Fridge {
    }
}

