import { useState, useRef } from "react"
import { User, Save } from "lucide-react"
import styles from "../AccountSettings.module.css";

// TODO: REFACTOR accountsettingsform and break up the 3 sections into their own components
const PersonalInfoSection = () => {
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
      </div>
  );
}

export default PersonalInfoSection;