import React, { useState } from 'react';
import { Navbar, Container, Nav, NavDropdown } from 'react-bootstrap';
import kainLogo from "../../assets/kAIn-logo-v2.jpeg";
import "./TopNavbar.css";


// TODO: update img with sprite of luffy or continue using current logo
  // may need to color match background of jpg/png with color for navbar...
const TopNavbar = () => {
  return (
    <Navbar bg="light" expand="lg">
      <Container>
        <Navbar.Brand className="fw-bold" href="/main">
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
            <Nav.Link href="#home">Home</Nav.Link>
            <Nav.Link href="#about-us">About Us</Nav.Link>
            <Nav.Link href="#profile">Profile</Nav.Link>
            <Nav.Link href="#sign-out">Sign Out</Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default TopNavbar;