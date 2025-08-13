import { useState, useEffect } from 'react';
import RecipeGallery from '../../components/RecipeGallery/RecipeGallery';
import ChatPane from '../../components/ChatPane/ChatPane';
import TabSelection from '../../components/TabSelection/TabSelection.jsx';
import RecipeDetails from '../../components/RecipeDetails/RecipeDetails.jsx';
import './Main.css';

const Main = () => {
  const [browserWidth, setBrowserWidth] = useState(window.innerWidth);
  const breakpoint = 780; // used for determining when to start stacking components
  const [currTab, setCurrTab] = useState(0); // 0 for recipes, 1 for recipe details and AI chat
  const [focusedRecipe, setFocusedRecipe] = useState({});
  const [focusedRecipeIdx, setFocusedRecipeIdx] = useState(-1);

  // receives updates from TabSelection component to render new view
  const handleTabSelectionClicked = (newView) => {
    setCurrTab(newView);
  }

  // when user clicks on a recipe to view, update RecipeDetails and switch to recipe details tab
  const updateMainPageWithHighlightedRecipe = (recipe, recipeIdx) => {
    setFocusedRecipe(recipe);
    setFocusedRecipeIdx(recipeIdx);
    setCurrTab(1); // switch to recipe details tab
  }

  // constantly checks curr size
  useEffect(() => {
    const handleResize = () => setBrowserWidth(window.innerWidth);

    window.addEventListener("resize", handleResize);

    return () => window.removeEventListener("resize", handleResize);
  }, []);

  
  return (
    <div className="container-fluid">
      <TabSelection handleTabSelectionClicked={handleTabSelectionClicked} focusedTab={currTab} />
      {/* changes view based on TabSelection */}
      {currTab === 0 ? (
        <div className="recipe-gallery-div-wrapper">
          <RecipeGallery updateMainPageWithHighlightedRecipe={updateMainPageWithHighlightedRecipe} focusedRecipeIdx={focusedRecipeIdx} />
        </div>
      ) : (
        <>
          {browserWidth > breakpoint ? (
            <div>
              <div className="recipe-chat-and-details-div">
                <div className="recipe-details-col">
                  <RecipeDetails recipe={focusedRecipe} />
                </div>
                <div className="ai-chat-col">
                  <ChatPane />
                </div>
              </div>
            </div>
          ) : (
            <div>
              <RecipeDetails recipe={focusedRecipe} />
              <ChatPane />
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default Main;