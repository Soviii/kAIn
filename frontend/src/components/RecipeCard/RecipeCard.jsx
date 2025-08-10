import React from 'react';
import './RecipeCard.css';

const RecipeCard = ({ details }) => {

  // TODO: switch 
  const onClick = () => {
    console.log(`${details.idx} clicked!`);
  }

  return (
    <div>
      <div className="card" onClick={onClick}>
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