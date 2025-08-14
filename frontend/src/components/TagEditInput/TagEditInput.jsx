import { useState } from 'react';
import { X, Plus } from 'lucide-react';
import styles from "./TagEditInput.module.css";

const TagEditInput = ({ tags, onUpdate }) => {
  const [inputValue, setInputValue] = useState('');

  const addTag = () => {
    if (inputValue.trim() && !tags.includes(inputValue.trim())) {
      onUpdate([...tags, inputValue.trim()]);
      setInputValue('');
    }
  };

  const removeTag = (indexToRemove) => {
    onUpdate(tags.filter((_, index) => index !== indexToRemove));
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      e.preventDefault();
      addTag();
    }
  };

  return (
    <div className={styles.container}>
      <div className={styles.inputRow}>
        <input
          type="text"
          placeholder="Add a tag..."
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
          // onKeyPress={handleKeyPress}
          className={styles.input}
        />
        <button
          type="button"
          onClick={addTag}
          className={styles.addBtn}
        >
          <Plus size={14} />
          Add
        </button>
      </div>

      {tags.length > 0 && (
        <div className={styles.tagList}>
          {tags.map((tag, index) => (
            <span key={index} className={styles.tagPill}>
              {tag}
              <button
                type="button"
                onClick={() => removeTag(index)}
                className={styles.removeBtn}
              >
                <X size={12} />
              </button>
            </span>
          ))}
        </div>
      )}
    </div>
  );

};

export default TagEditInput;