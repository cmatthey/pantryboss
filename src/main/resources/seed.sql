/* 
 * Copywrite(c) 2017 Coco Matthey
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software  * 
 */
/**
 * Author:  Coco
 * Created: Oct 8, 2017
 */

/**
 * Account
 */
INSERT INTO account(username, password)
    VALUES('coco', 'notpasswod');
INSERT INTO account(username, password)
    VALUES('radhika', 'notpasswod');

/**
 * Recipe
 */
INSERT INTO recipe(dish, img)
    VALUES('Chopped Apple', 'images/apple.jpg');
INSERT INTO recipe(dish, img)
    VALUES('Blueberry Waffle', 'images/blueberrywaffle.jpeg');
INSERT INTO recipe(dish, img)
    VALUES('Carrot Juice', 'images/carrotjuice.jpeg');
INSERT INTO recipe(dish, img)
    VALUES('Fries', 'images/fries.jpg');
INSERT INTO recipe(dish, img)
    VALUES('Hot Chocolate with Marshmallow', 'images/marshmallowhotchocolate.jpg');
INSERT INTO recipe(dish, img)
    VALUES('Spaghetti with Bolognese Sauce', 'images/spaghettibolognese.jpg');
INSERT INTO recipe(dish, img)
    VALUES('Steamed Corn', 'images/steamedcorn.jpg');
INSERT INTO recipe(dish, img)
    VALUES('Triple Berries Fruit Salad', 'images/tripleberriesfruitsalad.jpg');

/**
 * Inventory
 */
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Apple', 1, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Blueberry', 3, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Raspberry', 5, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Strawberry', 7, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Flour', 9, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Sugar', 11, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Baking Powder', 13, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Cocoa Powder', 15, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Marshmallow', 17, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Milk', 19, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Canola Oil', 21, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Corn', 10, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Onion', 10, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'radhika'), 'Broccoli', 3, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'radhika'), 'Carrot', 5, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Tomatoe', 1, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Ground Beef', 1, 3);

/**
 * Ingredient
 */
INSERT INTO ingredient(recipe_id, inventory_id, quantity)
    VALUES((SELECT recipe_id FROM recipe WHERE dish = 'Steamed broccoli'), (SELECT inventory_id FROM inventory WHERE item = 'Broccoli' LIMIT 1), 1);
INSERT INTO ingredient(recipe_id, inventory_id, quantity)
    VALUES((SELECT recipe_id FROM recipe WHERE dish = 'Steamed corn'), (SELECT inventory_id FROM inventory WHERE item = 'Corn' LIMIT 1), 1);
INSERT INTO ingredient(recipe_id, inventory_id, quantity)
    VALUES((SELECT recipe_id FROM recipe WHERE dish = 'Steamed carrot'), (SELECT inventory_id FROM inventory WHERE item = 'Carrot' LIMIT 1), 1);