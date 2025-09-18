import PersonalInfoSection from '../../components/AccountSettings/PersonalInfoSection/PersonalInfoSection';
import DeleteAccountSection from '../../components/AccountSettings/DeleteAccountSection/DeleteAccountSection';
import SecuritySection from '../../components/AccountSettings/SecuritySection/SecuritySection';
import ProfileSidebar from '../../components/ProfileSidebar/ProfileSidebar';
import styles from './AccountSettings.module.css';

const AccountSettings = () => {
  return (
    <>
      <ProfileSidebar />
      <div className={styles["account-settings-div"]}>
        <PersonalInfoSection />
        <DeleteAccountSection />
        <SecuritySection /> 
      </div>
    </>
  )
}

export default AccountSettings;