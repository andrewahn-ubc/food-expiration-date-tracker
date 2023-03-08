package persistance;

import model.Item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Supertype for JsonReaderTest and JsonWriterTest.
public class JsonTest {
    // Effects: Checks that an item's fields have the expected values.
    public void checkItem(String name, String expDate, String category, Item item) {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String newExpDate = format.format(item.getExpDate());

        assertEquals(name, item.getName());
        assertEquals(expDate, newExpDate);
        assertEquals(category, item.getCategory());
    }
}
