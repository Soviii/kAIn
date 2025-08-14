import { X } from 'lucide-react';
import styles from './IngredientEditInput.module.css';

const IngredientEditInput = ({ ingredient, index, onUpdate, onRemove }) => {
  const handleChange = (field, value) => {
    onUpdate(index, { ...ingredient, [field]: value });
  };

return (
  <div className={styles.container}>
    <input
      type="text"
      placeholder="Qty"
      value={ingredient.quantity || ''}
      onChange={(e) => handleChange('quantity', e.target.value)}
      className={styles.inputQty}
    />
    <input
      type="text"
      placeholder="Unit"
      value={ingredient.unit || ''}
      onChange={(e) => handleChange('unit', e.target.value)}
      className={styles.inputUnit}
    />
    <input
      type="text"
      placeholder="Ingredient name"
      value={ingredient.name || ''}
      onChange={(e) => handleChange('name', e.target.value)}
      className={styles.inputName}
    />
    <button
      type="button"
      onClick={() => onRemove(index)}
      className={styles.removeBtn}
    >
      <X size={16} />
    </button>
  </div>
);

};

export default IngredientEditInput;