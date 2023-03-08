package persistance;

import model.Item;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonTest {
    public void checkItem(String name, String expDate, String category, Item item) {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String newExpDate = format.format(item.getExpDate());

        assertEquals(name, item.getName());
        assertEquals(expDate, newExpDate);
        assertEquals(category, item.getCategory());
    }
}
