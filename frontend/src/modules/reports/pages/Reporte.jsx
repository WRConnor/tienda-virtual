import { useState } from "react";
import ConsultasDashboard from "../../dashboard/pages/ConsultasDashboard";
import ListadoUsuarios from "../../users/pages/ListadoUsuarios";
import ListadoClientes from "../../clients/pages/ListadoClientes";
import VentasPorCliente from "../../sales/pages/VentasPorCliente";
import "../../../css/global.css";

function Reporte() {

  const vistas = {
    dashboard: {
      nombre: "Dashboard",
      componente: <ConsultasDashboard />
    },
    usuarios: {
      nombre: "Usuarios",
      componente: <ListadoUsuarios />
    },
    clientes: {
      nombre: "Clientes",
      componente: <ListadoClientes />
    },
    ventas: {
      nombre: "Ventas por Cliente",
      componente: <VentasPorCliente />
    }
  };

  const [vista, setVista] = useState("dashboard");

  return (
    <div className="reporte-container">

      <h1>Consultas y Reportes</h1>

      {/* MENU */}
      <div className="menu-reportes">
        {Object.entries(vistas).map(([key, value]) => (
          <button
            key={key}
            className={`btn-menu ${vista === key ? "activo" : ""}`}
            onClick={() => setVista(key)}
          >
            {value.nombre}
          </button>
        ))}
      </div>

      {/* CONTENIDO */}
      <div className="contenido-reporte">
        {vistas[vista].componente}
      </div>

    </div>
  );
}

export default Reporte;