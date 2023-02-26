package ui;

import model.fridge.Fridge;
import model.item.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

// The main application through which fridges and items are accessed by the user.
public class Fresher {
    private Scanner input;
    private Fridge fridge;

    // EFFECTS: Runs the Fresher application.
    public Fresher() {
        runFresher();
    }

    // MODIFIES:
    // EFFECTS:
    public void runFresher() {
        boolean keepGoing = true;
        String command;

        initialization();

        while (keepGoing) {
            displayMenu();

            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nBon appetit!");
    }

    public void initialization() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        fridge = new Fridge();
    }

    public void displayMenu() {
        System.out.println("\nSelect one of the following:");
        System.out.println("\ta -> add an item to the fridge");
        System.out.println("\tr -> remove an item from the fridge");
        System.out.println("\tf -> find the expiration date of a given item");
        System.out.println("\te -> view the list of expired items");
        System.out.println("\tt -> view the total list of items");
        System.out.println("\tc -> view all items in a given category");
        System.out.println("\tq -> quit the application");
    }

    public void processCommand(String command) {
        switch (command) {
            case "a":
                doAddItem();
                break;
            case "r":
                doRemoveItem();
                break;
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

    public void doAddItem() {
        System.out.println("\nWhat is the item name?");
        String itemName = input.next();

        System.out.println("\nExpiration year?");
        int expYear = input.nextInt();

        System.out.println("\nExpiration month in integer form (eg. January = 1)?");
        int expMonth = input.nextInt();

        System.out.println("\nExpiration day?");
        int expDay = input.nextInt();

        System.out.println("\nFood group (eg. Protein, Dairy, Fruit, etc.)");
        String itemCat = input.next();

        Item item = makeItem(itemName, expYear, expMonth, expDay, itemCat);

        fridge.addItem(item);

        System.out.println("\nItem successfully added. Yum!");
    }

    public Item makeItem(String itemName, int expYear, int expMonth, int expDay, String itemCat) {
        Calendar c = Calendar.getInstance();
        c.set(expYear, (expMonth - 1), expDay);
        Date itemExpDate = c.getTime();

        return new Item(itemName, itemExpDate, itemCat);
    }

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

    public void doFindExpDate() {
        System.out.println("\nWhat is the name of the item you are curious about?");
        String itemName = input.next();

        Date desiredExpDate = fridge.findExpDate(itemName);

        if (desiredExpDate == null) {
            System.out.println("\nNo items in the fridge with that name were found...");
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(desiredExpDate);

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            month = month + 1;
            int day = c.get(Calendar.DAY_OF_MONTH);

            System.out.println("\nThe expiration date for " + itemName + " is "
                    + year + "/" + month + "/" + day + ".");
        }
    }

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
}
