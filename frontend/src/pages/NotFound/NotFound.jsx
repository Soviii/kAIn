import { Link } from "react-router-dom";

const NotFound = () => {
  return (
    <div>
      <h2>Not Found</h2>
      <Link to={"/home"}>
        <button>Return to Main Page</button>
      </Link>

    </div>
  )
}

export default NotFound;