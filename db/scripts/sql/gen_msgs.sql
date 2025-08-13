-- 30 messages for recipe 1
INSERT INTO Messages (recipe_id, sender, message_text, timestamp) VALUES
(1, 'user', 'Can I add mushrooms to Spaghetti Surprise?', NOW()),
(1, 'ai', 'Yes, sautéed mushrooms go well with the sauce.', NOW()),
(1, 'user', 'What kind of mushrooms do you recommend?', NOW()),
(1, 'ai', 'Cremini or button mushrooms are great choices.', NOW()),
(1, 'user', 'Should I cook them before adding?', NOW()),
(1, 'ai', 'Yes, cook until golden brown first.', NOW()),

(1, 'user', 'Can I make the sauce spicier?', NOW()),
(1, 'ai', 'Add red pepper flakes or chili for heat.', NOW()),
(1, 'user', 'Is butter okay instead of olive oil?', NOW()),
(1, 'ai', 'Butter adds richness, so it works.', NOW()),
(1, 'user', 'What wine pairs best with this dish?', NOW()),
(1, 'ai', 'Try a light red like Chianti or Pinot Noir.', NOW()),

(1, 'user', 'Can I freeze leftovers?', NOW()),
(1, 'ai', 'Yes, it freezes well for up to a month.', NOW()),
(1, 'user', 'How do I reheat frozen spaghetti?', NOW()),
(1, 'ai', 'Reheat gently in a pan or microwave.', NOW()),
(1, 'user', 'Should I add cheese before or after reheating?', NOW()),
(1, 'ai', 'Add cheese after reheating for best flavor.', NOW()),

(1, 'user', 'What dessert pairs well with Spaghetti Surprise?', NOW()),
(1, 'ai', 'Tiramisu is a classic Italian dessert to try.', NOW()),
(1, 'user', 'Can I substitute tomato sauce with pesto?', NOW()),
(1, 'ai', 'Yes, pesto works for a fresh flavor twist.', NOW()),
(1, 'user', 'How long should I boil whole wheat spaghetti?', NOW()),
(1, 'ai', 'Boil for 10-12 minutes, a bit longer than regular.', NOW()),

(1, 'user', 'Is garlic necessary?', NOW()),
(1, 'ai', 'It adds great aroma but is optional.', NOW()),
(1, 'user', 'Can I add olives?', NOW()),
(1, 'ai', 'Yes, black or green olives add nice tanginess.', NOW()),
(1, 'user', 'How to keep pasta from sticking?', NOW()),
(1, 'ai', 'Stir often and add a bit of oil to the water.', NOW());

-- 6 summaries for recipe 1 (1 per 5 messages)
INSERT INTO Summaries (recipe_id, summary_text, created_at) VALUES
(1, 'User asked about adding and cooking mushrooms; AI recommended sautéing cremini or button mushrooms.', NOW()),
(1, 'Discussion on spicing the sauce, using butter, and wine pairing suggestions.', NOW()),
(1, 'Talked about freezing leftovers, reheating tips, and when to add cheese.', NOW()),
(1, 'User asked about dessert pairing and sauce substitutions like pesto.', NOW()),
(1, 'Covered boiling time for whole wheat pasta and the importance of garlic.', NOW()),
(1, 'User inquired about adding olives and preventing pasta from sticking; AI provided tips.', NOW());

-- 30 messages for recipe 2
INSERT INTO Messages (recipe_id, sender, message_text, timestamp) VALUES
(2, 'user', 'Can I use chicken thighs instead of breasts?', NOW()),
(2, 'ai', 'Yes, thighs are juicier and more flavorful.', NOW()),
(2, 'user', 'Should I remove the skin?', NOW()),
(2, 'ai', 'Removing skin makes it lighter, but it adds crispiness if left on.', NOW()),
(2, 'user', 'Is lemon juice a good marinade addition?', NOW()),
(2, 'ai', 'Yes, lemon adds bright tanginess.', NOW()),

(2, 'user', 'Can I grill the chicken?', NOW()),
(2, 'ai', 'Grilling adds a smoky flavor and works well.', NOW()),
(2, 'user', 'How long should I marinate the chicken?', NOW()),
(2, 'ai', '30 minutes to 2 hours is ideal.', NOW()),
(2, 'user', 'When should I add vegetables?', NOW()),
(2, 'ai', 'Add bell peppers and onions after browning the chicken.', NOW()),

(2, 'user', 'Can I use zucchini or corn instead?', NOW()),
(2, 'ai', 'Yes, both are great alternatives.', NOW()),
(2, 'user', 'How long to cook the vegetables?', NOW()),
(2, 'ai', 'Cook about 5 minutes until tender.', NOW()),
(2, 'user', 'What sides go well with Chicken Fiesta?', NOW()),
(2, 'ai', 'Rice, tortillas, or a fresh salad work nicely.', NOW()),

