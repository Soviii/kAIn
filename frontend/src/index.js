import React from 'react';
import { createRoot } from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import App from './App.jsx';
import NotFound from './pages/NotFound.jsx';
import Main from './pages/Main/Main.jsx';

const router = createBrowserRouter([
  {path:"/", element:<App />},
  {path:"*", element:<NotFound />},
  {path: "/main", element: <Main /> },
]);


createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);


