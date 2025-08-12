import './RecipeDetails.css';
import trashIcon from "../../assets/trash-bin.png";
import editIcon from "../../assets/edit-icon.png";
// TODO: show details of highlighted recipe
// const RecipeDetails = ({ recipe }) => {
const RecipeDetails = () => {
  const recipe = {
    "title": "Orange Chicken",
    "description": "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Voluptatibus, quibusdam aperiam! Earum, eos deserunt temporibus at dolorem nam molestias reiciendis fugit alias architecto? Facere reprehenderit quos incidunt doloribus eius. Explicabo.",
    "tags": ["meatlovers", "sweet", "tasty", "easy"],
    "ingredients": [{
      "name": "chicken",
      "unit": "lbs",
      "quantity": "1",
    },
    {
      "name": "orange",
      "unit": undefined,
      "quantity": 4
    },
    {
      "name": "special sauce",
      "unit": "oz",
      "quantity": 1
    }],
    "instructions": [
      "do this",
      "then this",
      "also this",
      "Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsa voluptatibus et, corporis sint tempore exercitationem sunt consequatur error beatae delectus quam nam perferendis. Beatae quia veritatis doloribus impedit nostrum inventore!",
      "Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsa voluptatibus et, corporis sint tempore exercitationem sunt consequatur error beatae delectus quam nam perferendis. Beatae quia veritatis doloribus impedit nostrum inventore!",
      "Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsa voluptatibus et, corporis sint tempore exercitationem sunt consequatur error beatae delectus quam nam perferendis. Beatae quia veritatis doloribus impedit nostrum inventore!",
      "Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsa voluptatibus et, corporis sint tempore exercitationem sunt consequatur error beatae delectus quam nam perferendis. Beatae quia veritatis doloribus impedit nostrum inventore!",
      "Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsa voluptatibus et, corporis sint tempore exercitationem sunt consequatur error beatae delectus quam nam perferendis. Beatae quia veritatis doloribus impedit nostrum inventore!",
      "Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsa voluptatibus et, corporis sint tempore exercitationem sunt consequatur error beatae delectus quam nam perferendis. Beatae quia veritatis doloribus impedit nostrum inventore!",
      "Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsa voluptatibus et, corporis sint tempore exercitationem sunt consequatur error beatae delectus quam nam perferendis. Beatae quia veritatis doloribus impedit nostrum inventore!",
      "Lorem ipsum dolor sit, amet consectetur adipisicing elit. Ipsa voluptatibus et, corporis sint tempore exercitationem sunt consequatur error beatae delectus quam nam perferendis. Beatae quia veritatis doloribus impedit nostrum inventore!"
    ]
  }

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