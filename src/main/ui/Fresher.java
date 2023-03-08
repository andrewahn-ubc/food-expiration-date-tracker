package ui;

import model.Fridge;
import model.Item;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static model.Item.dateToStr;
import static model.Item.strToDate;

// The main application through which the fridge can be accessed by the user.
public class Fresher {
    private final String destination = "./data/fridge.json";
    private final JsonReader jsonReader = new JsonReader(destination);
    private final JsonWriter jsonWriter = new JsonWriter(destination);
    private Scanner input;
    private Fridge fridge;

    // EFFECTS: Runs the Fresher application.
    public Fresher() {
        runFresher();
    }

    // MODIFIES: this
    // EFFECTS: Processes user input.
    public void runFresher() {
        boolean keepGoing = true;
        String command;

        initialization();

        while (keepGoing) {
            displayMenuMain();

            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommandMain(command);
            }
        }

        System.out.println("\nBon appetit!");
    }

    // MODIFIES: this
    // EFFECTS: Initializes an input scanner and a fridge.
    public void initialization() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        fridge = new Fridge();
    }

    // EFFECTS: Prints out the main options for Fresher.
    public void displayMenuMain() {
        System.out.println("\nSelect one of the following:");
        System.out.println("\tl -> reload your fridge");
        System.out.println("\ts -> save your current fridge");
        System.out.println("\ti -> edit inventory");
        System.out.println("\tv -> view inventory");
        System.out.println("\tq -> quit the application");
    }

    // MODIFIES: this
    // EFFECTS: Processes initial user input when it is not "q".
    public void processCommandMain(String command) {
        switch (command) {
            case "s":
                processCommandSave();
                break;
            case "l":
                processCommandLoad();
                break;
            case "i":
                processCommandEdit();
                break;
            case "v":
                processCommandView();
                break;
            default:
                System.out.println("\nThat's an invalid entry :(");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: Processes user input for editing options.
    public void processCommandEdit() {
        displayMenuEdit();
        String nextCommand = input.next();

        switch (nextCommand) {
            case "a":
                doAddItem();
                break;
            case "r":
                doRemoveItem();
                break;
            default:
                System.out.println("\nThat's an invalid entry :(");
                break;
        }
    }

    // EFFECTS: Prints out the sub options for edit inventory
    public void displayMenuEdit() {
        System.out.println("\nSelect one of the following:");
        System.out.println("\ta -> add an item to the fridge");
        System.out.println("\tr -> remove an item from the fridge");
    }

    // MODIFIES: this
    // EFFECTS: Processes user input for viewing options.
    public void processCommandView() {
        displayMenuView();

        String nextCommand = input.next();

        switch (nextCommand) {
            case "f":
                doFindExpDate();
                break;
            case "e":
                doViewExpiredItems();
                break;
            case "t":
                doGetAllItems();
                break;
            case "c":
                doGetItemsInCat();
                break;
            default:
                System.out.println("\nThat's an invalid entry :(");
                break;
        }
    }

    // EFFECTS: Prints out the sub options for edit inventory
    public void displayMenuView() {
        System.out.println("\tf -> find the expiration date of a given item");
        System.out.println("\te -> view the list of expired items");
        System.out.println("\tt -> view the total list of items");
        System.out.println("\tc -> view all items in a given category");
    }

    // MODIFIES: this
    // EFFECTS: Creates a new item and tries to add it to the fridge.
    public void doAddItem() {
        try {
            Item item = makeNewItem();
            ArrayList<Item> allItems = fridge.getAllItems();

            int initialFridgeSize = allItems.size();
            fridge.addItem(item);
            int finalFridgeSize = allItems.size();

            if (initialFridgeSize == finalFridgeSize) {
                System.out.println("\n"
                        + "An item with that name already exists."
                        + " Please choose a different name (eg. beef_2)!");
            } else {
                System.out.println("\nItem successfully added. Yum!");
            }
        } catch (ParseException e) {
            System.out.println("That expiration date is not valid!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Prompts the user for parameters and uses them to form an Item instantiation.
    public Item makeNewItem() throws ParseException {
        System.out.println("\nWhat is the item name?");
        String itemName = input.next();

        System.out.println("\nWhat is its expiration date in form mm/dd/yyyy?");
        String expDateInput = input.next();
        Date expDate = strToDate(expDateInput);

        System.out.println("\nFood group (eg. Protein, Dairy, Fruit, etc.)");
        String itemCat = input.next();

        return new Item(itemName, expDate, itemCat);
    }

    // MODIFIES: this
    // EFFECTS: If an item with the given name is found, removes it from the fridge.
    public void doRemoveItem() {
        System.out.println("\nWhat is the name of the item you want to remove?");
        String itemName = input.next();

        ArrayList<Item> itemList = fridge.getAllItems();
        int initialSize = itemList.size();

        fridge.removeItem(itemName);

        int finalSize = itemList.size();

        if (initialSize == finalSize) {
            System.out.println("\nNo item with a matching name was found :(");
        } else {
            System.out.println("\nItem successfully removed. Buh-bye " + itemName + "!");
        }
    }

    // EFFECTS: If there are expired items, prints out the list of names of expired items.
    public void doViewExpiredItems() {
        ArrayList<Item> expiredItemList = fridge.viewExpiredItems();

        if (expiredItemList.size() == 0) {
            System.out.println("\nThere are no expired items! Yay!");
        } else {
            int itemCount = 0;

            System.out.println("\nHere is the list of expired items:");

            for (Item i : expiredItemList) {
                itemCount++;
                System.out.println("\t" + itemCount + ". " + i.getName());
            }
        }
    }

    // EFFECTS: Prints out the expiration date of an item with the given name, if found.
    public void doFindExpDate() {
        System.out.println("\nWhat is the name of the item you are curious about?");
        String itemName = input.next();

        Date desiredExpDate = fridge.findExpDate(itemName);

        if (desiredExpDate == null) {
            System.out.println("\nNo items in the fridge with that name were found...");
        } else {
            String strDate = dateToStr(desiredExpDate);
            System.out.println("\nThe expiration date for "
                    + itemName + " is " + strDate + ".");
        }
    }

    // EFFECTS: Prints out a list of all the items in the fridge.
    public void doGetAllItems() {
        ArrayList<Item> allItems = fridge.getAllItems();

        if (allItems.size() == 0) {
            System.out.println("\nThe fridge seems to be empty... Time to buy groceries!");
        } else {
            int itemCount = 0;
            System.out.println("\nHere is the list of all items in the fridge:");

            for (Item i : allItems) {
                itemCount++;
                System.out.println("\t" + itemCount + ". " + i.getName());
            }
        }
    }

    // EFFECTS: Prints out the list of all the items in the fridge in the given category.
    public void doGetItemsInCat() {
        System.out.println("\nWhich food group are you interested in?");
        String cat = input.next();

        ArrayList<Item> itemsInCat = fridge.getItemsInCat(cat);

        if (itemsInCat.size() == 0) {
            System.out.println("\nIt seems like there are no items in that food group... ");
        } else {
            int itemCount = 0;
            System.out.println("\nHere is the list of all " + cat + " items in the fridge:");

            for (Item i : itemsInCat) {
                itemCount++;
                System.out.println("\t" + itemCount + ". " + i.getName());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Saves the current fridge into a data file in JSON format.
    public void processCommandSave() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.fridge);
            jsonWriter.close();
            System.out.println("Your fridge has successfully been saved!");
        } catch (IOException e) {
            System.out.println("The fridge could not be saved :(");
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads the data from a data file into the current fridge.
    public void processCommandLoad() {
        try {
            this.fridge = jsonReader.read();
            System.out.println("Your fridge has successfully been loaded from " + destination);
        } catch (IOException e) {
            System.out.println("The fridge could not be loaded :,)");
        } catch (ParseException p) {
            System.out.println("There is at least 1 item in the fridge whose date could not be read.");
        }
    }
}
