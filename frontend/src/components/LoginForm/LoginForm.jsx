import "../LoginForm/LoginForm.css"
import { useNavigate } from "react-router-dom";

export default function LoginForm() {

    const handleLogin = () => console.log("hello Test");
    const navigate = useNavigate(); 
  return (


<div>
    <div id="login">
        <h3 className="text-center text-white pt-5">Login form</h3>
        <div className="container">
            <div id="login-row" className="row justify-content-center align-items-center">
                <div id="login-column" className="col-md-6">
                    <div id="login-box" className="col-md-12">
                        <form id="login-form" className="form" action="" method="post">
                            <h3 className="login">Login</h3>
                            <div className="form-group">
                                <label for="username" className="username">Username:</label><br />
                                <input type="text" name="username" id="username" className="form-control" />
                            </div>
                            <div className="form-group">
                                <label for="password" className="password">Password:</label><br />
                                <input type="text" name="password" id="password" className="form-control" />
                            </div>
                            <div className="form-group">
                                <label for="remember-me" className="remember"><span>Remember me</span> <span><input id="remember-me" name="remember-me" type="checkbox" /></span></label><br />
                                <div className="flex-form-button">
                                    <input  name="login" className="btn btn-info btn-md" value="Login" onClick={handleLogin} />
                                    <input  name="register" className="btn btn-info btn-md" value="Register" onClick={() => navigate("/main")} />
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
  );
}