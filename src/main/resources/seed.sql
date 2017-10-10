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
    VALUES('Steamed broccoli', 'images/100x100.png');
INSERT INTO recipe(dish, img)
    VALUES('Steamed corn', 'images/100x100.png');
INSERT INTO recipe(dish, img)
    VALUES('Steamed carrot', 'images/100x100.png');

/**
 * Inventory
 */
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Broccoli', 1, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'coco'), 'Corn', 10, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'radhika'), 'Broccoli', 3, 3);
INSERT INTO inventory(account_id, item, quantity, recorder_point)
    VALUES((SELECT account_id FROM account WHERE username = 'radhika'), 'Carrot', 5, 3);

/**
 * Ingredient
 */
INSERT INTO ingredient(recipe_id, inventory_id, quantity)
    VALUES((SELECT recipe_id FROM recipe WHERE dish = 'Steamed broccoli'), (SELECT inventory_id FROM inventory WHERE item = 'Broccoli' LIMIT 1), 1);
INSERT INTO ingredient(recipe_id, inventory_id, quantity)
    VALUES((SELECT recipe_id FROM recipe WHERE dish = 'Steamed corn'), (SELECT inventory_id FROM inventory WHERE item = 'Corn' LIMIT 1), 1);
INSERT INTO ingredient(recipe_id, inventory_id, quantity)
    VALUES((SELECT recipe_id FROM recipe WHERE dish = 'Steamed carrot'), (SELECT inventory_id FROM inventory WHERE item = 'Carrot' LIMIT 1), 1);