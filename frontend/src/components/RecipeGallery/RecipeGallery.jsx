import React from 'react';
import { useEffect, useState } from 'react';

import Spinner from '../Spinner/Spinner.jsx';
import RecipeCard from '../RecipeCard/RecipeCard.jsx';

const RecipeGallery = () => {
  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [focusedRecipe, setFocusRecipe] = useState("");

  useEffect(() => {
    // Uncomment and implement API call when backend is ready
    /*
    fetch('/api/recipes')
      .then(res => res.json())
      .then(data => {
        setRecipes(data);
        setLoading(false);
      })
      .catch(() => setLoading(false));
    */

    // Hardcoded recipes for now
    setTimeout(() => {
      setRecipes([
        {
          title: "Spaghetti Carbonara",
          description: "Classic Italian pasta with eggs, cheese, pancetta, and pepper.",
          tags: ["documentation", "food", "june is a poopy head", "dadjasnajnd", "dasjdnasjdndj", "dajsdnajdnsadnajd", "dajsndadjnjdsna"]
        },
        {
          title: "Chicken Tikka Masala",
          description: "Grilled chicken pieces in a spicy curry sauce."
        },
        {
          title: "Vegetable Stir Fry",
          description: "Mixed vegetables sautéed in a savory sauce."
        }
      ]);
      setLoading(false);
    }, 1000); // Simulate loading delay
  }, []);

  return (
    <div>
      <h2>Saved Recipes</h2>
      {loading ? (
        <Spinner />
      ) : recipes.length === 0 ? (
        <div className="text-muted">No recipes saved yet.</div>
      ) : (
        <div className="row">
          {recipes.map((recipe, idx) => (
            <RecipeCard 
            key={idx}
            details={{
              "idx": idx,
              "title": recipe.title,
              "description": recipe.description,
              "tags": recipe.tags
            }}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export default RecipeGallery;
