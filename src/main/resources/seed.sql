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
    VALUES('coco', 'replacewithyourpassword');
INSERT INTO account(username, password)
    VALUES('radhika', 'replacewithyourpassword');

/**
 * Recipe
 */
INSERT INTO recipe(dish, img)
    VALUES('Chopped Apple', '/images/apple.jpeg');
INSERT INTO recipe(dish, img)
    VALUES('Blueberry Waffle', '/images/blueberrywaffle.jpeg');
INSERT INTO recipe(dish, img)
    VALUES('Carrot Juice', '/images/carrotjuice.jpeg');
INSERT INTO recipe(dish, img)
    VALUES('Fries', '/images/fries.jpeg');
INSERT INTO recipe(dish, img)
    VALUES('Hot Chocolate with Marshmallow', '/images/marshmallowhotchocolate.jpeg');
INSERT INTO recipe(dish, img)
    VALUES('Spaghetti with Bolognese Sauce', '/images/spaghettibolognese.jpeg');
INSERT INTO recipe(dish, img)
    VALUES('Steamed Corn', '/images/steamedcorn.jpeg');
INSERT INTO recipe(dish, img)
    VALUES('Triple Berries Fruit Salad', '/images/tripleberriesfruitsalad.jpeg');

/**
 * Grocery
 */
INSERT INTO grocery(item)
    VALUES('Apple');
INSERT INTO grocery(item)
    VALUES('Blueberry');
INSERT INTO grocery(item)
    VALUES('Strawberry');
INSERT INTO grocery(item)
    VALUES('Raspberry');
INSERT INTO grocery(item)
    VALUES('Milk');
INSERT INTO grocery(item)
    VALUES('Broccoli');
INSERT INTO grocery(item)
    VALUES('Carrot');
INSERT INTO grocery(item)
    VALUES('Corn');
INSERT INTO grocery(item)
    VALUES('Onion');
INSERT INTO grocery(item)
    VALUES('Tomatoe');
INSERT INTO grocery(item)
    VALUES('Ground Beef');
INSERT INTO grocery(item)
    VALUES('Canola Oil');
INSERT INTO grocery(item)
    VALUES('Baking Powder');
INSERT INTO grocery(item)
    VALUES('Cocoa Powder');
INSERT INTO grocery(item)
    VALUES('Flour');
INSERT INTO grocery(item)
    VALUES('Marshmallow');
INSERT INTO grocery(item)
    VALUES('Sugar');

/**
 * Inventory
 */
INSERT INTO inventory(account_id, grocery_id, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), (SELECT grocery_id FROM grocery WHERE item = 'Apple'), 1, 3);
INSERT INTO inventory(account_id, grocery_id, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), (SELECT grocery_id FROM grocery WHERE item = 'Blueberry'), 1, 3);
INSERT INTO inventory(account_id, grocery_id, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), (SELECT grocery_id FROM grocery WHERE item = 'Raspberry'), 1, 3);
INSERT INTO inventory(account_id, grocery_id, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), (SELECT grocery_id FROM grocery WHERE item = 'Strawberry'), 1, 3);
INSERT INTO inventory(account_id, grocery_id, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), (SELECT grocery_id FROM grocery WHERE item = 'Milk'), 1, 3);
INSERT INTO inventory(account_id, grocery_id, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), (SELECT grocery_id FROM grocery WHERE item = 'Broccoli'), 1, 3);
INSERT INTO inventory(account_id, grocery_id, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), (SELECT grocery_id FROM grocery WHERE item = 'Carrot'), 1, 3);
INSERT INTO inventory(account_id, grocery_id, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), (SELECT grocery_id FROM grocery WHERE item = 'Corn'), 1, 3);


/**
 * Ingredient
 */
INSERT INTO ingredient(recipe_id, grocery_id, quantity)
    VALUES((SELECT recipe_id FROM recipe WHERE dish = 'Chopped Apple'), (SELECT grocery_id FROM grocery WHERE item = 'Apple'), 1);
INSERT INTO ingredient(recipe_id, grocery_id, quantity)
    VALUES((SELECT recipe_id FROM recipe WHERE dish = 'Blueberry Waffle'), (SELECT grocery_id FROM grocery WHERE item = 'Blueberry Waffle'), 1);
INSERT INTO ingredient(recipe_id, grocery_id, quantity)
    VALUES((SELECT recipe_id FROM recipe WHERE dish = 'Steamed carrot'), (SELECT grocery_id FROM grocery WHERE item = 'Steamed carrot'), 1);