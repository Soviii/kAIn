import styles from "./LoginForm.module.css";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import RegisterForm from "../RegisterForm/RegisterForm";
import { useAuth } from "../../context/AuthContext.jsx";

export default function LoginForm() {
  const { setUserId, setIsAuthenticated } = useAuth();
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [fetchStatusMsg, setFetchStatusMsg] = useState("");


  const handleLoginClick = async () => {
    setFetchStatusMsg("Verifying... please wait");

    const response = await fetch("http://localhost:8080/users/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include",
      body: JSON.stringify({
        email: email,
        password: password,
      }),
    });

    if (!response.ok) {
      setFetchStatusMsg("Error code: " + response.status);
      return;
    }

    if (response.ok) {
      const data = await response.json();
      setUserId(data["userId"]);
      setIsAuthenticated(true);
      setFetchStatusMsg("Correct credentials, redirecting...");
      // slight delay to show user the success message
      await new Promise((resolve) => setTimeout(resolve, 1250));
      navigate("/main", { replace: true });
    }
  };

  return (
    <div className={styles["login-page"]}>
      <div className={styles["login-card"]}>
        <h2 className={styles["login-title"]}>Welcome Back</h2>
        <p className={styles["login-subtitle"]}>Please sign in to continue</p>

        <div className={styles["form-group"]}>
          <label className={styles["label"]} htmlFor="email">Email</label>
          <input
            id="email"
            type="email"
            className={styles["input"]}
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="you@example.com"
          />
        </div>

        <div className={styles["form-group"]}>
          <label className={styles["label"]} htmlFor="password">Password</label>
          <input
            id="password"
            type="password"
            className={styles["input"]}
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="••••••••"
          />
        </div>

        <div className={styles["button-row"]}>
          <button
            className={styles["btn-primary"]}
            onClick={handleLoginClick}
            disabled={email.length === 0 || password.length === 0}
          >
            Login
          </button>
          <RegisterForm />
        </div>

        {fetchStatusMsg && (
          <p className={styles["status-msg"]}>{fetchStatusMsg}</p>
        )}
      </div>
    </div>
  );
}
