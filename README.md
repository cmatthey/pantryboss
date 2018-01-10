Final project report
Recipe matcher and pantry management app
Description
Pantry Boss is a recipe matcher and pantry management app. It helps users find recipes by ingredient/s. It matches ingredients from a recipe and inventory from a user and only shows recipes available to the users based on the ingredients the user has in his/her pantry. It also tracks pantry inventory. Once a user selects and confirms a recipe, the pantry inventory will be automatically adjusted to reflect ingredient usage. A user can edit the pantry inventory after the pantry has been restocked with new ingredients.  This is a multi-user application. A user registers to create an account, then logs in to use the application.
Features
Find recipes by ingredient/s
View and manage current pantry inventory
Supports multiple users
Persistent storage by MySQL
Design
This project uses Model–view–controller paradigm. It uses JDBC, MySQL and Java Swing.

UI Design
Log In and Sign up dialog

Log In error message

Recipe matcher
Recipes available to a user are filtered based on pantry inventory.



Inventory

Menu
 
          
Database Schema
UML Activity diagram
UML Sequence Diagram
Log in and Sign up flow

Recipe picker and Restock inventory flow

-----UML Class diagram

Implementation
The source code can be found here at github
References
The Java™ Tutorials
A Tutorial For MySQL and JDBC, Radhika Grover
Credit
https://www.pexels.com/search/food/
https://www.flaticon.com/free-icon/
