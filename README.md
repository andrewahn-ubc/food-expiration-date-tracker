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
   

### Citations
- A third-party library named SpringUtilities was downloaded 
  (with permission from the source) into the UI folder. It was lightly
  modified to pass the 210 checkstyle policy as well as to remove unused methods.

### For the Future
If I had more time, I would refactor the methods in the
GraphicalFresher class that **add buttons to the main frame** 
in the graphical UI. There are currently 5 such methods in
the class, and they all perform similar functions. A major 
benefit to this refactoring would be simplification. It would
remove redundancy from the class and make the class easier
to navigate. In addition, it would decrease the time it takes
for the program to run since the function of 5 methods would
be condensed into mostly 1 method. 
