package model;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Represents an item having a name, date and category (eg. dairy, protein, etc.).
public class Item {
    private static final DateFormat expDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private final String name;
    private final Date expDate;
    private final String category;

    // EFFECTS: constructs an item with a name and an expiration date
    public Item(String name, Date expDate, String category) {
        this.name = name;
        this.expDate = expDate;
        this.category = category;
    }

    // EFFECTS: Same as other constructor except accepts expiration Date in String form.
    public Item(String name, String strExpDate, String category) throws ParseException {
        this.name = name;
        this.expDate = strToDate(strExpDate);
        this.category = category;
    }

    // EFFECTS: Converts a date object into its string form.
    public static String dateToStr(Date date) {
        return expDateFormat.format(date);
    }

    // EFFECTS: Converts a date in string form into its Date form.
    public static Date strToDate(String str) throws ParseException {
        return expDateFormat.parse(str);
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

    // EFFECTS: Documents an item's information onto a JSON object.
    public JSONObject toJson() {
        JSONObject jsonItem = new JSONObject();
        String strExpDate = dateToStr(this.expDate);

        jsonItem.put("Name", name);
        jsonItem.put("Expiration Date", strExpDate);
        jsonItem.put("Category", category);

        return jsonItem;
    }
}

