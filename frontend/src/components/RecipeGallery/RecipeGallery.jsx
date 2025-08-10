import React, { useEffect, useState } from 'react';
import Spinner from '../Spinner/Spinner.jsx';
import RecipeCard from '../RecipeCard/RecipeCard.jsx';
import NewRecipe from '../Modals/NewRecipe/NewRecipe.jsx';
import './RecipeGallery.css';


const RecipeGallery = () => {
  const [recipes, setRecipes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [focusedRecipe, setFocusRecipe] = useState("");
  const [showModal, setShowModal] = useState(false);

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

  const handleAddRecipe = (recipe) => {
    setRecipes([...recipes, recipe]);
    setShowModal(false);
  };

  return (
    <div>
      <div>
        <h2>Saved Recipes</h2>
        <button className="btn btn-primary" onClick={() => setShowModal(true)}>
          + Add Recipe
        </button>
      </div>
      {showModal && (
        <div className="modal-backdrop show">
          <div className="modal d-block" tabIndex="-1">
            <div className="modal-dialog">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title">Add New Recipe</h5>
                  <button type="button" className="btn-close" onClick={() => setShowModal(false)}></button>
                </div>
                <div className="modal-body">
                  <NewRecipe onSubmit={handleAddRecipe} />
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
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