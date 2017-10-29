/**
 * Author:  Coco
 * Created: Oct 7, 2017
 */

CREATE TABLE IF NOT EXISTS account (
    account_id TINYINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(128) UNIQUE,
    password VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS recipe (
    recipe_id TINYINT AUTO_INCREMENT PRIMARY KEY,
    dish VARCHAR(128) UNIQUE,
    img VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS grocery (
    grocery_id TINYINT AUTO_INCREMENT PRIMARY KEY,
    item VARCHAR(128) UNIQUE
);

CREATE TABLE IF NOT EXISTS inventory (
    inventory_id TINYINT AUTO_INCREMENT PRIMARY KEY,
    account_id TINYINT,
    grocery_id TINYINT,
    quantity INT,
    recorder_point INT,
    FOREIGN KEY (account_id)
        REFERENCES account(account_id),
    FOREIGN KEY (grocery_id)
        REFERENCES grocery(grocery_id)
);

CREATE TABLE IF NOT EXISTS ingredient (
    recipe_id TINYINT,
    grocery_id TINYINT,
    quantity INT,
    FOREIGN KEY (recipe_id)
        REFERENCES recipe(recipe_id),
    FOREIGN KEY (grocery_id)
        REFERENCES grocery(grocery_id)
);
