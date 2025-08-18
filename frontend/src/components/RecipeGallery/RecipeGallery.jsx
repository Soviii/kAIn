import { useState } from 'react';
import Spinner from '../Spinner/Spinner.jsx';
import RecipeCard from '../RecipeCard/RecipeCard.jsx';
import NewRecipe from '../Modals/NewRecipe/NewRecipe.jsx';
import './RecipeGallery.css';


const RecipeGallery = ({ isLoading, focusedRecipeId, recipeList }) => {
  const [showModal, setShowModal] = useState(false);

  return (
    <div className="recipes-section">
      <div className='saved-recipes-container'>
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
                  <NewRecipe onClose={() => setShowModal(false)} />
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
      {isLoading ? (
        <Spinner />
      ) : recipeList.length === 0 ? (
        <div className="text-muted">No recipes saved yet.</div>
      ) : (
        <div className="row">
          {recipeList.slice() // need to slice since is const
          .reverse()
          .map((recipe, idx) => (
            <RecipeCard
              key={idx}
              details={{
                "idx": recipe["recipeId"],
                "title": recipe["title"],
                "description": recipe["description"],
                "tags": recipe["tags"]
              }}
              isFocused={focusedRecipeId === recipe["recipeId"] ? true : false}
            />
          ))}
          <RecipeCard 
          details={{
            "idx": 999,
            "title": "My fake recipe",
            "description": "My fake description",
            "tags": ["some", "different", "tags", "etc."]
          }}
          isFocused={false}
          />
          <RecipeCard 
          details={{
            "idx": 999,
            "title": "My fake recipe",
            "description": "My fake description",
            "tags": ["some", "different", "tags", "etc."]
          }}
          isFocused={false}
          />
          <RecipeCard 
          details={{
            "idx": 999,
            "title": "My fake recipe",
            "description": "My fake description",
            "tags": ["some", "different", "tags", "etc."]
          }}
          isFocused={false}
          />
          <RecipeCard 
          details={{
            "idx": 999,
            "title": "My fake recipe",
            "description": "My fake description",
            "tags": ["some", "different", "tags", "etc."]
          }}
          isFocused={false}
          />
          <RecipeCard 
          details={{
            "idx": 999,
            "title": "My fake recipe",
            "description": "My fake description",
            "tags": ["some", "different", "tags", "etc."]
          }}
          isFocused={false}
          />
          <RecipeCard 
          details={{
            "idx": 999,
            "title": "My fake recipe",
            "description": "My fake description",
            "tags": ["some", "different", "tags", "etc."]
          }}
          isFocused={false}
          />
        </div>
      )}
    </div>
  );
};

export default RecipeGallery;