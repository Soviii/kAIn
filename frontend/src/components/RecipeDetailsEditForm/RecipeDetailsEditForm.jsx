import { useContext, useState } from 'react';
import { Save, X, Plus } from 'lucide-react';
import IngredientInput from '../IngredientEditInput/IngredientEditInput.jsx';
import TagInput from '../TagEditInput/TagEditInput.jsx';
import styles from './RecipeDetailsEditForm.module.css';
import { RecipeContext } from '../../pages/Main/Main.jsx';
import UpdateRecipeDetailsModal from '../UpdateRecipeDetailsModal/UpdateRecipeDetailsModal.jsx';

const RecipeDetailsEditForm = ({ recipe, onCancel }) => {
  const [updateStatus, setUpdateStatus] = useState("");
  const [formData, setFormData] = useState({
    title: recipe.title || '',
    description: recipe.description || '',
    tags: recipe.tags || [],
    ingredients: recipe.ingredients || [{ quantity: '', unit: '', name: '' }],
    steps: recipe.steps || [{ instruction: '', stepNumber: 0 }]
  });
  const { setFocusedRecipe, fetchRecipeSummaries, handleUpdateRecipe } = useContext(RecipeContext);

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

  const handleStepChange = (index, value) => {
    const newSteps = [...formData.steps];
    newSteps[index] = { ...newSteps[index], instruction: value };
    setFormData(prev => ({ ...prev, steps: newSteps }));
  };

  const addStep = () => {
    setFormData(prev => ({
      ...prev,
      steps: [
        ...prev.steps,
        { instruction: '', stepNumber: prev.steps.length }
      ]
    }));
  };

  const removeStep = (index) => {
    if (formData.steps.length > 1) {
      setFormData(prev => ({
        ...prev,
        steps: prev.steps.filter((_, i) => i !== index)
      }));
    }
  };

  // submitting new recipe info to backend
  const handleSubmit = async (e) => {
    e.preventDefault();
    // Filter out empty ingredients and steps
    const cleanedData = {
      ...formData,
      ingredients: formData.ingredients.filter(ing => ing.name.trim()),
      steps: formData.steps.filter(inst => inst.instruction.trim())
    };

    setUpdateStatus("pending");
    const status = await handleUpdateRecipe(cleanedData);

    await new Promise(resolve => setTimeout(resolve, 1250));
    // if saving was successful
    if (status === 0) {
      setFocusedRecipe(cleanedData);
      fetchRecipeSummaries();
      setUpdateStatus("success");
      await new Promise(resolve => setTimeout(resolve, 1250));
      onCancel();
    } else {
      setUpdateStatus("fail");
      await new Promise(resolve => setTimeout(resolve, 1250));
      setUpdateStatus("");
    }
  };

  return (
    <>
      {updateStatus.length > 0 && <UpdateRecipeDetailsModal updateStatus={updateStatus} />}
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

            {/* Steps */}
            <div className={styles.card}>
              <div className={styles.sectionHeader}>
                <label className={styles.label}>Instructions</label>
                <button type="button" onClick={addStep} className={styles.sectionActions}>
                  <Plus size={14} /> Add Step
                </button>
              </div>
              <div className={styles.stack}>
                {formData.steps.map((step, index) => (
                  <div key={index} className={styles.instructionRow}>
                    <div className={styles.instructionNumber}>{index + 1}</div>
                    <div className={styles.instructionTextRow}>
                      <textarea
                        value={step.instruction}
                        onChange={(e) => handleStepChange(index, e.target.value)}
                        className={styles.textarea}
                        rows="2"
                        placeholder={`Step ${index + 1} instructions...`}
                      />
                      {formData.steps.length > 1 && (
                        <button
                          type="button"
                          onClick={() => removeStep(index)}
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
    </>
  );
};

export default RecipeDetailsEditForm;