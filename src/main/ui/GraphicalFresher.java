package ui;

import model.Fridge;
import model.Item;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.SpringLayout;

import static model.Item.dateToStr;

// Graphical UI for Fresher
public class GraphicalFresher extends JFrame implements ActionListener {
    private final SpringLayout layout;
    private final JPanel contentPane;
    private DefaultListModel<Item> listItems;
    private JList<Item> list;
    private JScrollPane listScrollPane;
    private JButton removeItemBtn;
    private final JPanel buttonPane;
    private JTextField findExpDateField;
    private JPanel newPanel;
    private JTextField t1;
    private JTextField t2;
    private JTextField t3;
    private final String destination = "data/fridge.json";
    private final JsonWriter writer = new JsonWriter(destination);
    private final JsonReader reader = new JsonReader(destination);
    private Fridge fridge;

    // EFFECT: constructs and initializes the GUI for Fresher
    public GraphicalFresher() {
        super("My Fridge");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        layout = new SpringLayout();
        setLayout(layout);
        contentPane = (JPanel) getContentPane();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPane = new JPanel();

        fridge = new Fridge();

        addAddItemBtn();
        addRemoveItemBtn();
        addFindExpDate();
        addShowExpiredBtn();
        addSaveBtn();
        addLoadBtn();
        addItemList();
        add(buttonPane);
        setConstraints();

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECT: sets the constraints for the layout of the main window
    //         used by the constructor
    public void setConstraints() {
        layout.putConstraint(SpringLayout.EAST, contentPane, 5, SpringLayout.EAST, buttonPane);
        layout.putConstraint(SpringLayout.EAST, listScrollPane, 5, SpringLayout.EAST, contentPane);
        layout.putConstraint(SpringLayout.WEST, listScrollPane, 5, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.SOUTH, contentPane, 5, SpringLayout.SOUTH, buttonPane);
        layout.putConstraint(SpringLayout.NORTH, contentPane, 5, SpringLayout.NORTH, listScrollPane);
        layout.putConstraint(SpringLayout.NORTH, buttonPane, 5, SpringLayout.SOUTH, listScrollPane);
    }

    // MODIFIES: this, addItemBtn
    // EFFECT: adds a button that allows a user to add an item to the fridge
    public void addAddItemBtn() {
        JButton addItemBtn = new JButton("Add Item");
        addItemBtn.setActionCommand("add");
        addItemBtn.addActionListener(this);
        buttonPane.add(addItemBtn);
    }

    // MODIFIES: this
    // EFFECT: adds a button to remove an item from the fridge
    public void addRemoveItemBtn() {
        removeItemBtn = new JButton("Remove");
        removeItemBtn.setActionCommand("remove");
        removeItemBtn.addActionListener(this);
        buttonPane.add(removeItemBtn);
    }

    // MODIFIES: this, label, findExpDateBtn
    // EFFECT: adds a button and text field that allows a user to find the expiration
    //         date of an item by inputting its name and clicking the button
    public void addFindExpDate() {
        JLabel label = new JLabel("Find an Exp. Date: ", JLabel.TRAILING);
        findExpDateField = new JTextField(10);
        findExpDateField.addActionListener(this);
        label.setLabelFor(findExpDateField);
        buttonPane.add(label);
        buttonPane.add(findExpDateField);

        JButton findExpDateBtn = new JButton("Search");
        findExpDateBtn.setActionCommand("find");
        findExpDateBtn.addActionListener(this);
        buttonPane.add(findExpDateBtn);
    }

    // MODIFIES: this, showExpiredBtn
    // EFFECT: adds a button that will generate the items that are expired
    public void addShowExpiredBtn() {
        JButton showExpiredBtn = new JButton("Show Expired");
        showExpiredBtn.setActionCommand("expired");
        showExpiredBtn.addActionListener(this);
        buttonPane.add(showExpiredBtn);
    }

    // MODIFIES: this, saveBtn
    // EFFECT: adds a button to save the current fridge onto file
    public void addSaveBtn() {
        JButton saveBtn = new JButton("Save");
        saveBtn.setActionCommand("save");
        saveBtn.addActionListener(this);
        buttonPane.add(saveBtn);
    }

    // MODIFIES: this, loadBtn
    // EFFECT: adds a button to load the previous fridge
    public void addLoadBtn() {
        JButton loadBtn = new JButton("Load");
        loadBtn.setActionCommand("load");
        loadBtn.addActionListener(this);
        buttonPane.add(loadBtn);
    }

    // MODIFIES: this
    // EFFECT: creates a scrollable JList of items to represent the food in the fridge
    public void addItemList() {
        listItems = new DefaultListModel<>();

        list = new JList<>(listItems);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(10);

        listScrollPane = new JScrollPane(list);
        add(listScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: governs what actions are performed for different button events,
    //          called every time a button is pressed
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            addItemPopUp();
        } else if (e.getActionCommand().equals("submit")) {
            submitNewItem();
        } else if (e.getActionCommand().equals("remove")) {
            removeItem();
        } else if (e.getActionCommand().equals("find")) {
            findExpDate();
        } else if (e.getActionCommand().equals("expired")) {
            showExpired();
        } else if (e.getActionCommand().equals("save")) {
            saveList();
        } else if (e.getActionCommand().equals("load")) {
            loadList();
        }
    }

    // MODIFIES: this
    // EFFECT: finalizes the addition of a new item onto the fridge,
    //         used by actionPerformed()
    public void submitNewItem() {
        String name = t1.getText();
        String date = t2.getText();
        String cat = t3.getText();
        try {
            Item i = new Item(name, date, cat);
            listItems.addElement(i);
            fridge.addItem(i);
            list.setSelectedIndex(listItems.getSize() - 1);
        } catch (ParseException ex) {
            popUpMessage("Invalid Date", "That was an invalid date. Try again please!");
        }
    }

    // MODIFIES: this
    // EFFECT: removes the selected item off of the list
    public void removeItem() {
        int index = list.getSelectedIndex();
        String itemName = listItems.get(index).getName();
        fridge.removeItem(itemName);
        listItems.remove(index);
        int size = listItems.getSize();

        if (size == 0) {
            removeItemBtn.setEnabled(false);
        } else {
            if (index == size) {
                index--;
            }
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }
    }

    // MODIFIES: this, newFrame
    // EFFECT: new frame, allows user to input info regarding a new item to be added
    //         used by actionPerformed()
    public void addItemPopUp() {
        JFrame newFrame = new JFrame();
        SpringLayout lay = new SpringLayout();
        newFrame.setLayout(lay);
        newPanel = (JPanel) newFrame.getContentPane();
        labelsForAddItem();

        SpringUtilities.makeCompactGrid(newPanel, 4, 2, 5, 5, 5, 5);

        newFrame.pack();
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
    }

    // MODIFIES: this, l1, l2, l3, l4
    // EFFECT: creates labels and text fields for addItemPopUp()
    public void labelsForAddItem() {
        JLabel l1 = new JLabel("Item Name: ", JLabel.TRAILING);
        newPanel.add(l1);
        t1 = new JTextField(10);
        l1.setLabelFor(t1);
        newPanel.add(t1);

        JLabel l2 = new JLabel("Exp. Date (mm/dd/yyyy): ", JLabel.TRAILING);
        newPanel.add(l2);
        t2 = new JTextField(10);
        l2.setLabelFor(t2);
        newPanel.add(t2);

        JLabel l3 = new JLabel("Category: ", JLabel.TRAILING);
        newPanel.add(l3);
        t3 = new JTextField(10);
        l3.setLabelFor(t3);
        newPanel.add(t3);

        JLabel l4 = new JLabel("Click to add: ", JLabel.TRAILING);
        newPanel.add(l4);
        JButton submitBtn = new JButton("Add");
        submitBtn.addActionListener(this);
        submitBtn.setActionCommand("submit");
        l4.setLabelFor(submitBtn);
        newPanel.add(submitBtn);
    }

    // EFFECT: finds the expiration date of an item and tells the user, if it exists
    public void findExpDate() {
        String itemName = findExpDateField.getText();
        Date desiredDate  = this.fridge.findExpDate(itemName);

        if (desiredDate == null) {
            popUpMessage("Error", "An item with that name could not be found");
        } else {
            String strDate = dateToStr(desiredDate);
            popUpMessage("Expiration Date", "Your " + itemName
                    + "'s expiration date is " + strDate + ".");
        }
    }

    // MODIFIES: frame, listPane
    // EFFECT: shows the expired items
    public void showExpired() {
        JFrame frame = new JFrame("Expired Items");
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));

