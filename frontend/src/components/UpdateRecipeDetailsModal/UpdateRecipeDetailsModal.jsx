import styles from "./UpdateRecipeDetailsModal.module.css";
import { Loader } from "lucide-react";

const UpdateRecipeDetailsModal = ({ updateStatus }) => {
  return (
    <>
      <div className={styles["modal-overlay"]}>
        <div className={styles["modal-content"]}>

          {updateStatus === "success" &&
            <p className={styles["modal-message-success"]}>
              Your recipe has been updated!
            </p>
          }

          {updateStatus === "fail" && (
            <p className={styles["modal-message-fail"]}>
              An error occured when saving your recipe
            </p>
          )}

          {updateStatus === "pending" && (
            <>
              <p className={styles["modal-message-pending"]}>
                Updating recipe... please wait
              </p>
              <div className={styles["loading-spinner-div"]}>
                <Loader size={28} className={styles["loading-spinner"]} />
              </div>
            </>
          )}
        </div>
      </div>
    </>
  )
}

export default UpdateRecipeDetailsModal;