import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import AboutUs from './pages/AboutUs/AboutUs.jsx';
import ForgotPassword from './pages/ForgotPassword/ForgotPassword.jsx';
import LoginAndRegister from './pages/LoginAndRegister/LoginAndRegister.jsx';
import Main from './pages/Main/Main.jsx';
import NotFound from './pages/NotFound/NotFound.jsx';
import Profile from './pages/Profile/Profile.jsx';
import ResetPassword from './pages/ResetPassword/ResetPassword.jsx';
import TopNavbar from './components/TopNavbar/TopNavbar.jsx';

function App() {
  const router = createBrowserRouter([
    {
      path: "/login",
      element: (
        <>
          <TopNavbar />
          <LoginAndRegister />
        </>
      )
    },
    {
      path: "/forgot-password",
      element: (
        <>
          <TopNavbar />
          <ForgotPassword />
        </>
      )
    },
    {
      path: "/main",
      element: (
        <>
          <TopNavbar />
          <Main />
        </>
      )
    },
    {
      path: "/not-found",
      element: (
        <>
          <TopNavbar />
          <NotFound />
        </>
      )
    },
    {
      path: "/profile",
      element: (
        <>
          <TopNavbar />
          <Profile />
        </>
      )
    },
    {
      path: "/reset-password",
      element: (
        <>
          <TopNavbar />
          <ResetPassword />
        </>
      )
    },
    {
      path: "/about-us",
      element: (
        <>
          <TopNavbar />
          <AboutUs />
        </>
      )
    },
    {
      path: "/*",
      element: (
        <>
          <TopNavbar />
          <NotFound />
        </>
      )
    }
  ])

  return (
    <React.StrictMode>
      <RouterProvider router={router} />
    </React.StrictMode>
  )
}

export default App;
