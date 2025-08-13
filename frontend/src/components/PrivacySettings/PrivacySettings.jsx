import { useState } from "react"
import { Shield, MessageSquare, Trash2, Database } from "lucide-react"
import styles from "./PrivacySettings.module.css"

const PrivacySettings = () => {
  const [chatHistory, setChatHistory] = useState(127)
  const [showClearDialog, setShowClearDialog] = useState(false)

  const handleClearChatHistory = () => {
    setChatHistory(0)
    setShowClearDialog(false)
    alert("Chat History Cleared: All your AI chat conversations have been permanently deleted.")
  }

  return (
    <div className={styles["privacy-settings"]}>
      <div className={styles["settings-header"]}>
        <h1>Privacy Settings</h1>
        <p>Control your data and privacy preferences.</p>
      </div>

      <div className={styles["settings-card"]}>
        <div className={styles["card-header"]}>
          <div className={styles["card-title"]}>
            <MessageSquare className={styles["title-icon"]} />
            AI Chat History
          </div>
          <p className={styles["card-description"]}>
            Manage your conversation data with AI assistants.
          </p>
        </div>
        <div className={styles["card-content"]}>
          <div className={styles["info-item"]}>
            <div className={styles["info-content"]}>
              <Database className={styles["info-icon"]} />
              <div>
                <p className={styles["info-title"]}>Stored Conversations</p>
                <p className={styles["info-subtitle"]}>{chatHistory} chat sessions saved</p>
              </div>
            </div>
          </div>

          <button 
            onClick={() => setShowClearDialog(true)}
            className={`${styles["btn"]} ${styles["btn-outline"]} ${styles["btn-destructive"]} ${styles["btn-full"]}`}
          >
            <Trash2 className={styles["btn-icon"]} />
            Clear All Chat History
          </button>
        </div>
      </div>

      <div className={styles["settings-card"]}>
        <div className={styles["card-header"]}>
          <div className={styles["card-title"]}>
            <Shield className={styles["title-icon"]} />
            Data Protection
          </div>
          <p className={styles["card-description"]}>
            Your privacy and data security information.
          </p>
        </div>
        <div className={styles["card-content"]}>
          <div className={styles["protection-items"]}>
            <div className={styles["protection-item"]}>
              <Shield className={styles["protection-icon"]} />
              <div>
                <p className={styles["protection-title"]}>End-to-End Encryption</p>
                <p className={styles["protection-subtitle"]}>Your conversations are encrypted and secure.</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      {showClearDialog && (
        <div className={styles["dialog-overlay"]}>
          <div className={styles["dialog"]}>
            <div className={styles["dialog-header"]}>
              <h3 className={styles["dialog-title"]}>Clear Chat History?</h3>
              <p className={styles["dialog-description"]}>
                This will permanently delete all your AI chat conversations. This action cannot be undone.
              </p>
            </div>
            <div className={styles["dialog-footer"]}>
              <button 
                onClick={() => setShowClearDialog(false)}
                className={`${styles["btn"]} ${styles["btn-outline"]}`}
              >
                Cancel
              </button>
              <button 
                onClick={handleClearChatHistory}
                className={`${styles["btn"]} ${styles["btn-destructive"]}`}
              >
                Clear History
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
};

export default PrivacySettings;