import React, { useState } from 'react';
import RecipeGallery from '../../components/RecipeGallery/RecipeGallery';
import ChatPane from '../../components/ChatPane/ChatPane';
import TopNavBar from '../../components/TopNavbar/TopNavbar.jsx';
import TabSelection from '../../components/TabSelection/TabSelection.jsx';
import RecipeDetails from '../../components/RecipeDetails/RecipeDetails.jsx';
import './Main.css';

const Main = () => {
  const [currView, setCurrView] = useState(0); // 0 for recipes, 1 for recipe details and AI chat

  // receives updates from TabSelection component to render new view
  const updateMainPage = (newView) => {
    setCurrView(newView);
  }

  return (
    <div className="container-fluid">
      <TabSelection updateMainPage={updateMainPage} />
      {/* changes view based on TabSelection */}
      {currView === 0 ? (
        <RecipeGallery />
      ) : (
        <div className="container-fluid">
          <div className="row min-vh-100">
            <div className="col-12 col-md-6 d-flex flex-column p-4 border-end bg-white">
              <RecipeDetails />
            </div>
            <div className="col-12 col-md-6 d-flex flex-column p-4 bg-light">
              <ChatPane />
            </div>
          </div>
        </div>
      )}

    </div>
  );
};

export default Main;