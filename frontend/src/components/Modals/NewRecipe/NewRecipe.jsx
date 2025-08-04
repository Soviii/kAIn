import React, { useState } from 'react';
import { Form, Button, Alert, Row, Col } from 'react-bootstrap';
import './NewRecipe.css';

const NewRecipe = ({onClose, onSubmit}) => {  
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [ingredients, setIngredients] = useState(['']);
  const [steps, setSteps] = useState(['']);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState('');

  const handleIngredientChange = (idx, value) => {
    const updated = [...ingredients];
    updated[idx] = value;
    setIngredients(updated);
  };

  const handleStepChange = (idx, value) => {
    const updated = [...steps];
    updated[idx] = value;
    setSteps(updated);
  };

  const addIngredient = () => setIngredients([...ingredients, '']);
  const addStep = () => setSteps([...steps, '']);

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
        {ingredients.map((ing, idx) => (
          <div key={idx} className="d-flex mb-2">
            <Form.Control
              value={ing}
              onChange={e => handleIngredientChange(idx, e.target.value)}
              required
            />
            {idx === ingredients.length - 1 && (
              <Button className="add-ingredient-button" onClick={addIngredient}>
                + Ingredient
              </Button>
            )}
          </div>
        ))}
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>Steps</Form.Label>
        {steps.map((step, idx) => (
          <div key={idx} className="d-flex mb-2">
            <Form.Control
              value={step}
              onChange={e => handleStepChange(idx, e.target.value)}
              required
            />
            {idx === steps.length - 1 && (
              <Button className="add-step-button" onClick={addStep}>
                + Step
              </Button>
            )}
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