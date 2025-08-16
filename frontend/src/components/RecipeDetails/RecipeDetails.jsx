import './RecipeDetails.css';
import { useState } from 'react';
import { Pencil, Trash2 } from "lucide-react";
import RecipeDetailsEditForm from '../RecipeDetailsEditForm/RecipeDetailsEditForm';
import DeleteRecipeModal from '../DeleteRecipeForm/DeleteRecipeModal';

const RecipeDetails = ({ recipe }) => {
  const [inEditMode, setInEditMode] = useState(false);
  const [inDeleteMode, setInDeleteMode] = useState(false);

  // deletes passed in recipe ID
  const handleDeleteRecipe = async () => {
    try {
      const response = await fetch('http://localhost:8080/recipes', {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'userId': 1, // TODO: update userId to be retrieved from JWT or server side session
          'recipeId': recipe.recipeId
        },
      });
      if (response.ok) {
        return 0; // meaning deleted successfully; tells DeleteRecipeModal to show confirm message
      }
    } catch (error) {
      console.error('Error fetching recipes:', error);
    }
  }

  // closes delete modal
  const handleCancelEdit = () => {
    setInEditMode(false);
  }

  // closes delete recipe modal
  const handleCloseDeleteRecipeModal = () => {
    setInDeleteMode(false);
  }

  if (inEditMode) {
    return (
      <RecipeDetailsEditForm recipe={recipe} onCancel={handleCancelEdit} />
    )
  }

  return (
    <div className="recipe-details-div">
      <div className="recipe-header">
        <h1 className="recipe-name-h1">{recipe.title ?? "<RECIPE NAME>"}</h1>
        <div className="icons-div">
          {/* <img src={editIcon} onClick={() => setInEditMode(true)} className="edit-img" width="30" alt="edit-image" /> */}
          <button className="edit-recipe-details-btn" onClick={() => setInEditMode(true)}>
            <Pencil size={18} />
          </button>
          <button className="delete-recipe-btn" onClick={() => setInDeleteMode(true)}>
            <Trash2 size={18} />
          </button>
          {inDeleteMode === true && <DeleteRecipeModal recipeTitle={recipe.title} handleCloseDeleteRecipeModal={handleCloseDeleteRecipeModal} handleDeleteRecipe={handleDeleteRecipe} />}
        </div>
      </div>
      <p className="recipe-description-p">{recipe.description ?? `<RECIPE DESCRIPTION>`}</p>
      <div className="recipe-tag-list-div">
        {(recipe["tags"] || []).map((tag, idx) => (
          <span key={idx} className="recipe-tag-span">{tag}</span>
        ))}
      </div>
      <hr className="horizontal-sep-hr" />
      <div className="ingredients-list-div">
        <h3 className="ingredients-h3">Ingredients</h3>
        <ul className="ingredients-ul">
          {(recipe["ingredients"] || []).map((obj, idx) => (
            <li className="ingredient-li" key={idx}>
              <span className="ingredient-quantity">{obj.quantity ? `${obj.quantity} ` : ``}</span>
              <span className="ingredient-unit">{obj.unit ? `${obj.unit}` : ``} </span>
              <span className="ingredient-name">{obj.name}</span>
            </li>
          ))}
        </ul>
      </div>
      <div className="instructions-div">
        <h3 className="instructions-h3">Instructions</h3>
        <ul className="instructions-ul">
          {(recipe["steps"] || []).map((step, idx) => (
            <li className="instructions-li" key={idx}>
              <span className="step-number-span">{step["stepNumber"]}</span>
              <span className="step-info-span">{step["instruction"]}</span>
            </li>
          ))}
        </ul>
      </div>
    </div>
  )
}

export default RecipeDetails;