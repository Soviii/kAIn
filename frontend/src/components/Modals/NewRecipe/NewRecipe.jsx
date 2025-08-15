import { useContext, useState } from 'react';
import { Form, Button, Alert } from 'react-bootstrap';
import { RecipeContext } from '../../../pages/Main/Main';
import './NewRecipe.css';

//  TODO add in the "tag" field to the recipe form, should come after Title

const NewRecipe = ({ onClose }) => {  
  const userId = 1; // TODO: replace with actual user ID from context or props
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState('');
  const [ingredients, setIngredients] = useState([
    { name: '', quantity: '', unit: '' }
  ]);
  const [steps, setSteps] = useState([
    {instruction: '', stepNumber: 1}
  ]);
  const { handleNewRecipeSuccess } = useContext(RecipeContext);


  // Function to handle changes in ingredient fields
  const handleIngredientChange = (idx, field, value) => {
    const updated = [...ingredients];
    updated[idx] = { ...updated[idx], [field]: value };
    setIngredients(updated);
  };

  // Function to handle changes in step fields
  const handleStepChange = (idx, field, value) => {
    const updated = [...steps];
    updated[idx] = { ...updated[idx], [field]: value };
    setSteps(updated);
  };

  // Function to handle deletion of an ingredient
  const handleDeleteIngredient = (idx) => {
    setIngredients(ingredients.filter((_,i) => i !== idx));
  };

  // Function to handle deletion of a step
  const handleDeleteStep = (idx) => {
    setSteps(steps.filter((_,i) => i !== idx));
  };

  // Function to add a new ingredient obj
  const handleAddIngredient = () => setIngredients([...ingredients, { name: '', quantity: '', unit: '' }]);

  // Function to add a new step obj
  const handleAddStep = () => setSteps([...steps, { instruction: '', stepNumber: steps.length + 1 }]);

  // Function to handle moving a step up
  const handleMoveStepUp = (idx) => {
    if (idx > 0) {
      const updatedSteps = [...steps];
      // swaps two items in the array with destructuring and without using temp variable
      [updatedSteps[idx - 1], updatedSteps[idx]] = [updatedSteps[idx], updatedSteps[idx - 1]];
      setSteps(updatedSteps);
    }
  };

  // Function to handle moving a step down
  const handleMoveStepDown = (idx) => {
    if (idx < steps.length - 1) {
      const updatedSteps = [...steps];
      // swaps two items in the array with destructuring and without using temp variable
      [updatedSteps[idx + 1], updatedSteps[idx]] = [updatedSteps[idx], updatedSteps[idx + 1]];
      setSteps(updatedSteps);
    }
  };

  // Function to handle POST request to submit the recipe
  const handleSubmitRecipe = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess(false);

    try {
      const response = await fetch('http://localhost:8080/recipes', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title, description, ingredients, steps, userId }),
      });
      if (response.ok) {
        const data = await response.json();
        setSuccess(true);
        setTimeout(() => {
          onClose();
          handleNewRecipeSuccess(data["recipeId"], data);
        }, 1000);
      } else {
        throw new Error('Failed to submit recipe');
      }
    } catch (err) {
      setError(err.message || 'An error occurred');
    }
  };

   return (
    <Form className="new-recipe-form">
      <h2 className="add-recipe-title">Add New Recipe</h2>
      {success && <Alert variant="success">Recipe submitted!</Alert>}
      {error && <Alert variant="danger">{error}</Alert>}
      <Form.Group className="form-section">
        <Form.Label>Title</Form.Label>
        <Form.Control
          value={title}
          onChange={e => setTitle(e.target.value)}
          required
          className="recipe-title-input"
        />
      </Form.Group>
      <Form.Group className="form-section">
        <Form.Label>Description</Form.Label>
        <Form.Control
          as="textarea"
          value={description}
          onChange={e => setDescription(e.target.value)}
          rows={2}
          className='recipe-description-input'
        />
      </Form.Group>
      <Form.Group className="form-section">
        <Form.Label>Ingredients</Form.Label>
        {ingredients.map((ingredientObj, idx) => (
          <div key={idx} className="ingredient-list">
            <Form.Control
              placeholder="Qty"
              type="number"
              min="0"
              value={ingredientObj.quantity}
              onChange={e => handleIngredientChange(idx, 'quantity', e.target.value)}
              required
              style={{ width: 80 }}
              className="ingredient-quantity-input"
            />
            <Form.Control
              as="input"
              list={`unit-options-${idx}`}
              value={ingredientObj.unit}
              onChange={e => handleIngredientChange(idx, 'unit', e.target.value)}
              required
              style={{ width: 100 }}
              placeholder="Unit"
            />
            <datalist id={`unit-options-${idx}`}>
              <option value="tsp" />
              <option value="tbsp" />
              <option value="cup" />
              <option value="pcs" />
              <option value="g" />
              <option value="kg" />
              <option value="ml" />
              <option value="l" />
            </datalist>
            <Form.Control
              value={ingredientObj.name}
              onChange={e => handleIngredientChange(idx, "name", e.target.value)}
              placeholder="Ingredient"
              required
              className="ingredient-name-input"
            />
            <div className='action-buttons'>
              {idx === ingredients.length - 1 && (
                <Button 
                className="add-ingredient-button" 
                onClick={handleAddIngredient}
                >
                  + Add
                </Button>
              )}
              <Button
                className="remove-ingredient-button"
                variant="outline-danger"
                type="button"
                onClick={() => {handleDeleteIngredient(idx)}}
                disabled={ingredients.length === 1}
              >
                Delete 
              </Button>
          </div>
          </div>
        ))}
      </Form.Group>
      <Form.Group className="form-section">
        <Form.Label>Steps</Form.Label>
        {steps.map((stepObj, idx) => (
          <div key={idx} className="steps-list">
            <div className="move-steps-container"> 
              {idx > 0 && (
                <Button
                  className= "move-arrow-button"
                  variant="outline-secondary"
                  type="button"
                  onClick={() => handleMoveStepUp(idx)}
                  disabled={idx === 0}
                >
                ↑
                </Button>
              )}
              {idx < steps.length - 1 && (
                <Button
                  className= "move-arrow-button"
                  variant="outline-secondary"
                  type="button"
                  onClick={() => handleMoveStepDown(idx)}
                disabled={idx === steps.length - 1}
                >
                ↓
                </Button>
              )}
              
            </div>
            <Form.Label className="step-number">{`${idx + 1}.`}</Form.Label>
            <Form.Control
              value={stepObj.instruction}
              onChange={e => handleStepChange(idx, "instruction", e.target.value)}
              placeholder={`Step ${idx + 1}`}
              required
              className="step-instruction-input"
            />
            <div className='action-buttons'>
            {idx === steps.length - 1 && (
              <Button 
                className="add-step-button" 
                type="button"
                onClick={handleAddStep}
              >
                + Add
              </Button>
            )}
            <Button
              className="remove-step-button"
              variant="outline-danger"
              type="button"
              onClick={() => {handleDeleteStep(idx)}}
              disabled={steps.length === 1}
            >
              Delete 
            </Button>
          </div>
          </div>
        ))}
      </Form.Group>
      <div className="button-row">
        <Button className="save-recipe-button" onClick={handleSubmitRecipe}>Save Recipe</Button>
        <Button className="cancel-button" variant="outline-danger" onClick={onClose}>Cancel</Button>
      </div>
    </Form>
  );
};

export default NewRecipe;