package persistance;

import model.Fridge;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

// Represents the object that transcribes the current fridge onto a file as a JSON object.
public class JsonWriter {
    private final String destination;
    private PrintWriter writer;

    // EFFECTS: Constructs a writer with a destination.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: Constructs a new PrintWriter with the given destination.
    public void open() throws IOException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: Adapts a fridge's information onto the destination file.
    public void write(Fridge fridge) {
        JSONObject jsonFridge = fridge.toJson();
        String stringFridge = jsonFridge.toString(1);
        writer.print(stringFridge);
    }

    // MODIFIES: this
    // EFFECTS: Closes the writer
    public void close() {
        writer.close();
    }

}
