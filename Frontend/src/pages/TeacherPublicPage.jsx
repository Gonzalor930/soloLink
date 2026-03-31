import { useParams } from 'react-router-dom'

function TeacherPublicPage() {
  const { username } = useParams()

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-2xl mx-auto p-6">
        <div className="bg-white rounded-lg shadow-md p-8 text-center mb-6">
          <div className="w-20 h-20 bg-blue-100 rounded-full mx-auto mb-4 flex items-center justify-center">
            <span className="text-2xl font-bold text-blue-600">{username?.[0]?.toUpperCase()}</span>
          </div>
          <h1 className="text-2xl font-bold text-gray-800 mb-1">{username}</h1>
          <p className="text-gray-500">Teacher</p>
        </div>

        <div className="bg-white rounded-lg shadow-md p-6">
          <h2 className="text-lg font-bold text-gray-800 mb-4">Available slots</h2>
          <p className="text-gray-500">No availability set yet.</p>
        </div>
      </div>
    </div>
  )
}

export default TeacherPublicPage