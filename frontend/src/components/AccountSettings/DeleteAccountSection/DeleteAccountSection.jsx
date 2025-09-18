import { useState } from "react"
import { Lock } from "lucide-react"
import styles from "../AccountSettings.module.css";

const DeleteAccountSection = () => {
    const [showDeleteDialog, setShowDeleteDialog] = useState(false)
    // Sends email to user for resetting password
    // TODO
    const handleChangePassword = () => {
        alert("Password Change: Please check your email for password reset instructions.")
    }
    
    return (
        <div className={styles["account-settings"]}>
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
        </div>
    )
}
export default DeleteAccountSection;