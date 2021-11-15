INSERT INTO role(name) VALUES('admin'), ('moderator'), ('user');

INSERT INTO ingridient_category(name) VALUES('fruit'), ('alcohol'), ('juice') , ('lemonade'), ('water');

INSERT INTO kitchenware_category(name) VALUES('glass'), ('cutlery');

INSERT INTO kitchenware_category(name) VALUES('jar');

INSERT INTO dish_category(name) VALUES('alcoholic'), ('non-alcoholic');

INSERT INTO ingridient(name,  ingridient_category_id) VALUES('orange',  '1') ;
INSERT INTO ingridient(name,  ingridient_category_id) VALUES('lemon',  '1') ;
INSERT INTO ingridient(name,  ingridient_category_id) VALUES('water',  '5') ;

INSERT INTO kitchenware(name, kitchenware_category_id) VALUES('wineglass', 1);
INSERT INTO kitchenware(name, kitchenware_category_id) VALUES('jug', 3);
INSERT INTO kitchenware(name, kitchenware_category_id) VALUES('knife', 2);

INSERT INTO dish(name, description,  dish_category_id, receipt, volume) VALUES('water with lemon', 'water with lemon', 2, 'Squeeze lemon juice into a glass.', 0.5) ;

INSERT INTO ingridient_dish(dish_id, ingridient_id, amount, measure_type) VALUES(1, 2, 1, 'piece' ) ;
INSERT INTO ingridient_dish(dish_id, ingridient_id, amount, measure_type) VALUES(1, 3, 500, 'ml' ) ;

INSERT INTO kitchenware_dish(dish_id, kitchenware_id) VALUES(1,2 ) ;
INSERT INTO kitchenware_dish(dish_id, kitchenware_id) VALUES(1,3 ) ;





