import "../RegisterForm/RegisterForm.css";
import React, { useState } from "react";

function FullScreenModal({ onClose }) {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

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
    onClose(); // Close the modal

    try {
      const response = await fetch("http://localhost:8080/users/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

     
      
    } catch (error) {
      console.error("Error saving user:", error);
    }

    onClose(); // close modal after submission
  };

  return (
    //Styling for Modal to fill screen
    <div
      style={{
        position: "fixed",
        top: 0,
        left: 0,
        width: "100vw",
        height: "100vh",
        backgroundColor: "rgba(0,0,0,0.5)",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        zIndex: 9999,
      }}
    >
      <div
        style={{
          color: "#F1D3B3",
          backgroundColor: "#8B7E74",
          padding: "50px 60px",
          borderRadius: "25px",
          minWidth: "400px",
          maxWidth: "90vw",
          textAlign: "right",
        }}
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
            {/*disabled if form is incomplete */}
            <button type="submit" disabled={!isFormComplete}>
              Submit
            </button>
            <button type="button" onClick={onClose}>
              Cancel
            </button>
          </div>
        </form>
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
