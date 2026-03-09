import { useEffect, useState } from "react";
import { api } from "../../../api/api";
import "../../../css/global.css";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

function ListadoClientes() {

  const [clientes, setClientes] = useState([]);
  const [busqueda, setBusqueda] = useState("");
  const [pagina, setPagina] = useState(1);
  const [ordenCampo, setOrdenCampo] = useState("cedulaCliente");
  const [ordenAsc, setOrdenAsc] = useState(true);

  const filasPorPagina = 8;

  const cargarClientes = async () => {
    try {

      const data = await api.obtenerClientes();

      if (Array.isArray(data)) {
        setClientes(data);
      } else if (Array.isArray(data.data)) {
        setClientes(data.data);
      } else if (Array.isArray(data.clientes)) {
        setClientes(data.clientes);
      } else {
        setClientes([]);
      }

    } catch (err) {
      console.error(err);
      setClientes([]);
    }
  };

  useEffect(() => {
    cargarClientes();
  }, []);

  // FILTRAR
  const clientesFiltrados = clientes.filter((c) =>
    c.nombreCliente?.toLowerCase().includes(busqueda.toLowerCase()) ||
    c.emailCliente?.toLowerCase().includes(busqueda.toLowerCase()) ||
    String(c.cedulaCliente).includes(busqueda)
  );

  // ORDENAR
  const clientesOrdenados = [...clientesFiltrados].sort((a, b) => {

    const valorA = a[ordenCampo];
    const valorB = b[ordenCampo];

    if (valorA < valorB) return ordenAsc ? -1 : 1;
    if (valorA > valorB) return ordenAsc ? 1 : -1;
    return 0;

  });

  // PAGINACION
  const totalPaginas = Math.ceil(clientesOrdenados.length / filasPorPagina);

  const inicio = (pagina - 1) * filasPorPagina;
  const fin = inicio + filasPorPagina;

  const clientesPagina = clientesOrdenados.slice(inicio, fin);

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

    const encabezado = "Cédula;Nombre;Email;Teléfono;Dirección\n";

    const filas = clientesOrdenados
      .map(c =>
        `${c.cedulaCliente};${c.nombreCliente};${c.emailCliente};${c.telefonoCliente};${c.direccionCliente}`
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
    link.download = `reporte_clientes_${fecha}.csv`;
    link.click();

    URL.revokeObjectURL(url);
  };

  // ===== EXPORTAR PDF =====
  const exportarPDF = () => {

    const doc = new jsPDF();

    doc.setFontSize(18);
    doc.text("Listado de Clientes", 14, 20);

    doc.setFontSize(12);
    doc.text(`Fecha: ${new Date().toLocaleDateString()}`, 14, 30);

    autoTable(doc, {
      startY: 40,
      head: [["Cédula", "Nombre", "Email", "Teléfono", "Dirección"]],
      body: clientesOrdenados.map(c => [
        c.cedulaCliente,
        c.nombreCliente,
        c.emailCliente,
        c.telefonoCliente,
        c.direccionCliente
      ]),
    });

    const fecha = new Date().toISOString().split("T")[0];

    doc.save(`reporte_clientes_${fecha}.pdf`);
  };

  return (
    <div className="reporte-seccion">

      <h2 className="titulo-reporte">Listado de Clientes</h2>

      {/* BUSCADOR */}

      <input
        className="input-busqueda"
        type="text"
        placeholder="Buscar cliente..."
        value={busqueda}
        onChange={(e) => {
          setBusqueda(e.target.value);
          setPagina(1);
        }}
      />

      {/* EXPORTACION */}

      <div style={{ marginBottom: "10px" }}>

        <button onClick={exportarCSV}>
          Exportar CSV
        </button>

        <button onClick={exportarPDF} style={{ marginLeft: "10px" }}>
          Exportar PDF
        </button>

      </div>

      <div className="tabla-container">

        <table className="tabla-reporte">

          <thead>
            <tr>
              <th onClick={() => ordenarPor("cedulaCliente")}>Cédula</th>
              <th onClick={() => ordenarPor("nombreCliente")}>Nombre</th>
              <th onClick={() => ordenarPor("emailCliente")}>Email</th>
              <th onClick={() => ordenarPor("telefonoCliente")}>Teléfono</th>
              <th onClick={() => ordenarPor("direccionCliente")}>Dirección</th>
            </tr>
          </thead>

          <tbody>

            {clientesPagina.length === 0 ? (
              <tr>
                <td colSpan="5" className="sin-datos">
                  No se encontraron clientes
                </td>
              </tr>
            ) : (
              clientesPagina.map((c) => (
                <tr key={c.cedulaCliente}>
                  <td>{c.cedulaCliente}</td>
                  <td>{c.nombreCliente}</td>
                  <td>{c.emailCliente}</td>
                  <td>{c.telefonoCliente}</td>
                  <td>{c.direccionCliente}</td>
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

export default ListadoClientes;