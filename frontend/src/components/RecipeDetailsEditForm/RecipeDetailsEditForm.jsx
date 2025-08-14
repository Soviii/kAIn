import { useState } from 'react';
import { Save, X, Plus } from 'lucide-react';
import IngredientInput from '../IngredientEditInput/IngredientEditInput.jsx';
import TagInput from '../TagEditInput/TagEditInput.jsx';
import styles from './RecipeDetailsEditForm.module.css';

const RecipeDetailsEditForm = ({ recipe, onSave, onCancel }) => {
  const [formData, setFormData] = useState({
    title: recipe.title || '',
    description: recipe.description || '',
    tags: recipe.tags || [],
    ingredients: recipe.ingredients || [{ quantity: '', unit: '', name: '' }],
    instructions: recipe.instructions || ['']
  });

  const handleInputChange = (field, value) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  const handleIngredientUpdate = (index, ingredient) => {
    const newIngredients = [...formData.ingredients];
    newIngredients[index] = ingredient;
    setFormData(prev => ({ ...prev, ingredients: newIngredients }));
  };

  const addIngredient = () => {
    setFormData(prev => ({
      ...prev,
      ingredients: [...prev.ingredients, { quantity: '', unit: '', name: '' }]
    }));
  };

  const removeIngredient = (index) => {
    if (formData.ingredients.length > 1) {
      setFormData(prev => ({
        ...prev,
        ingredients: prev.ingredients.filter((_, i) => i !== index)
      }));
    }
  };

  const handleInstructionChange = (index, value) => {
    const newInstructions = [...formData.instructions];
    newInstructions[index] = value;
    setFormData(prev => ({ ...prev, instructions: newInstructions }));
  };

  const addInstruction = () => {
    setFormData(prev => ({
      ...prev,
      instructions: [...prev.instructions, '']
    }));
  };

  const removeInstruction = (index) => {
    if (formData.instructions.length > 1) {
      setFormData(prev => ({
        ...prev,
        instructions: prev.instructions.filter((_, i) => i !== index)
      }));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Filter out empty ingredients and instructions
    const cleanedData = {
      ...formData,
      ingredients: formData.ingredients.filter(ing => ing.name.trim()),
      instructions: formData.instructions.filter(inst => inst.trim())
    };
    onSave(cleanedData);
  };

return (
  <div className={styles.page}>
    <div className={styles.container}>
      <form onSubmit={handleSubmit} className={styles.form}>
        
        {/* Header */}
        <div className={styles.header}>
          <h1 className={styles.title}>Edit Recipe</h1>
          <p className={styles.subtitle}>Update your recipe details below</p>
        </div>

        {/* Title */}
        <div className={styles.card}>
          <label className={styles.label}>Recipe Title</label>
          <input
            type="text"
            value={formData.title}
            onChange={(e) => handleInputChange('title', e.target.value)}
            className={styles.input}
            placeholder="Enter recipe title..."
            required
          />
        </div>

        {/* Description */}
        <div className={styles.card}>
          <label className={styles.label}>Description</label>
          <textarea
            value={formData.description}
            onChange={(e) => handleInputChange('description', e.target.value)}
            className={styles.textarea}
            rows="3"
            placeholder="Describe your recipe..."
          />
        </div>

        {/* Tags */}
        <div className={styles.card}>
          <label className={styles.label}>Tags</label>
          <TagInput
            tags={formData.tags}
            onUpdate={(tags) => handleInputChange('tags', tags)}
          />
        </div>

        {/* Ingredients */}
        <div className={styles.card}>
          <div className={styles.sectionHeader}>
            <label className={styles.label}>Ingredients</label>
            <button type="button" onClick={addIngredient} className={styles.sectionActions}>
              <Plus size={14} />Add
            </button>
          </div>
          <div className={styles.stack}>
            {formData.ingredients.map((ingredient, index) => (
              <IngredientInput
                key={index}
                ingredient={ingredient}
                index={index}
                onUpdate={handleIngredientUpdate}
                onRemove={removeIngredient}
              />
            ))}
          </div>
        </div>

        {/* Instructions */}
        <div className={styles.card}>
          <div className={styles.sectionHeader}>
            <label className={styles.label}>Instructions</label>
            <button type="button" onClick={addInstruction} className={styles.sectionActions}>
              <Plus size={14} /> Add Step
            </button>
          </div>
          <div className={styles.stack}>
            {formData.instructions.map((instruction, index) => (
              <div key={index} className={styles.instructionRow}>
                <div className={styles.instructionNumber}>{index + 1}</div>
                <div className={styles.instructionTextRow}>
                  <textarea
                    value={instruction}
                    onChange={(e) => handleInstructionChange(index, e.target.value)}
                    className={styles.textarea}
                    rows="2"
                    placeholder={`Step ${index + 1} instructions...`}
                  />
                  {formData.instructions.length > 1 && (
                    <button
                      type="button"
                      onClick={() => removeInstruction(index)}
                      className={styles.removeButton}
                    >
                      <X size={16} />
                    </button>
                  )}
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Action Buttons */}
        <div className={styles.actions}>
          <button type="button" onClick={onCancel} className={styles.cancelBtn}>
            <X size={18} /> Cancel
          </button>
          <button type="submit" className={styles.saveBtn}>
            <Save size={18} /> Save Recipe
          </button>
        </div>
      </form>
    </div>
  </div>
);
};

export default RecipeDetailsEditForm;