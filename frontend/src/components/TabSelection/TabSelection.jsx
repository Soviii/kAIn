import React, { useState } from 'react';
import { Navbar, Container, Nav } from 'react-bootstrap';
import './TabSelection.css';

const TabSelection = ({ handleTabSelectionClicked, focusedTab }) => {
  // updates curr tab so parent component can render necessary components
    // if currTab === 0, then show list of recipes, else show curr recipe details and AI assistant
  const onClick = (newTab) => {
    handleTabSelectionClicked(newTab);
  };

  return (
    <Navbar bg="light" className="tab-navbar">
      <Container className="tab-container">
        <Nav className="w-100 d-flex">
          <Nav.Link className={focusedTab === 0 ? 'curr-tab' : 'off-tab'} onClick={() => onClick(0)}>
            Recipes
          </Nav.Link>
          <Nav.Link className={focusedTab === 1 ? 'curr-tab' : 'off-tab'} onClick={() => onClick(1)}>
            Details
          </Nav.Link>
        </Nav>
      </Container>
    </Navbar>
  );
};

export default TabSelection;
