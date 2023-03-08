package persistance;

import model.Fridge;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// The unit test class for JsonReader, testing its exception-handling as well as function.
public class JsonReaderTest extends JsonTest {
    @Test
    public void testFileNotFound() {
        JsonReader reader = new JsonReader("./data/fakeName.json");
        try {
            reader.read();
            fail("IOException should have been thrown.");
        } catch (ParseException p) {
            fail("ParseException shouldn't have been thrown here.");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testEmptyFile() {
        JsonReader reader = new JsonReader("data/testReaderEmpty.json");
        try {
            Fridge fridge = reader.read();
            ArrayList<Item> items = fridge.getAllItems();

            assertEquals(0, items.size());
        } catch (IOException e) {
            fail("IOException shouldn't have been thrown here.");
        } catch (ParseException p) {
            fail("ParseException shouldn't have been thrown here.");
        }

    }

    @Test
    public void testGeneralFridge() {
        JsonReader reader = new JsonReader("data/testReaderGeneral.json");

        try {
            Fridge fridge = reader.read();
            ArrayList<Item> items = fridge.getAllItems();

            assertEquals(2, items.size());
            checkItem("beef", "10/10/1010", "protein", items.get(0));
            checkItem("corn", "11/11/1111", "vegetable", items.get(1));
        } catch (IOException e) {
            fail("IOException shouldn't have been thrown here.");
        } catch (ParseException p) {
            fail("ParseException shouldn't have been thrown here.");
        }
    }
}
