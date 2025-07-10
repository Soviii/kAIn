-- Insert dummy recipes
INSERT INTO Recipes (id, user_id, title, created_at, updated_at) VALUES
  (1, 1, 'Spaghetti Surprise', NOW(), NOW()),
  (2, 1, 'Chicken Fiesta', NOW(), NOW()),
  (3, 2, 'Veggie Delight', NOW(), NOW());

-- Insert dummy ingredients for each recipe (5 per recipe)
INSERT INTO Ingredients (id, recipe_id, name, quantity, unit) VALUES
  (1, 1, 'Spaghetti', 200, 'grams'),
  (2, 1, 'Tomato Sauce', 1, 'cup'),
  (3, 1, 'Garlic', 2, 'cloves'),
  (4, 1, 'Olive Oil', 2, 'tablespoons'),
  (5, 1, 'Basil', 5, 'leaves'),

  (6, 2, 'Chicken Breast', 2, 'pieces'),
  (7, 2, 'Bell Pepper', 1, 'whole'),
  (8, 2, 'Onion', 1, 'whole'),
  (9, 2, 'Paprika', 1, 'teaspoon'),
  (10, 2, 'Salt', 0.5, 'teaspoon'),

  (11, 3, 'Broccoli', 1, 'cup'),
  (12, 3, 'Carrot', 2, 'whole'),
  (13, 3, 'Tofu', 100, 'grams'),
  (14, 3, 'Soy Sauce', 2, 'tablespoons'),
  (15, 3, 'Ginger', 1, 'teaspoon');

-- Insert dummy steps for each recipe (5 per recipe)
INSERT INTO Steps (id, recipe_id, step_number, instruction) VALUES
  (1, 1, 1, 'Boil spaghetti in salted water.'),
  (2, 1, 2, 'Heat olive oil in a pan.'),
  (3, 1, 3, 'Add garlic and sauté until fragrant.'),
  (4, 1, 4, 'Pour in tomato sauce and simmer.'),
  (5, 1, 5, 'Mix in spaghetti and garnish with basil.'),

  (6, 2, 1, 'Season chicken with salt and paprika.'),
  (7, 2, 2, 'Sauté chicken until browned.'),
  (8, 2, 3, 'Add chopped onion and bell pepper.'),
  (9, 2, 4, 'Cook until vegetables are soft.'),
  (10, 2, 5, 'Serve hot with your favorite side.'),

  (11, 3, 1, 'Chop all vegetables and tofu.'),
  (12, 3, 2, 'Heat oil and add ginger.'),
  (13, 3, 3, 'Add broccoli and carrot, stir-fry.'),
  (14, 3, 4, 'Add tofu and soy sauce, cook for 5 minutes.'),
  (15, 3, 5, 'Serve warm as a healthy meal.');