(2, 'user', 'Can I make this dish dairy-free?', NOW()),
(2, 'ai', 'Yes, it contains no dairy ingredients.', NOW()),
(2, 'user', 'How to store leftovers?', NOW()),
(2, 'ai', 'Use airtight containers in the fridge.', NOW()),
(2, 'user', 'Can I reheat without drying it out?', NOW()),
(2, 'ai', 'Reheat gently with a splash of water.', NOW()),

(2, 'user', 'What drinks pair well with this?', NOW()),
(2, 'ai', 'Cold beer or iced tea are good choices.', NOW()),
(2, 'user', 'Can I add spice?', NOW()),
(2, 'ai', 'Add chili powder or hot sauce to taste.', NOW()),
(2, 'user', 'Any tips for making it healthier?', NOW()),
(2, 'ai', 'Use less oil and add more vegetables.', NOW());

-- 6 summaries for recipe 2 (1 per 5 messages)
INSERT INTO Summaries (recipe_id, summary_text, created_at) VALUES
(2, 'User asked about using chicken thighs, skin removal, and lemon marinade.', NOW()),
(2, 'Discussed grilling, marinating times, and when to add vegetables.', NOW()),
(2, 'Talked about vegetable substitutions, cooking times, and side dishes.', NOW()),
(2, 'Covered making it dairy-free, storing leftovers, and reheating tips.', NOW()),
(2, 'User asked about drink pairings, adding spice, and health tips.', NOW()),
(2, 'AI recommended reducing oil and increasing vegetables for a healthier meal.', NOW());

-- 30 messages for recipe 3
INSERT INTO Messages (recipe_id, sender, message_text, timestamp) VALUES
(3, 'user', 'Can I add broccoli to Veggie Delight?', NOW()),
(3, 'ai', 'Yes, broccoli is a great addition.', NOW()),
(3, 'user', 'What about snap peas?', NOW()),
(3, 'ai', 'Snap peas add a nice crunch.', NOW()),
(3, 'user', 'Should I peel the carrots?', NOW()),
(3, 'ai', 'Peeling is optional but makes carrots more tender.', NOW()),

(3, 'user', 'How to prepare tofu?', NOW()),
(3, 'ai', 'Cut into cubes and pat dry.', NOW()),
(3, 'user', 'What oil is best for stir-frying?', NOW()),
(3, 'ai', 'Use neutral oil like canola or vegetable oil.', NOW()),
(3, 'user', 'When do I add ginger?', NOW()),
(3, 'ai', 'Add ginger to hot oil before vegetables.', NOW()),

(3, 'user', 'How long to stir-fry veggies?', NOW()),
(3, 'ai', '5-7 minutes until tender but crisp.', NOW()),
(3, 'user', 'When to add soy sauce?', NOW()),
(3, 'ai', 'Add soy sauce after vegetables are cooked.', NOW()),
(3, 'user', 'Can I make it spicy?', NOW()),
(3, 'ai', 'Add chili flakes or sriracha for heat.', NOW()),

(3, 'user', 'Is sesame oil good for finishing?', NOW()),
(3, 'ai', 'Yes, drizzle at the end for flavor.', NOW()),
(3, 'user', 'Can I make it gluten-free?', NOW()),
(3, 'ai', 'Use tamari instead of soy sauce.', NOW()),
(3, 'user', 'Can I add cashews?', NOW()),
(3, 'ai', 'Toasted cashews add nice crunch.', NOW()),

(3, 'user', 'What sides go well besides rice?', NOW()),
(3, 'ai', 'Quinoa or noodles are good options.', NOW()),
(3, 'user', 'Can I prepare this ahead?', NOW()),
(3, 'ai', 'Yes, but add sauces just before serving.', NOW()),
(3, 'user', 'How to store leftovers?', NOW()),
(3, 'ai', 'Use airtight containers and refrigerate.', NOW());

-- 6 summaries for recipe 3 (1 per 5 messages)
INSERT INTO Summaries (recipe_id, summary_text, created_at) VALUES
(3, 'User asked about adding broccoli, snap peas, and peeling carrots.', NOW()),
(3, 'Discussed tofu prep, oil choice, and adding ginger.', NOW()),
(3, 'Talked about stir-fry timing, soy sauce addition, and spicing.', NOW()),
(3, 'Covered using sesame oil, gluten-free substitutions, and adding cashews.', NOW()),
(3, 'User asked about side dishes like quinoa and noodles.', NOW()),
(3, 'Discussion on prepping ahead and storing leftovers.', NOW());
