import React, {useState} from 'react';
import './RecipeCard.css';

const RecipeCard = ({ details, handleRecipeCardClicked, isFocused }) => {
    const [f, setF] = useState(0);
  // update main page to switch tabs to recipe details and show this recipe's info
  const onClick = () => {
    handleRecipeCardClicked(details["idx"]);
  }

  return (
    <div>
      <div className={`card ${isFocused === true ? "focused-card" : ''}`} onClick={onClick}>
        <div className="card-body">
          <h5 className="card-title">{details.title || `Recipe ${details.idx + 1}`}</h5>
          <p className="card-text">{details.description || 'No description available.'}</p>
        </div>
        <div className="tag-list-div">
          {(details["tags"] || []).map((tag, idx) => (
            <span key={idx} className="tag-span">{tag}</span>
          ))}
        </div>
      </div>
    </div>
  )
}

export default RecipeCard;