/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    dish VARCHAR(128),
    img VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS inventory (
    inventory_id TINYINT AUTO_INCREMENT PRIMARY KEY,
    account_id TINYINT,
    item VARCHAR(128),
    quantity INT,
    recorder_point INT,
    FOREIGN KEY (account_id)
        REFERENCES account(account_id)
);

CREATE TABLE IF NOT EXISTS ingredient (
    recipe_id TINYINT,
    inventory_id TINYINT,
    quantity INT,
    FOREIGN KEY (recipe_id)
        REFERENCES recipe(recipe_id),
    FOREIGN KEY (inventory_id)
        REFERENCES inventory(inventory_id)
);
