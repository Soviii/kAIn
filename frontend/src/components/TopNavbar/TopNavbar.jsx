import React, { useEffect, useState } from 'react';
import { Navbar, Container, Nav, NavDropdown } from 'react-bootstrap';
import kainLogo from "../../assets/kAIn-logo-v2.jpeg";
import "./TopNavbar.css";


// TODO: update img with sprite of luffy or continue using current logo
// may need to color match background of jpg/png with color for navbar...
const TopNavbar = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // signs the user out 
    // TODO: handle JWT as well... maybe remove ???
  const handleSignOut = () => {
    setIsLoggedIn(!isLoggedIn);
  }

  // checks if current JWT is still valid
  // TODO: implement JWT and check JWT ...
  const checkIfLoggedIn = () => {
    setIsLoggedIn(true); // temporary; for testing
    return;
  }

  // for checking if logged in - renders different options based on logged in status
  useEffect(() => {
    checkIfLoggedIn();
  }, [])

  return (
    <Navbar bg="light" expand="lg">
      <Container>
        <Navbar.Brand className="fw-bold" href="/home">
          <img src={kainLogo}
            className="logo-img"
            width="30"
            alt=""
          />
          kAIn
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto"> {/* ms-auto (margin start auto) allows nav links align right while brand stays on the left */}
            {isLoggedIn === true ?
              <>
                <Nav.Link href="/about-us">About Us</Nav.Link>
                <Nav.Link href="/profile">Profile</Nav.Link>
                <Nav.Link onClick={handleSignOut}>Sign Out</Nav.Link>
              </>
              :
              <>
                <Nav.Link href="/about-us">About Us</Nav.Link>
                <Nav.Link href="/login">Login/Register</Nav.Link>
                <Nav.Link href="/reset-password">Reset Password</Nav.Link>
              </>
            }
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default TopNavbar;