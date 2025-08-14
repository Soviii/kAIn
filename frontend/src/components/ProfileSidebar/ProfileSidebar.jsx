import { User, Shield } from "lucide-react"
import { NavLink } from "react-router-dom"
import styles from "./ProfileSidebar.module.css" // modular to not mess with other components' css

const ProfileSidebar = () => {
  const items = [
    { title: "Account", url: "/account", icon: User },
    { title: "Privacy", url: "/privacy", icon: Shield },
  ]
  return (
    <div className={`${styles["profile-sidebar"]} open`}> {/* TODO: implement collapse option; css is already there */}
      <nav className={styles["profile-nav"]}>
        <div className={styles["nav-group"]}>
          <div className={styles["nav-label"]}>Settings</div>
          <ul className={styles["nav-menu"]}>
            {items.map((item) => (
              <li key={item.title} className={styles["nav-item"]}>
                <NavLink
                  to={item.url}
                  end
                  className={styles["nav-link"]}
                >
                  <item.icon className={styles["nav-icon"]} />
                  {true && <span className={styles["nav-text"]}>{item.title}</span>}
                </NavLink>
              </li>
            ))}
          </ul>
        </div>
      </nav>
    </div>
  )
}

export default ProfileSidebar;