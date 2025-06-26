
import './App.css';
import Nav from './components/Nav';
import Body from './components/Body';




function App() {

  const handleLogin = () => console.log("hello Test");

  return (
    <>
      <div>
       
        <Nav />
        <Body />
        
        <button onClick={handleLogin}>Test Click</button>
      </div>
    </>
  );
}

export default App;
