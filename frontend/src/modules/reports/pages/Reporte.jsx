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
      icono: "",
      componente: <ConsultasDashboard />
    },
    usuarios: {
      nombre: "Usuarios",
      icono: "",
      componente: <ListadoUsuarios />
    },
    clientes: {
      nombre: "Clientes",
      icono: "",
      componente: <ListadoClientes />
    },
    ventas: {
      nombre: "Ventas por Cliente",
      icono: "",
      componente: <VentasPorCliente />
    }
  };

  const [vista, setVista] = useState("dashboard");

  return (

    <div className="reporte-container">

      {/* HEADER */}

      <div className="header-reporte">

        <h1>Consultas y Reportes</h1>

        <span className="contador-secciones">
          {Object.keys(vistas).length} módulos
        </span>

      </div>

      {/* MENU */}

      <div className="menu-reportes">

        {Object.entries(vistas).map(([key, value]) => (

          <button
            key={key}
            className={`btn-menu ${vista === key ? "activo" : ""}`}
            onClick={() => setVista(key)}
          >

            <span className="icono-menu">
              {value.icono}
            </span>

            {value.nombre}

          </button>

        ))}

      </div>

      {/* CONTENIDO */}

      <div className="contenido-reporte">

        <div className="panel-reporte">

          <div className="animacion-vista" key={vista}>
            {vistas[vista].componente}
          </div>

        </div>

      </div>

    </div>

  );

}

export default Reporte;