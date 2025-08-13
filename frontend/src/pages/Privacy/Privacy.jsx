import PrivacySettings from "../../components/PrivacySettings/PrivacySettings";
import ProfileSidebar from "../../components/ProfileSidebar/ProfileSidebar";
import styles from "./Privacy.module.css";

const Privacy = () => {
  return (
    <>
      <ProfileSidebar />
      <div className={styles["privacy-settings-div"]}>
        <PrivacySettings />
      </div>
    </>
  )
};

export default Privacy;