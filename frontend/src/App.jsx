
import { useNavigate } from "react-router-dom";

import './App.css';
import Nav from './components/Nav';
import Body from './components/Body';
import Main from './pages/Main/Main';



function App() {

  const handleLogin = () => console.log("hello Test");
  const navigate = useNavigate();
  return (
    <>
      <div>
       
        <Nav />
        <Body />
        
        <button onClick={handleLogin}>Test Click</button>
        <button onClick={() => navigate("/main")}>Go to Main Page</button>
      </div>
    </>
  );
}

export default App;
