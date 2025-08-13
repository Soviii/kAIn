import React, { useState } from 'react';
import { Navbar, Container, Nav } from 'react-bootstrap';
import './TabSelection.css';

const TabSelection = ({ updateMainPage }) => {
  const [currTab, setCurrTab] = useState(0); // 0 = Recipes, 1 = Recipe Details

  // updates curr tab so parent component can render necessary components
    // if currTab === 0, then show list of recipes, else show curr recipe details and AI assistant
  const onClick = (newTab) => {
    setCurrTab(newTab);
    updateMainPage(newTab);
  };

  return (
    <Navbar bg="light" className="tab-navbar">
      <Container className="tab-container">
        <Nav className="w-100 d-flex">
          <Nav.Link className={currTab === 0 ? 'curr-tab' : 'off-tab'} onClick={() => onClick(0)}>
            Recipes
          </Nav.Link>
          <Nav.Link className={currTab === 1 ? 'curr-tab' : 'off-tab'} onClick={() => onClick(1)}>
            Recipe Details
          </Nav.Link>
        </Nav>
      </Container>
    </Navbar>
  );
};

export default TabSelection;
