package persistance;

import model.Fridge;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    public void testFileNotFound() {
        JsonWriter writer = new JsonWriter("./data/\0fakeName.json");

        try {
            writer.open();
            fail("IOException should have been thrown.");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testEmptyFile() {
        String destination = "data/testWriterEmpty.json";
        JsonWriter writer = new JsonWriter(destination);
        JsonReader reader = new JsonReader(destination);
        Fridge fridge = new Fridge();
        try {
            writer.open();
            writer.write(fridge);
            writer.close();
            fridge = reader.read();
            ArrayList<Item> items = fridge.getAllItems();

            assertEquals(0, items.size());

        } catch (IOException e) {
            fail("IOException should not have been thrown.");
        } catch (ParseException p) {
            fail("ParseException should not have been thrown.");
        }
    }

    @Test
    public void testGeneralFridge() {
        JsonReader reader = new JsonReader("data/testWriterGeneral.json");

        try {
            initializeFile();

            Fridge newFridge = reader.read();
            ArrayList<Item> items = newFridge.getAllItems();
            assertEquals(2, items.size());
            checkItem("beef", "10/10/1010", "protein", items.get(0));
            checkItem("corn", "11/11/1111", "vegetable", items.get(1));
        } catch (ParseException p) {
            fail("ParseException shouldn't have been thrown.");
        } catch (IOException e) {
            fail("IOException shouldn't have been thrown.");
        }
    }

    public void initializeFile() throws IOException, ParseException {
        JsonWriter writer = new JsonWriter("data/testWriterGeneral.json");
        Fridge fridge = new Fridge();

        fridge.addItem(new Item("beef", "10/10/1010", "protein"));
        fridge.addItem(new Item("corn", "11/11/1111", "vegetable"));

        writer.open();
        writer.write(fridge);
        writer.close();
    }
}
