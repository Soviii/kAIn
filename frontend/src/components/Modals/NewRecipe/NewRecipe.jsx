import React, { useState } from 'react';
import './NewRecipe.css';

const NewRecipe = () => {
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

      if (!response.ok) {
        throw new Error('Failed to submit recipe');
      }

      setSuccess(true);
      setTitle('');
      setDescription('');
      setIngredients(['']);
      setSteps(['']);
    } catch (err) {
      setError(err.message || 'An error occurred');
    }
  };

  return (
    <form className="new-recipe-form" onSubmit={handleSubmit}>
      <h2>Add New Recipe</h2>
      {success && <div className="alert alert-success">Recipe submitted!</div>}
      {error && <div className="alert alert-danger">{error}</div>}
      <div className="mb-3">
        <label className="form-label">Title</label>
        <input
          className="form-control"
          value={title}
          onChange={e => setTitle(e.target.value)}
          required
        />
      </div>
      <div className="mb-3">
        <label className="form-label">Description</label>
        <textarea
          className="form-control"
          value={description}
          onChange={e => setDescription(e.target.value)}
          rows={2}
        />
      </div>
      <div className="mb-3">
        <label className="form-label">Ingredients</label>
        {ingredients.map((ing, idx) => (
          <div key={idx} className="d-flex mb-2">
            <input
              className="form-control"
              value={ing}
              onChange={e => handleIngredientChange(idx, e.target.value)}
              required
            />
            {idx === ingredients.length - 1 && (
              <button type="button" className="btn btn-secondary ms-2" onClick={addIngredient}>
                +
              </button>
            )}
          </div>
        ))}
      </div>
      <div className="mb-3">
        <label className="form-label">Steps</label>
        {steps.map((step, idx) => (
          <div key={idx} className="d-flex mb-2">
            <input
              className="form-control"
              value={step}
              onChange={e => handleStepChange(idx, e.target.value)}
              required
            />
            {idx === steps.length - 1 && (
              <button type="button" className="btn btn-secondary ms-2" onClick={addStep}>
                +
              </button>
            )}
          </div>
        ))}
      </div>
      <button type="submit" className="btn btn-primary">Save Recipe</button>
    </form>
  );
};

export default NewRecipe;