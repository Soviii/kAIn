import { Trash2, X } from "lucide-react";
import styles from "./DeleteRecipeModal.module.css";
import { useContext, useState } from "react";
import { RecipeContext } from "../../pages/Main/Main";

const DeleteRecipeModal = ({ recipeTitle, handleCloseDeleteRecipeModal, handleDeleteRecipe }) => {
  const [deleteStatus, setDeleteStatus] = useState(null); // null | success | fail
  const { fetchRecipeSummaries, handleTabSelectionClicked, setFocusedRecipe, setFocusedRecipeId } = useContext(RecipeContext);
  
  // deletes current focused recipe and redirects to recipes tab
  const handleDeleteButtonClicked = async () => {
    try {
      const status = await handleDeleteRecipe(); // if handleDeleteRecipe returns a Promise

      if (status === 0) {
        setDeleteStatus("success");

        // wait 2 seconds
        await new Promise(resolve => setTimeout(resolve, 2000));

        setFocusedRecipe({});
        setFocusedRecipeId(-1);
        fetchRecipeSummaries();
        handleTabSelectionClicked(0);
        handleCloseDeleteRecipeModal();
      } else {
        setDeleteStatus("fail");
        await new Promise(resolve => setTimeout(resolve, 2000));
      }
    } catch (err) {
      console.error(err);
      setDeleteStatus("fail");
    }
  };


  return (
    <>
      <div className={styles["modal-overlay"]}>
        <div className={styles["modal-content"]}>
          {/* Close Button */}
          {deleteStatus !== "success" &&
            <button
              className={styles["modal-close"]}
              onClick={handleCloseDeleteRecipeModal}
            >
              <X size={20} />
            </button>
          }

          {/* Icon */}
          <div className={styles["modal-icon"]}>
            <Trash2 size={24} />
          </div>

          {/* Title & Message */}
          <h2 className={styles["modal-title"]}>Are you sure?</h2>

          {deleteStatus === "success" && 
            <p className={styles["modal-message-success"]}>
              <strong>{recipeTitle}</strong> has been deleted successfully!
            </p>
          }

          {deleteStatus === "fail" && (
            <p className={styles["modal-message-fail"]}>
              Failed to delete <strong>{recipeTitle}</strong>. Please try again.
            </p>
          )}
          {deleteStatus !== "success" &&
            <p className={styles["modal-message"]}>
              This will permanently delete
              <strong>{` ${recipeTitle}`}</strong>. This action cannot be undone.
            </p>
          }


          {/* Actions */}
          {deleteStatus !== "success" &&
            <div className={styles["modal-actions"]}>
              <button
                className={styles["cancel-btn"]}
                onClick={handleCloseDeleteRecipeModal}
              >
                Cancel
              </button>
              <button
                className={styles["confirm-btn"]}
                onClick={handleDeleteButtonClicked}
              >
                Delete
              </button>
            </div>
          }
        </div>
      </div>
    </>
  );
}

export default DeleteRecipeModal;
