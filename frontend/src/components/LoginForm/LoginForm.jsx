import "../LoginForm/LoginForm.css"
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import RegisterForm from "../RegisterForm/RegisterForm";

export default function LoginForm() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [fetchStatusMsg, setFetchStatusMsg] = useState("");

  // Validates user inputted credentials. Redirects to main page if correct
  const handleLoginClick = async () => {
    setFetchStatusMsg("Verifying... please wait");

    const response = await fetch("http://localhost:8080/users/login", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "email": email,
        "password": password
      },
    });

    if (!response.ok) {
      setFetchStatusMsg("Error code: " + response.status);
      return
    }

    setFetchStatusMsg("Correct credentials, redirecting to main page");
    setTimeout(() => {
      navigate("/main");
    }, 1500);
  }

  return (
    <div>
      <div id="login">
        <h3 className="text-center text-white pt-5">Login form</h3>
        <div className="container">
          <div id="login-row" className="row justify-content-center align-items-center">
            <div id="login-column" className="col-md-6">
              <div id="login-box" className="col-md-12">
                <div id="login-form" className="form" action="" method="post">
                  <h3 className="login">Login</h3>
                  <div className="form-group">
                    <label htmlFor="username" className="username">Email:</label><br />
                    <input type="text" name="username" id="username" className="form-control" value={email} onChange={(e) => setEmail(e.target.value)} />
                  </div>
                  <div className="form-group">
                    <label htmlFor="password" className="password">Password:</label><br />
                    <input type="text" name="password" id="password" className="form-control" value={password} onChange={(e) => setPassword(e.target.value)} />
                  </div>
                  <div className="form-group">
                    {/* <label for="remember-me" className="remember"><span>Remember me</span>   <span><input id="remember-me" name="remember-me" type="checkbox" /></span></label><br /> */}
                    <div className="flex-form-button">
                      <input name="login" className="btn btn-info btn-md" value="Login" onClick={handleLoginClick} />
                      <RegisterForm />
                    </div>
                  </div>
                  {fetchStatusMsg.length > 0 && <p>{fetchStatusMsg}</p>}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}