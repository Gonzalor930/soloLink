import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';

function TeacherPublicPage() {
  const { publicId } = useParams();
  const [teacher, setTeacher] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchTeacher = async () => {
      try {
        const response = await fetch(`/public/teachers/${publicId}`);
        
        if (!response.ok) {
          if (response.status === 404) {
            throw new Error('El profesor que buscas no existe o ha sido dado de baja.');
          }
          throw new Error('Hubo un problema al cargar el perfil.');
        }

        const data = await response.json();
        setTeacher(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchTeacher();
  }, [publicId]);

  if (loading) return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
    </div>
  );

  // UX for case that the teacher doesn't exists 
  if (error) return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-50 px-4">
      <h1 className="text-6xl font-bold text-gray-300 mb-4">404</h1>
      <p className="text-xl text-gray-600 text-center mb-8">{error}</p>
      <Link to="/" className="bg-blue-600 text-white px-6 py-2 rounded-md hover:bg-blue-700 transition">
        Volver al inicio
      </Link>
    </div>
  );

  return (
    <div className="min-h-screen bg-gray-50 py-12 px-4">
      <div className="max-w-3xl mx-auto bg-white rounded-2xl shadow-xl overflow-hidden">
        {/*Profile*/}
        <div className="bg-blue-600 h-32"></div>
        <div className="px-8 pb-8">
          <div className="relative -mt-12 mb-6">
            <div className="bg-white p-2 rounded-full inline-block shadow-lg">
              <div className="w-24 h-24 bg-gray-200 rounded-full flex items-center justify-center text-3xl font-bold text-gray-500">
                {teacher.name.charAt(0)}
              </div>
            </div>
          </div>

          <h1 className="text-3xl font-bold text-gray-900 mb-2">{teacher.name}</h1>
          <p className="text-blue-600 font-medium text-lg mb-6">${teacher.pricePerHour} / hora</p>
          
          <div className="border-t border-gray-100 pt-6">
            <h2 className="text-sm font-semibold text-gray-400 uppercase tracking-wider mb-3">Sobre mí</h2>
            <p className="text-gray-700 leading-relaxed whitespace-pre-line">
              {teacher.description || "Este profesor aún no ha agregado una descripción."}
            </p>
          </div>

          {/*Reserve*/}
          <button className="w-full mt-8 bg-blue-600 text-white py-4 rounded-xl font-bold text-lg hover:bg-blue-700 transition shadow-lg shadow-blue-200">
            Ver horarios disponibles
          </button>
        </div>
      </div>
    </div>
  );
}

export default TeacherPublicPage;