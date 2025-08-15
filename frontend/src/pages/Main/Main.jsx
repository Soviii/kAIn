import { useState, useEffect, createContext } from 'react';
import RecipeGallery from '../../components/RecipeGallery/RecipeGallery';
import ChatPane from '../../components/ChatPane/ChatPane';
import TabSelection from '../../components/TabSelection/TabSelection.jsx';
import RecipeDetails from '../../components/RecipeDetails/RecipeDetails.jsx';
import './Main.css';

export const RecipeContext = createContext(null);

const Main = () => {
  const [browserWidth, setBrowserWidth] = useState(window.innerWidth);
  const breakpoint = 780; // used for determining when to start stacking components
  const [currTab, setCurrTab] = useState(0); // 0 for recipes, 1 for recipe details and AI chat
  const [focusedRecipeId, setFocusedRecipeId] = useState(-1); // id is based off of database
  const [focusedRecipe, setFocusedRecipe] = useState({});
  const [isLoading, setLoading] = useState(true);
  const [recipeList, setRecipeList] = useState([
    { title: '', description: '', recipeId: '' }
  ]);

  // custom hook that gets all recipes associated with user and sets the recipes useState
  const fetchRecipeSummaries = async () => {
    // TODO DONT USE HARD CODED USER ID VALUE
    try {
      const response = await fetch('http://localhost:8080/recipes', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'userId': '1'
        },
      });
      if (response.ok) {
        const data = await response.json();
        setRecipeList(data);
      }
    } catch (error) {
      console.error('Error fetching recipes:', error);
    } finally {
      setLoading(false);
      setFocusedRecipe({});
      setFocusedRecipeId(-1);
    }
  };

  // fetches recipe summaries on first load of main page
  useEffect(() => {
    fetchRecipeSummaries();
  }, []);

  // receives updates from TabSelection component to render new view
  const handleTabSelectionClicked = (newView) => {
    setCurrTab(newView);
  }

  // when user clicks on a recipe to view, update RecipeDetails and switch to recipe details tab
  const handleRecipeCardClicked = async (recipeId) => {
    try {
      setFocusedRecipeId(recipeId);
      // setFocusedRecipeIdx(recipeIdx);
      const response = await fetch('http://localhost:8080/recipes/details', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'userId': '1',
          'recipeId': recipeId,
        },
      });

      if (response.ok) {
        const data = await response.json();
        setFocusedRecipe(data);
      }
    } catch (error) {
      console.error('Error fetching recipes:', error);
      setFocusedRecipe({
        title: "<ERROR>",
        description: "<ERROR>",
        "tags": [],
        ingredients: {},
        steps: []
      });
      setFocusedRecipeId(-1); // default to -1 in case of any API calls on other components (i.e. user tries to update broken RecipeGallery, no recipeId will have -1)
    } finally {
      setLoading(false);
      setCurrTab(1);
    }
  }

  // saves new recipe to recipeList and focuses it, then switch to recipe details tab
  const handleNewRecipeSuccess = (newRecipeId, newRecipe) => {
    setRecipeList([...recipeList, newRecipe]);
    setFocusedRecipe(newRecipeId);
    setFocusedRecipe(newRecipe);
    setCurrTab(1);
  }

  // constantly checks curr size
  useEffect(() => {
    const handleResize = () => setBrowserWidth(window.innerWidth);

    window.addEventListener("resize", handleResize);

    return () => window.removeEventListener("resize", handleResize);
  }, []);


  return (
    <RecipeContext.Provider value={{ handleRecipeCardClicked, handleNewRecipeSuccess, fetchRecipeSummaries, handleTabSelectionClicked }}>
      <div className="container-fluid">
        <TabSelection handleTabSelectionClicked={handleTabSelectionClicked} focusedTab={currTab} />
        {/* changes view based on TabSelection */}
        {currTab === 0 ? (
          <div className="recipe-gallery-div-wrapper">
            <RecipeGallery isLoading={isLoading} focusedRecipeId={focusedRecipeId} recipeList={recipeList} />
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
    </RecipeContext.Provider>
  );
};

export default Main;