import { useState } from "react"
import { Trash2 } from "lucide-react"
import styles from "../AccountSettings.module.css";


const SecuritySection = () => {
    const [showDeleteDialog, setShowDeleteDialog] = useState(false)
    // Handles deleting user account
    // TODO
    const handleDeleteAccount = () => {
    alert("Account Deletion: Your account deletion request has been processed.")
        setShowDeleteDialog(false)
    }
    return (
        <div  className={styles["account-settings"]}>
      <div className={`${styles["settings-card"]} ${styles["danger-card"]}`}>
        <div className={`${styles["card-header"]} ${styles["danger-header"]}`}>
          <div className={`${styles["card-title"]} ${styles["danger-title"]}`}>
            <Trash2 className={styles["title-icon"]} />
            Danger Zone
          </div>
          <p className={styles["card-description"]}>
            Permanently delete your account and all associated data.
          </p>
        </div>
        <div className={styles["card-content"]}>
          <button
            onClick={() => setShowDeleteDialog(true)}
            className={`${styles["btn"]} ${styles["btn-destructive"]} ${styles["btn-full"]}`}
          >
            <Trash2 className={styles["btn-icon"]} />
            Delete Account
          </button>
        </div>
      </div>

      {showDeleteDialog && (
        <div className={styles["dialog-overlay"]}>
          <div className={styles["dialog"]}>
            <div className={styles["dialog-header"]}>
              <h3 className={styles["dialog-title"]}>Are you absolutely sure?</h3>
              <p className={styles["dialog-description"]}>
                This action cannot be undone. This will permanently delete your account
                and remove all your data from our servers.
              </p>
            </div>
            <div className={styles["dialog-footer"]}>
              <button
                onClick={() => setShowDeleteDialog(false)}
                className={`${styles["btn"]} ${styles["btn-outline"]}`}
              >
                Cancel
              </button>
              <button
                onClick={handleDeleteAccount}
                className={`${styles["btn"]} ${styles["btn-destructive"]}`}
              >
                Delete Account
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default SecuritySection;