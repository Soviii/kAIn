import React from 'react';
import { createRoot } from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import App from './App.jsx';
import NotFound from './pages/NotFound.jsx';

const router = createBrowserRouter([
  {path:"/", element:<App />},
  {path:"*", element:<NotFound />},
]);


createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);


