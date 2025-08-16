import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import AboutUs from './pages/AboutUs/AboutUs.jsx';
import ForgotPassword from './pages/ForgotPassword/ForgotPassword.jsx';
import LoginAndRegister from './pages/LoginAndRegister/LoginAndRegister.jsx';
import Main from './pages/Main/Main.jsx';
import NotFound from './pages/NotFound/NotFound.jsx';
import AccountSettings from './pages/AccountSettings/AccountSettings.jsx';
import ResetPassword from './pages/ResetPassword/ResetPassword.jsx';
import TopNavbar from './components/TopNavbar/TopNavbar.jsx';
import Privacy from './pages/Privacy/Privacy.jsx';
import PrivateRoute from './components/PrivateRoute/PrivateRouter.jsx';
import { AuthProvider } from './context/AuthContext.jsx';

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
        <PrivateRoute>
          <TopNavbar />
          <Main />
        </PrivateRoute>
      )
    },
    {
      path: "/account",
      element: (
        <PrivateRoute>
          <TopNavbar />
          <AccountSettings />
        </PrivateRoute>
      )
    },
    {
      path: "/privacy",
      element: (
        <PrivateRoute>
          <TopNavbar />
          <Privacy />
        </PrivateRoute>
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
  ]);

  return (
    <React.StrictMode>
      <AuthProvider>
        <RouterProvider router={router} />
      </AuthProvider>
    </React.StrictMode>
  );
}

export default App;