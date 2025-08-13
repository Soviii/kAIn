import { useState, useRef } from "react"
import { User, Lock, Trash2, Save } from "lucide-react"
import styles from "./AccountSettingsForm.module.css"

const AccountSettingsForm = () => {
  const [formData, setFormData] = useState({
    "firstName": "vince",
    "lastName": "dudeeeee",
    "email": "vincedudeeee@aol.com"
  })
  const formDataRef = useRef({
    "firstName": "vince",
    "lastName": "dudeeeee",
    "email": "vincedudeeee@aol.com"
  })
  const [showDeleteDialog, setShowDeleteDialog] = useState(false)

  const handleInputChange = (field, value) => {
    setFormData(prev => ({ ...prev, [field]: value }))
  }

  // Will update info in backend
  // TODO
  const handleUpdateInfo = () => {
    alert("Profile Updated: Your account information has been successfully updated.")
  }

  // Sends email to user for resetting password
  // TODO
  const handleChangePassword = () => {
    alert("Password Change: Please check your email for password reset instructions.")
  }

  // Handles deleting user account
  // TODO
  const handleDeleteAccount = () => {
    alert("Account Deletion: Your account deletion request has been processed.")
    setShowDeleteDialog(false)
  }

  // returns true if no changes are made, false if there have been changes made
  const personalInfoHasBeenUpdated = () => {
    const current = formData;
    const original = formDataRef.current;
    console.log(`current: `, current);
    console.log(`original: `, original);
    return (
      current["firstName"] === original["firstName"] &&
      current["lastName"] === original["lastName"] &&
      current["email"] === original["email"]
    );
  }

  return (
    <div className={styles["account-settings"]}>
      <div className={styles["settings-header"]}>
        <h1>Account Settings</h1>
        <p>Manage your personal information and account preferences.</p>
      </div>

      <div className={styles["settings-card"]}>
        <div className={styles["card-header"]}>
          <div className={styles["card-title"]}>
            <User className={styles["title-icon"]} />
            Personal Information
          </div>
          <p className={styles["card-description"]}>
            Update your basic account details below.
          </p>
        </div>
        <div className={styles["card-content"]}>
          <div className={styles["form-grid"]}>
            <div className={styles["form-field"]}>
              <label htmlFor={styles["firstName"]}>First Name</label>
              <input
                id="firstName"
                type="text"
                value={formData.firstName}
                onChange={(e) => handleInputChange("firstName", e.target.value)}
                className={styles["form-input"]}
              />
            </div>
            <div className={styles["form-field"]}>
              <label htmlFor="lastName">Last Name</label>
              <input
                id="lastName"
                type="text"
                value={formData.lastName}
                onChange={(e) => handleInputChange("lastName", e.target.value)}
                className={styles["form-input"]}
              />
            </div>
          </div>

          <div className={styles["form-field"]}>
            <label htmlFor="email">Email Address</label>
            <input
              id="email"
              type="email"
              value={formData.email}
              onChange={(e) => handleInputChange("email", e.target.value)}
              className={styles["form-input"]}
            />
          </div>

          <button
            onClick={handleUpdateInfo}
            className={`${styles["btn"]} ${styles["btn-primary"]} ${personalInfoHasBeenUpdated() === true ? styles["btn-disabled"] : ""}`}
          >
            <Save className={styles["btn-icon"]} />
            Update Information
          </button>
        </div>
      </div>

      <div className={styles["settings-card"]}>
        <div className={styles["card-header"]}>
          <div className={styles["card-title"]}>
            <Lock className={styles["title-icon"]} />
            Security
          </div>
          <p className={styles["card-description"]}>
            Manage your password and account security.
          </p>
        </div>
        <div className={styles["card-content"]}>
          <button
            onClick={handleChangePassword}
            className={`${styles["btn"]} ${styles["btn-outline"]} ${styles["btn-full"]}`}
          >
            <Lock className={styles["btn-icon"]} />
            Change Password
          </button>
        </div>
      </div>

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
  )
}

export default AccountSettingsForm;