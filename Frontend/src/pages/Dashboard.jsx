import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'

function Dashboard() {
  const [bookings, setBookings] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const navigate = useNavigate()

  useEffect(() => {
    const token = localStorage.getItem('token')
    if (!token) {
      navigate('/')
      return
    }

    const fetchBookings = async () => {
      try {
        const response = await fetch('/api/teachers/me/bookings', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        })

        if (!response.ok) {
          if (response.status === 403) {
            localStorage.removeItem('token')
            navigate('/')
            throw new Error('Sesión expirada. Vuelve a iniciar sesión.')
          }
          throw new Error('Error al cargar las reservas')
        }

        const data = await response.json()
        setBookings(data)
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }

    fetchBookings()
  }, [navigate])

  const handleLogout = () => {
    localStorage.removeItem('token')
    navigate('/')
  }

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50 flex items-center justify-center">
        <p className="text-gray-500 font-medium animate-pulse">Cargando tu panel espacial...</p>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Navbar */}
      <nav className="bg-white shadow-sm px-6 py-4 flex justify-between items-center">
        <h1 className="text-2xl font-bold text-blue-600">SoloLink</h1>
        <button 
          onClick={handleLogout}
          className="text-sm font-medium text-gray-600 hover:text-red-600 transition"
        >
          Cerrar Sesión
        </button>
      </nav>

      {/* Content */}
      <main className="max-w-5xl mx-auto mt-8 px-6">
        <header className="mb-8">
          <h2 className="text-3xl font-bold text-gray-800">Tus Reservas</h2>
          <p className="text-gray-500 mt-1">Gestiona tus próximas clases y alumnos.</p>
        </header>

        {error && (
          <div className="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-6">
            {error}
          </div>
        )}

        {/*List*/}
        <div className="bg-white rounded-lg shadow overflow-hidden">
          {bookings.length === 0 ? (
            <div className="p-8 text-center text-gray-500">
              Todavía no tienes reservas. ¡Comparte tu link público para empezar!
            </div>
          ) : (
            <ul className="divide-y divide-gray-200">
              {bookings.map((booking) => (
                <li key={booking.id} className="p-6 flex items-center justify-between hover:bg-gray-50 transition">
                  <div>
                    <p className="text-lg font-medium text-gray-900">{booking.studentName}</p>
                    <p className="text-sm text-gray-500 flex items-center mt-1">
                      <span className="mr-3">📅 {new Date(booking.startTime).toLocaleDateString()}</span>
                      <span>⏰ {new Date(booking.startTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</span>
                    </p>
                  </div>
                  
                  {/* Badge de Estado */}
                  <div>
                    <span className={`px-3 py-1 inline-flex text-xs leading-5 font-semibold rounded-full 
                      ${booking.status === 'CONFIRMED' ? 'bg-green-100 text-green-800' : 
                        booking.status === 'PENDING' ? 'bg-yellow-100 text-yellow-800' : 
                        'bg-red-100 text-red-800'}`}>
                      {booking.status}
                    </span>
                  </div>
                </li>
              ))}
            </ul>
          )}
        </div>
      </main>
    </div>
  )
}

export default Dashboard