import styles from "../RegisterForm/RegisterForm.module.css";
import { useState } from "react";
import { useAuth } from "../../context/AuthContext";

function FullScreenModal({ onClose }) {
  const { setUserId, setIsAuthenticated } = useAuth();
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });
  const [status, setStatus] = useState("");

  //check if fields are filled
  const isFormComplete =
    formData.firstName?.trim() &&
    formData.lastName?.trim() &&
    formData.email?.trim() &&
    formData.password?.trim();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault(); // prevent default form submission
    // onClose(); // Close the modal
    setStatus("Registering...");
    try {
      const response = await fetch("http://localhost:8080/users/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
        credentials: "include"
      });
      
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const data = response.json();
      setUserId(data["userId"]);
      setIsAuthenticated(true);
      setStatus("Successfully registered!");
      
    } catch (error) {
      console.error("Error saving user:", error);
      setStatus("There was an error registering");
    } finally {
      await new Promise(resolve => setTimeout(resolve, 1250));
      setStatus("");
    }

    onClose(); // close modal after submission
  };

  return (
    //Styling for Modal to fill screen
    <div className={styles["register-container-div"]}

    >
      <div className={styles["register-form-div"]}

        //end of stylings **************************
      >
        <form onSubmit={handleSubmit}>
          <h3>Register New User</h3>
          <div>
            <label>First Name:</label>
            <input
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label>Last Name:</label>
            <input
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label>Email:</label>
            <input
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            <label>password:</label>
            <input
              name="password"
              type="password"
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>
          <div>
            {status === "" && (
              <>
                {/*disabled if form is incomplete */}
                <button type="submit" disabled={!isFormComplete}>
                  Submit
                </button>
                <button type="button" onClick={onClose}>
                  Cancel
                </button>
              </>
            )
            }
          </div>
        </form>
        <p>{status}</p>
      </div>
    </div>
  );
}

export default function App() {
  const [showModal, setShowModal] = useState(false);

  return (
    <div>
      <button
        className="btn btn-info btn-md"
        type="button"
        onClick={() => setShowModal(true)}
      >
        Register
      </button>
      {showModal && <FullScreenModal onClose={() => setShowModal(false)} />}
    </div>
  );
}
