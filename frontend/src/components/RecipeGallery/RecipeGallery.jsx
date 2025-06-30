import React from 'react';
import { useEffect, useState } from 'react';

const RecipeGallery = () => {
  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);

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
          description: "Classic Italian pasta with eggs, cheese, pancetta, and pepper."
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
    }, 500); // Simulate loading delay
  }, []);

  return (
    <div>
      <h2>Saved Recipes</h2>
      {loading ? (
        <div>Loading...</div>
      ) : recipes.length === 0 ? (
        <div className="text-muted">No recipes saved yet.</div>
      ) : (
        <div className="row">
          {recipes.map((recipe, idx) => (
            <div key={idx} className="col-md-4 mb-3">
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">{recipe.title || `Recipe ${idx + 1}`}</h5>
                  <p className="card-text">{recipe.description || 'No description available.'}</p>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default RecipeGallery;
