import { Link } from 'react-router-dom'

function NotFound() {
  return (
    <div className="min-h-screen bg-gray-50 flex flex-col items-center justify-center px-4">
      <div className="text-center">
        <h1 className="text-9xl font-black text-gray-200">404</h1>
        <p className="text-2xl font-bold tracking-tight text-gray-900 sm:text-4xl mt-4">
          ¡Ups! Te saliste del mapa.
        </p>
        <p className="mt-4 text-gray-500">
          La página que estás buscando no existe o fue movida.
        </p>
        <div className="mt-8 flex justify-center">
          <Link
            to="/"
            className="inline-flex items-center justify-center px-6 py-3 border border-transparent text-base font-medium rounded-md text-white bg-blue-600 hover:bg-blue-700 transition"
          >
            Volver a un lugar seguro
          </Link>
        </div>
      </div>
    </div>
  )
}

export default NotFound