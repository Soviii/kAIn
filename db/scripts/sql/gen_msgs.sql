-- Messages for recipe #1
INSERT INTO Messages (recipe_id, sender, message_text, timestamp) VALUES
(1, 'user', 'How do I start making Spaghetti Surprise?', NOW()),
(1, 'ai', 'Begin by boiling water and adding spaghetti.', NOW()),
(1, 'user', 'How long should I boil the pasta?', NOW()),
(1, 'ai', 'Boil for about 8-10 minutes.', NOW()),
(1, 'user', 'What sauce should I use?', NOW()),

(1, 'ai', 'Use tomato sauce for a classic flavor.', NOW()),
(1, 'user', 'Can I add garlic?', NOW()),
(1, 'ai', 'Absolutely, sauté garlic in olive oil.', NOW()),
(1, 'user', 'Should I use fresh basil?', NOW()),
(1, 'ai', 'Yes, fresh basil adds great aroma.', NOW()),

(1, 'user', 'How do I combine everything?', NOW()),
(1, 'ai', 'Mix pasta with sauce and garnish with basil.', NOW()),
(1, 'user', 'Can I use whole wheat spaghetti?', NOW()),
(1, 'ai', 'Yes, whole wheat is a healthy option.', NOW()),
(1, 'user', 'Is parmesan cheese necessary?', NOW());

-- Summaries for recipe #1
INSERT INTO Summaries (recipe_id, summary_text, created_at) VALUES
(1, 'User asked about starting Spaghetti Surprise, boiling pasta, and choosing sauce.', NOW()),
(1, 'Conversation covered adding garlic, using olive oil, and fresh basil.', NOW()),
(1, 'Discussed combining ingredients, using whole wheat pasta, and cheese options.', NOW());

-- Messages for recipe #2
INSERT INTO messages (recipe_id, sender, message_text, timestamp) VALUES
(2, 'user', 'How do I start making Chicken Fiesta?', NOW()),
(2, 'ai', 'Begin by seasoning the chicken breasts.', NOW()),
(2, 'user', 'What spices should I use?', NOW()),
(2, 'ai', 'Paprika and salt are classic choices.', NOW()),
(2, 'user', 'Should I marinate the chicken?', NOW()),

(2, 'ai', 'Marinating for 30 minutes enhances flavor.', NOW()),
(2, 'user', 'How do I cook the chicken?', NOW()),
(2, 'ai', 'Sauté the chicken until browned on both sides.', NOW()),
(2, 'user', 'When do I add the vegetables?', NOW()),
(2, 'ai', 'Add bell pepper and onion after browning the chicken.', NOW()),

(2, 'user', 'How long should I cook the vegetables?', NOW()),
(2, 'ai', 'Cook until they are soft, about 5 minutes.', NOW()),
(2, 'user', 'Can I use other vegetables?', NOW()),
(2, 'ai', 'Yes, you can add zucchini or corn.', NOW()),
(2, 'user', 'What should I serve with Chicken Fiesta?', NOW());

-- Summaries for recipe #2
INSERT INTO summaries (recipe_id, summary_text, created_at) VALUES
(2, 'User asked about starting Chicken Fiesta, seasoning, spices, and marinating the chicken.', NOW()),
(2, 'Discussion included cooking methods, sautéing chicken, and when to add bell pepper and onion.', NOW()),
(2, 'Talked about cooking vegetables, using alternatives like zucchini or corn, and serving suggestions.', NOW());

-- Messages for recipe #3
INSERT INTO messages (recipe_id, sender, message_text, timestamp) VALUES
(3, 'user', 'What is the first step for Veggie Delight?', NOW()),
(3, 'ai', 'Chop all vegetables and tofu before starting.', NOW()),
(3, 'user', 'Should I peel the carrots?', NOW()),
(3, 'ai', 'Peeling is optional, but it makes them more tender.', NOW()),
(3, 'user', 'How do I prepare the tofu?', NOW()),

(3, 'ai', 'Cut tofu into cubes and pat dry for better texture.', NOW()),
(3, 'user', 'What oil should I use for stir-frying?', NOW()),
(3, 'ai', 'Use a neutral oil like canola or vegetable oil.', NOW()),
(3, 'user', 'When do I add the ginger?', NOW()),
(3, 'ai', 'Add ginger to the oil before the vegetables.', NOW()),

(3, 'user', 'How long should I stir-fry the veggies?', NOW()),
(3, 'ai', 'About 5-7 minutes until just tender.', NOW()),
(3, 'user', 'When do I add soy sauce?', NOW()),
(3, 'ai', 'Add soy sauce after the veggies are cooked.', NOW()),
(3, 'user', 'Can I serve this with rice?', NOW());

-- Summaries for recipe #3
INSERT INTO summaries (recipe_id, summary_text, created_at) VALUES
(3, 'User inquired about the first step for Veggie Delight, carrot preparation, and tofu handling.', NOW()),
(3, 'Conversation covered oil selection, adding ginger, and timing for stir-frying vegetables.', NOW()),
(3, 'Discussed adding soy sauce, final cooking steps, and serving the dish with rice.', NOW());
