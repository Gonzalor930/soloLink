import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import TeacherPublicPage from './pages/TeacherPublicPage'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import NotFound from './pages/NotFound'
function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/dashboard" element={<Dashboard />} />
      <Route path="/p/:publicId" element={<TeacherPublicPage />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  )
}

export default App