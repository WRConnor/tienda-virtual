import { useEffect, useState } from "react";
import { api } from "../../../api/api";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";
import "../../../css/global.css";

function ListadoUsuarios() {

  const [usuarios, setUsuarios] = useState([]);
  const [busqueda, setBusqueda] = useState("");
  const [pagina, setPagina] = useState(1);
  const [ordenCampo, setOrdenCampo] = useState("idUsuario");
  const [ordenAsc, setOrdenAsc] = useState(true);

  const filasPorPagina = 8;

  const cargarUsuarios = async () => {
    try {

      const data = await api.obtenerUsuarios();

      console.log("Respuesta backend:", data);

      if (Array.isArray(data)) {
        setUsuarios(data);
      } else if (Array.isArray(data.data)) {
        setUsuarios(data.data);
      } else if (Array.isArray(data.usuarios)) {
        setUsuarios(data.usuarios);
      } else {
        setUsuarios([]);
      }

    } catch (err) {
      console.error(err);
      setUsuarios([]);
    }
  };

  useEffect(() => {
    cargarUsuarios();
  }, []);

  // FILTRAR
  const usuariosFiltrados = (Array.isArray(usuarios) ? usuarios : []).filter((u) =>
    u.nombreUsuario?.toLowerCase().includes(busqueda.toLowerCase()) ||
    u.usuario?.toLowerCase().includes(busqueda.toLowerCase()) ||
    String(u.cedulaUsuario).includes(busqueda)
  );

  // ORDENAR
  const usuariosOrdenados = [...usuariosFiltrados].sort((a, b) => {

    const valorA = a[ordenCampo];
    const valorB = b[ordenCampo];

    if (valorA < valorB) return ordenAsc ? -1 : 1;
    if (valorA > valorB) return ordenAsc ? 1 : -1;
    return 0;

  });

  // PAGINACION
  const totalPaginas = Math.ceil(usuariosOrdenados.length / filasPorPagina);

  const inicio = (pagina - 1) * filasPorPagina;
  const fin = inicio + filasPorPagina;

  const usuariosPagina = usuariosOrdenados.slice(inicio, fin);

  const ordenarPor = (campo) => {

    if (ordenCampo === campo) {
      setOrdenAsc(!ordenAsc);
    } else {
      setOrdenCampo(campo);
      setOrdenAsc(true);
    }

  };

  // ===== EXPORTAR CSV =====
  const exportarCSV = () => {

    const fecha = new Date().toISOString().split("T")[0];

    const encabezado = "ID;Cédula;Nombre;Email;Usuario\n";

    const filas = usuariosOrdenados
      .map(u =>
        `${u.idUsuario};${u.cedulaUsuario};${u.nombreUsuario};${u.emailUsuario};${u.usuario}`
      )
      .join("\n");

    const BOM = "\uFEFF";

    const blob = new Blob(
      [BOM + encabezado + filas],
      { type: "text/csv;charset=utf-8;" }
    );

    const url = URL.createObjectURL(blob);

    const link = document.createElement("a");
    link.href = url;
    link.download = `reporte_usuarios_${fecha}.csv`;
    link.click();

    URL.revokeObjectURL(url);
  };

  // ===== EXPORTAR PDF =====
  const exportarPDF = () => {

    const doc = new jsPDF();

    doc.setFontSize(18);
    doc.text("Listado de Usuarios", 14, 20);

    doc.setFontSize(12);
    doc.text(`Fecha: ${new Date().toLocaleDateString()}`, 14, 30);

    autoTable(doc, {
      startY: 40,
      head: [["ID", "Cédula", "Nombre", "Email", "Usuario"]],
      body: usuariosOrdenados.map(u => [
        u.idUsuario,
        u.cedulaUsuario,
        u.nombreUsuario,
        u.emailUsuario,
        u.usuario
      ]),
    });

    const fecha = new Date();
    const fechaFormateada = fecha.toISOString().split("T")[0];

    doc.save(`reporte_usuarios_${fechaFormateada}.pdf`);
  };

  return (
    <div className="reporte-seccion">

      <h2 className="titulo-reporte">Listado de Usuarios</h2>

      {/* BUSCADOR */}

      <input
        className="input-busqueda"
        type="text"
        placeholder="Buscar usuario..."
        value={busqueda}
        onChange={(e) => {
          setBusqueda(e.target.value);
          setPagina(1);
        }}
      />

      <div style={{marginBottom:"10px"}}>

        <button onClick={exportarCSV}>
          Exportar CSV
        </button>

        <button onClick={exportarPDF} style={{marginLeft:"10px"}}>
          Exportar PDF
        </button>

      </div>

      <div className="tabla-container">

        <table className="tabla-reporte">

          <thead>
            <tr>
              <th onClick={() => ordenarPor("idUsuario")}>ID</th>
              <th onClick={() => ordenarPor("cedulaUsuario")}>Cédula</th>
              <th onClick={() => ordenarPor("nombreUsuario")}>Nombre</th>
              <th onClick={() => ordenarPor("emailUsuario")}>Email</th>
              <th onClick={() => ordenarPor("usuario")}>Usuario</th>
            </tr>
          </thead>

          <tbody>

            {usuariosPagina.length === 0 ? (
              <tr>
                <td colSpan="5" className="sin-datos">
                  No se encontraron usuarios
                </td>
              </tr>
            ) : (
              usuariosPagina.map((u) => (
                <tr key={u.idUsuario}>
                  <td>{u.idUsuario}</td>
                  <td>{u.cedulaUsuario}</td>
                  <td>{u.nombreUsuario}</td>
                  <td>{u.emailUsuario}</td>
                  <td>{u.usuario}</td>
                </tr>
              ))
            )}

          </tbody>

        </table>

      </div>

      {/* PAGINACION */}

      <div className="paginacion">

        <button
          disabled={pagina === 1}
          onClick={() => setPagina(pagina - 1)}
        >
          ◀
        </button>

        <span>
          Página {pagina} de {totalPaginas || 1}
        </span>

        <button
          disabled={pagina === totalPaginas}
          onClick={() => setPagina(pagina + 1)}
        >
          ▶
        </button>

      </div>

    </div>
  );
}

export default ListadoUsuarios;