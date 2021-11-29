INSERT INTO role(role_id, name) VALUES(1,'admin'), (2, 'moderator'), (3,'user');

INSERT INTO ingridient_category(ingridient_category_id, name) VALUES(1,'fruit'), (2,'alcohol'), (3,'juice') , (4,'lemonade'), (5,'water');

INSERT INTO kitchenware_category(kitchenware_category_id, name) VALUES(1,'glass'), (2,'cutlery');

INSERT INTO kitchenware_category(kitchenware_category_id, name) VALUES(3,'jar');

INSERT INTO dish_category( dish_category_id, name) VALUES(1, 'alcoholic'), (2, 'non-alcoholic');

INSERT INTO ingridient(ingridient_id, name,  ingridient_category_id, is_active) VALUES(1, 'orange',  '1', true) ;
INSERT INTO ingridient(ingridient_id, name,  ingridient_category_id, is_active) VALUES(2, 'lemon',  '1', true) ;
INSERT INTO ingridient(ingridient_id, name,  ingridient_category_id, is_active) VALUES(3, 'water',  '5', true) ;

INSERT INTO kitchenware(kitchenware_id, name, kitchenware_category_id, is_active) VALUES(1, 'wineglass', 1, true);
INSERT INTO kitchenware(kitchenware_id, name, kitchenware_category_id, is_active) VALUES(2, 'jug', 3, true);
INSERT INTO kitchenware(kitchenware_id, name, kitchenware_category_id, is_active) VALUES(3, 'knife', 2, true);

INSERT INTO dish(name, description,  dish_category_id, receipt, likes, is_active) VALUES('water with lemon', 'water with lemon', 2, 'Squeeze lemon juice into a glass.', 0, true) ;

INSERT INTO ingridient_dish(dish_id, ingridient_id, amount, measure_type) VALUES(1, 2, 1, 'piece' ) ;
INSERT INTO ingridient_dish(dish_id, ingridient_id, amount, measure_type) VALUES(1, 3, 500, 'ml' ) ;

INSERT INTO kitchenware_dish(dish_id, kitchenware_id) VALUES(1,2 ) ;
INSERT INTO kitchenware_dish(dish_id, kitchenware_id) VALUES(1,3 ) ;





