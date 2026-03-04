import { useState } from "react";
import ConsultasDashboard from "../../dashboard/pages/ConsultasDashboard";
import ListadoUsuarios from "../../users/pages/ListadoUsuarios";
import ListadoClientes from "../../clients/pages/ListadoClientes";
import VentasPorCliente from "../../sales/pages/VentasPorCliente";
import "../../../css/global.css";

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

      <h1>Consultas y reportes</h1>
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