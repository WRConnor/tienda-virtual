import { useState } from "react";
import ConsultasDashboard from "./queries/ConsultasDashboard";
import ListadoUsuarios from "./queries/ListadoUsuarios";
import ListadoClientes from "./queries/ListadoClientes";
import VentasPorCliente from "./queries/VentasPorCliente";

function Reporte() {

  const [vista, setVista] = useState("dashboard");

  const renderVista = () => {
    switch (vista) {
      case "usuarios":
        return <ListadoUsuarios />;
      case "clientes":
        return <ListadoClientes />;
      case "ventas":
        return <VentasPorCliente />;
      default:
        return <ConsultasDashboard />;
    }
  };

  return (
    <div>

      <h1>Módulo de Consultas y Reportes</h1>

      <div className="menu-reportes">
        <button
          className={`btn-menu ${vista === "dashboard" ? "activo" : ""}`}
          onClick={() => setVista("dashboard")}
        >
          Dashboard
        </button>

        <button
          className={`btn-menu ${vista === "usuarios" ? "activo" : ""}`}
          onClick={() => setVista("usuarios")}
        >
          Usuarios
        </button>

        <button
          className={`btn-menu ${vista === "clientes" ? "activo" : ""}`}
          onClick={() => setVista("clientes")}
        >
          Clientes
        </button>

        <button
          className={`btn-menu ${vista === "ventas" ? "activo" : ""}`}
          onClick={() => setVista("ventas")}
        >
          Ventas por Cliente
        </button>
      </div>

      <div className="contenido-reporte">
        {renderVista()}
      </div>

    </div>
  );
}

export default Reporte;