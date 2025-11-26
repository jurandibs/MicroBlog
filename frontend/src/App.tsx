import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Posts from './pages/Posts';
import ProtectedRoute from './components/ProtectedRoute';

function App() {
  const token = localStorage.getItem('token');

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route element={<ProtectedRoute />}>
          <Route path="/posts" element={<Posts />} />
        </Route>
        <Route path="/" element={<Navigate to={token ? "/posts" : "/login"} />} />
      </Routes>
    </Router>
  );
}

export default App;
