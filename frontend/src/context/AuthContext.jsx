import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(null); // starts null to have loading screen
  const [loading, setLoading] = useState(true);
  const [userId, setUserId] = useState(-1); // -1 meaning none

  const validateToken = async () => {
    try {
      const response = await fetch("http://localhost:8080/users/validate", {
        method: "GET",
        credentials: "include"
      });

      if (response.ok) {
        setIsAuthenticated(true);
      } else {
        setIsAuthenticated(false);
        setUserId(-1);
      }
    } catch (err) {
      setIsAuthenticated(false);
      setUserId(-1);
    } finally {
      setLoading(false);
    }
  };

  // validates jwtw every 5 minutes
  useEffect(() => {
    validateToken(); // validates on initial render/mount

    const interval = setInterval(validateToken, 5 * 60 * 1000); // Re-validate every 5 minutes (ms)
    return () => {console.log('clearInterval()'); clearInterval(interval)};
  }, []);

  return (
    <AuthContext.Provider value={{ isAuthenticated, setIsAuthenticated, validateToken, userId, setUserId }}>
      {!loading && children}
    </AuthContext.Provider>
  );
};
