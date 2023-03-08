package persistance;

import model.Fridge;
import model.Item;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

// Represents the object that transcribes data to the current fridge.
public class JsonReader {
    private final String source;
    private Fridge fridge;

    // EFFECTS: Constructs a reader with a file source.
    public JsonReader(String source) {
        this.source = source;
        this.fridge = new Fridge();
    }

    // MODIFIES: this
    // EFFECTS: Reads data from a source file and realizes it into a fridge.
    public Fridge read() throws IOException, ParseException {
        String stringFridge = readFile(source);
        JSONObject jsonFridge = new JSONObject(stringFridge);
        jsonFridgeToFridge(jsonFridge);

        return fridge;
    }

    // EFFECTS: Reads the source file and returns its contents as a string.
    public String readFile(String fileName) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }
    // citation: A significant portion of the above method was mimics the design of the
    //           JsonReader.readFile() method in the JsonSerializationDemo application.

    // MODIFIES: this
    // EFFECTS: Extracts items from a JSON object and adds them to the fridge.
    public void jsonFridgeToFridge(JSONObject jsonFridge) throws ParseException {
        JSONArray items = jsonFridge.getJSONArray("Items");

        for (Object item : items) {
            JSONObject jsonItem = (JSONObject) item;
            addJsonItem(jsonItem);
        }
    }

    // MODIFIES: this
    // EFFECTS: Extracts information about an item from JSON format and adds it to the fridge.
    public void addJsonItem(JSONObject jsonItem) throws ParseException {
        String name = jsonItem.getString("Name");
        String cat = jsonItem.getString("Category");
        Date expDate = extractExpDate(jsonItem);

        Item newItem = new Item(name, expDate, cat);
        fridge.addItem(newItem);
    }

    // EFFECTS: Extracts and converts the date from String type to Date type.
    public Date extractExpDate(JSONObject jsonItem) throws ParseException {
        String strExpDate = jsonItem.getString("Expiration Date");
        DateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

        return myFormat.parse(strExpDate);
    }
}
