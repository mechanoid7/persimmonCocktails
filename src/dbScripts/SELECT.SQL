1) коктейль и список его ингидиентов( повторяються) 
SELECT  d.name, i.name , ing_d.amount, ing_d.measure_type
FROM dish AS d, ingridient as i, INGRIDIENT_DISH as ing_d
INNER JOIN dish ON dish.dish_id = ing_d.dish_id
INNER JOIN ingridient ON ingridient.ingridient_id = ing_d.ingridient_id;
