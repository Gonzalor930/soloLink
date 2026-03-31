import { Routes, Route } from 'react-router-dom'
import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import TeacherPublicPage from './pages/TeacherPublicPage'

function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/dashboard" element={<Dashboard />} />
      <Route path="/:username" element={<TeacherPublicPage />} />
    </Routes>
  )
}

export default App