import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';

function TeacherPublicPage() {
  const { publicId } = useParams();
  const [teacher, setTeacher] = useState(null);
  const [slots, setSlots] = useState([]);
  const [loadingSlots, setLoadingSlots] = useState(false);
  const [showSlots, setShowSlots] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchTeacher = async () => {
      try {
        const response = await fetch(`/public/teachers/${publicId}`);
        if (!response.ok) {
          if (response.status === 404) throw new Error('El profesor que buscas no existe.');
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

  const handleLoadSlots = async () => {
    setShowSlots(true);
    setLoadingSlots(true);

    try {
      const today = new Date();
      const year = today.getFullYear();
      const month = String(today.getMonth() + 1).padStart(2, '0');
      const day = String(today.getDate()).padStart(2, '0');
      const dateString = `${year}-${month}-${day}`;

      const response = await fetch(`/public/teachers/${publicId}/slots?date=${dateString}`);
      
      if (!response.ok) {
        const errorMsg = await response.text();
        console.error("Respuesta api:", errorMsg);
        throw new Error('El servidor rechazó la petición.');
      }
      
      const data = await response.json();
      setSlots(data);
    } catch (err) {
      alert(err.message); 
      setShowSlots(false); 
    } finally {
      setLoadingSlots(false);
    }
  };

  if (loading) return <div className="min-h-screen flex items-center justify-center bg-gray-50"><div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div></div>;

  if (error) return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-50 px-4">
      <h1 className="text-6xl font-bold text-gray-300 mb-4">404</h1>
      <p className="text-xl text-gray-600 text-center mb-8">{error}</p>
      <Link to="/" className="bg-blue-600 text-white px-6 py-2 rounded-md hover:bg-blue-700 transition">Volver al inicio</Link>
    </div>
  );

  return (
    <div className="min-h-screen bg-gray-50 py-12 px-4">
      <div className="max-w-3xl mx-auto bg-white rounded-2xl shadow-xl overflow-hidden">
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

          <div className="mt-8 border-t border-gray-100 pt-6">
            {!showSlots ? (
              <button 
                onClick={handleLoadSlots}
                className="w-full bg-blue-600 text-white py-4 rounded-xl font-bold text-lg hover:bg-blue-700 transition shadow-lg shadow-blue-200"
              >
                Ver horarios disponibles
              </button>
            ) : (
              <div>
                <h3 className="text-xl font-bold text-gray-800 mb-4">Próximos Turnos</h3>
                
                {loadingSlots ? (
                  <p className="text-center text-gray-500 animate-pulse">Buscando en la agenda...</p>
                ) : slots.length === 0 ? (
                  <p className="text-center text-gray-500 bg-gray-50 py-4 rounded-lg">
                    No hay turnos disponibles por el momento.
                  </p>
                ) : (
                  <div className="grid grid-cols-2 sm:grid-cols-3 gap-3">
                    {slots.map((slot, index) => (
                      <button 
                        key={index}
                        className="border border-blue-200 bg-blue-50 hover:bg-blue-600 hover:text-white text-blue-800 transition rounded-lg py-3 flex flex-col items-center justify-center"
                        onClick={() => alert(`En la próxima fase, esto abrirá el formulario para reservar el ${new Date(slot.startTime).toLocaleDateString()} a las ${new Date(slot.startTime).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}`)}
                      >
                        <span className="font-bold text-sm">
                          {new Date(slot.startTime).toLocaleDateString('es-ES', { weekday: 'short', day: 'numeric', month: 'short' })}
                        </span>
                        <span className="text-lg">
                          {new Date(slot.startTime).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                        </span>
                      </button>
                    ))}
                  </div>
                )}
              </div>
            )}
          </div>

        </div>
      </div>
    </div>
  );
}

export default TeacherPublicPage;