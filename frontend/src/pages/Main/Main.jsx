import React, { useState } from 'react';
import RecipeGallery from '../../components/RecipeGallery/RecipeGallery';
import ChatPane from '../../components/ChatPane/ChatPane';
import TopNavBar from '../../components/TopNavbar/TopNavbar.jsx';
import './Main.css';

const Main = () => {

  return (
    <div className="container-fluid">
      <TopNavBar />
        <div className="row min-vh-100">
          <div className="col-md-6 d-flex flex-column p-4 border-end bg-white">
            <RecipeGallery />
          </div>
          <div className="col-md-6 d-flex flex-column p-4 bg-light">
            <ChatPane />
          </div>
        </div>
    </div>
  );
};

export default Main;