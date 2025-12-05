import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Posts from './pages/Posts';
import ProtectedRoute from './components/ProtectedRoute';

function HomeRedirect() {
  const token = localStorage.getItem('token');
  return <Navigate to={token ? "/posts" : "/login"} />;
}

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route element={<ProtectedRoute />}>
          <Route path="/posts" element={<Posts />} />
        </Route>
        <Route path="/" element={<HomeRedirect />} />
      </Routes>
    </Router>
  );
}

export default App;
