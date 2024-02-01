# Fresher
\
Fresher is an application that tracks the expiration dates
of items your fridge so that **you** don't have to remember all 
of them. Fresher would allow you to have convenient access to:

- all items in your fridge, separated by category
- items that have expired
- search an item's name to see when it expires


Fresher could be useful for anyone that has to keep track of 
food items, whether that be a restaurant with hundreds of 
ingredients or a university student with a mini-fridge. 
I was personally interested in this project because there 
have been too many instances where I started cooking a meal 
only to realize _half-way in_ that I could not finish the 
recipe because one of the ingredients was expired. With an 
application like Fresher, I could quickly check whether I can
follow a recipe or not based on whether I have all the 
necessary ingredients and whether they are still 
safe to eat.

### User Stories
- As a user, I want to be able to add a food item to the fridge
- As a user, I want to be able to delete a food item from the fridge
- As a user, I want to be able to view the list of expired items
- As a user, I want to be able to view the list of all items
- As a user, I want to be able to specify the category for each food item, such as "dairy" for milk.
- As a user, I want to be able to search for a food item in my fridge and see its expiration date
- As a user, I want to have the option of saving the entire inventory in the main menu.
- As a user, I want to have the ability to reload my fridge when I open the application.

# Instructions for Grader
1. You can generate the first required action related to adding Xs 
   to a Y by clicking the "Add Item" button, after which you 
   will be prompted to fill out information about the 
   food item you want to add. Go ahead and add two items: 1 that has
   expired and 1 that hasn't.
2. You can generate the second required action related to adding
   Xs to a Y by clicking the "Show Expired" button, 
   upon which a new window will pop up. It should display the items in
   your fridge with expiration dates prior to the current date.
3. You can locate my visual component by having completed user story #2. You
   should have seen a new window displaying the expired items along 
   with an image. Notice what image pops up when you click
   "Show Expired" with no expired items!
4. You can save the state of my application by clicking the "Save" button.
5. You can reload the state of my application by clicking the "Load" button.
6. EXTRA: to remove an item, click on the item with your cursor and
          click the "Remove" button. Want to search for the expiration 
          date of a given item? Type the item's name into the text field
          and click the "Search" button.
   

### Citations
- A third-party library named SpringUtilities was downloaded 
  (with permission from the source) into the UI folder. It was lightly
  modified to pass the 210 checkstyle policy as well as to remove unused methods.

### Suppress Checkstyle
A checkstyle warning was suppressed in the SpringUtilities class as a method was too long.
It was written by a third party, so I felt that I should not modify it myself. 
I'm sorry for not checking with you prior to using it!

### Phase 4: Task 2
Added Beef to the fridge! \
Displayed the 1 expired item(s) in the fridge.\
Removed Beef from the fridge!\

### Phase 4: Task 3
If I had more time, I would refactor the methods in the
GraphicalFresher class that **add buttons to the main frame** 
in the graphical UI. There are currently 5 such methods in
the class, and they all perform similar functions. A major 
benefit to this refactoring would be simplification. It would
remove redundancy from the class and make the class easier
to navigate. In addition, it would decrease the time it takes
for the program to run since the function of 5 methods would
be condensed into mostly 1 method. 
