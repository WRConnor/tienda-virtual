import { useEffect, useState } from "react";
import { api } from "../../../api/api";
import "../../../css/global.css";

function ConsultasDashboard() {

  const [ventas, setVentas] = useState([]);
  const [clientes, setClientes] = useState([]);

  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {

    try {

      const ventasData = await api.obtenerVentas();
      const clientesData = await api.obtenerClientes();

      setVentas(Array.isArray(ventasData) ? ventasData : []);
      setClientes(Array.isArray(clientesData) ? clientesData : []);

    } catch (error) {

      console.error("Error cargando dashboard:", error);

    }

  };

  const totalVentas = ventas.reduce(
    (sum, v) => sum + (Number(v.totalVenta) || 0),
    0
  );

  const promedioVenta = ventas.length
    ? totalVentas / ventas.length
    : 0;

  return (

    <div className="dashboard-container">

      {/* TARJETAS KPI */}

      <div className="dashboard-cards">

        <div className="card-kpi">
          <div className="kpi-icon">💰</div>
          <div>
            <span className="kpi-title">Total Ventas</span>
            <h2>$ {totalVentas.toLocaleString()}</h2>
          </div>
        </div>

        <div className="card-kpi">
          <div className="kpi-icon">🧾</div>
          <div>
            <span className="kpi-title">Número Transacciones</span>
            <h2>{ventas.length}</h2>
          </div>
        </div>

        <div className="card-kpi">
          <div className="kpi-icon">👥</div>
          <div>
            <span className="kpi-title">Clientes</span>
            <h2>{clientes.length}</h2>
          </div>
        </div>

        <div className="card-kpi">
          <div className="kpi-icon">📊</div>
          <div>
            <span className="kpi-title">Promedio Venta</span>
            <h2>$ {promedioVenta.toLocaleString()}</h2>
          </div>
        </div>

      </div>

    </div>

  );

}

export default ConsultasDashboard;