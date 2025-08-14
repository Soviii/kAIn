import AccountSettingsForm from '../../components/AccountSettingsForm/AccountSettingsForm';
import ProfileSidebar from '../../components/ProfileSidebar/ProfileSidebar';
import styles from './AccountSettings.module.css';

const AccountSettings = () => {
  return (
    <>
      <ProfileSidebar />
      <div className={styles["account-settings-div"]}>
        <AccountSettingsForm />
      </div>
    </>
  )
}

export default AccountSettings;