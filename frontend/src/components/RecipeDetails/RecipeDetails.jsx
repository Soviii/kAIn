import './RecipeDetails.css';
import trashIcon from "../../assets/trash-bin.png";
import editIcon from "../../assets/edit-icon.png";

const RecipeDetails = ({ recipe }) => {

  const handleEditRecipe = () => {
    console.log('edit recipe clicked');
  }

  const handleDeleteRecipe = () => {
    console.log('delete recipe clicked');
  }

  return (
    <div className="recipe-details-div">
      <div className="recipe-header">
        <h1 className="recipe-name-h1">{recipe.title ?? "<RECIPE NAME>"}</h1>
        <div className="icons-div">
          <img src={editIcon} onClick={handleEditRecipe} className="edit-img" width="30" alt="edit-image" />
          <img src={trashIcon} onClick={handleDeleteRecipe} className="trash-img" width="30" alt="trash-image" />
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
          {(recipe["instructions"] || []).map((step, idx) => (
            <li className="instructions-li" key={idx}>
              <span className="step-number-span">{idx + 1}</span>
              <span className="step-info-span">{step}</span>
            </li>
          ))}
        </ul>
      </div>
    </div>
  )
}

export default RecipeDetails;