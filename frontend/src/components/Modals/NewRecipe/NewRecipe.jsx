import React, { useState } from 'react';
import { Form, Button, Alert, Row, Col } from 'react-bootstrap';
import './NewRecipe.css';

//  TODO add in the "tag" field to the recipe form, should come after Title
const NewRecipe = ({onClose, onSubmit}) => {  
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

  // Function to add a new ingredient
  const handleAddIngredient = () => setIngredients([...ingredients, { name: '', quantity: '', unit: '' }]);

  // Function to add a new step
  const handleAddStep = () => setSteps([...steps, { instruction: '', stepNumber: steps.length + 1 }]);

  // Function to handle POST request to submit the recipe
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess(false);

    try {
    //   const response = await fetch('/api/recipes', {
    //     method: 'POST',
    //     headers: { 'Content-Type': 'application/json' },
    //     body: JSON.stringify({ title, description, ingredients, steps }),
    //   });
    const response = { ok: true }; // Mock response for testing
      console.log(JSON.stringify({ title, description, ingredients, steps }))
      if (response.ok) {
        setSuccess(true);
        setTitle('');
        setDescription('');
        setIngredients(['']);
        setSteps(['']);
        // TODO: Update how we are creating a new recipe object if needed
        const newRecipe = { title, description, ingredients, steps };
        // Call the onSubmit prop with the new recipe to update the gallery view
        onSubmit && onSubmit(newRecipe);
      } else {
        throw new Error('Failed to submit recipe');
      }
    } catch (err) {
      setError(err.message || 'An error occurred');
    }
  };

   return (
    <Form className="new-recipe-form" onSubmit={handleSubmit}>
      <h2>Add New Recipe</h2>
      {success && <Alert variant="success">Recipe submitted!</Alert>}
      {error && <Alert variant="danger">{error}</Alert>}
      <Form.Group className="mb-3">
        <Form.Label>Title</Form.Label>
        <Form.Control
          value={title}
          onChange={e => setTitle(e.target.value)}
          required
        />
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>Description</Form.Label>
        <Form.Control
          as="textarea"
          value={description}
          onChange={e => setDescription(e.target.value)}
          rows={2}
        />
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>Ingredients</Form.Label>
        {ingredients.map((ingredientObj, idx) => (
          <div key={idx} className="d-flex mb-2">
            <Form.Control
              placeholder="Qty"
              type="number"
              min="0"
              value={ingredientObj.quantity}
              onChange={e => handleIngredientChange(idx, 'quantity', e.target.value)}
              required
              style={{ width: 80 }}
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
            />
            {idx === ingredients.length - 1 && (
              <div> 
              <Button className="add-ingredient-button" onClick={handleAddIngredient}>
                + Add
              </Button>
              </div>
            )}
            <Button
              className="remove-ingredient-button"
              variant="outline-danger"
              type="button"
              onClick={() => {handleDeleteIngredient(idx)}}
            >
              Delete 
            </Button>
          </div>
        ))}
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>Steps</Form.Label>
        {steps.map((stepObj, idx) => (
          <div key={idx} className="d-flex mb-2">
            <Form.Control
              value={stepObj.instruction}
              onChange={e => handleStepChange(idx, "instruction", e.target.value)}
              placeholder={`Step ${idx + 1}`}
              required
            />
            {idx === steps.length - 1 && (
              <Button className="add-step-button" onClick={handleAddStep}>
                + Add
              </Button>
            )}
            <Button
              className="remove-ingredient-button"
              variant="outline-danger"
              type="button"
              onClick={() => {handleDeleteIngredient(idx)}}
            >
              Delete
            </Button>
          </div>
        ))}
      </Form.Group>
      <Row className="justify-content-center mt-3">
        <Col xs={5} className="d-flex justify-content-end">
          <Button className="save-recipe-button">Save Recipe</Button>
        </Col>
        <Col xs={5} className="d-flex justify-content-start">
          <Button className="cancel-button" variant="outline-primary" onClick={onClose}>Cancel</Button>
        </Col>
      </Row>
    </Form>
  );
};

export default NewRecipe;