function Dashboard() {
  return (
    <div className="min-h-screen bg-gray-50">
      <nav className="bg-white shadow-sm px-6 py-4 flex justify-between items-center">
        <h1 className="text-xl font-bold text-gray-800">SoloLink</h1>
        <button className="text-sm text-gray-500 hover:text-gray-700">Log out</button>
      </nav>
      <div className="max-w-4xl mx-auto p-6">
        <h2 className="text-2xl font-bold text-gray-800 mb-6">Dashboard</h2>
        <p className="text-gray-500">Welcome! Here you'll manage your availability and bookings.</p>
      </div>
    </div>
  )
}

export default Dashboard