        ArrayList<Item> expired = this.fridge.viewExpiredItems();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);
        for (Item i: expired) {
            listModel.addElement(i.getName());
        }
        JScrollPane scroll = new JScrollPane(list);
        finalizeExpired(expired, scroll, listPane, frame);
    }

    // MODIFIES: frame, listPane
    // EFFECT: organizes the expired items window
    public void finalizeExpired(ArrayList<Item> expired,
                                JScrollPane scroll, JPanel listPane, JFrame frame) {
        JLabel image;
        if (expired.size() == 0) {
            image = new JLabel("No expired items - now that's what I like to see!",
                    new ImageIcon("data/gordonHappy.png"), JLabel.LEFT);
        } else {
            image = new JLabel(expired.size()
                    + " expired items - go fix this right now you panini head!",
                    new ImageIcon("data/gordonSad.png"), JLabel.LEFT);
        }

        listPane.add(scroll);
        listPane.add(image);

        frame.add(listPane);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: Saves the current fridge into a data file in JSON format.
    public void saveList() {
        try {
            writer.open();
            writer.write(this.fridge);
            writer.close();
            popUpMessage("Success", "Your fridge was saved :) ");
        } catch (IOException e) {
            popUpMessage("Error", "Your fridge could not be saved :( ");
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads the data from a data file into the current fridge.
    public void loadList() {
        try {
            this.fridge = reader.read();
            listItems = new DefaultListModel<>();

            for (Item i: this.fridge.getAllItems()) {
                listItems.addElement(i);
            }

            this.list.setModel(listItems);
            popUpMessage("Success", "Your fridge has successfully been loaded from " + destination);
        } catch (IOException e) {
            popUpMessage("Error", "The fridge could not be loaded :,)");
        } catch (ParseException p) {
            popUpMessage("Error", "There is at least 1 item in the fridge whose date could not be read.");
        }
    }

    // MODIFIES: window
    // EFFECT: A pop-up window that shows the user a given message
    public void popUpMessage(String main, String msg) {
        JFrame window = new JFrame(main);
        JLabel message = new JLabel(msg);
        window.add(message);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}





