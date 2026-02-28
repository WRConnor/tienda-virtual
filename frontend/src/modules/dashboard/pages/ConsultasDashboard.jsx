import { ventasMock } from "../../../config/mockData";
import CardMetrica from "../../../shared/components/CardMetrica";

function ConsultasDashboard() {

  const totalVentas = ventasMock.reduce((acc, v) => acc + v.total, 0);

  return (
    <div>
      <h2>Dashboard Comercial</h2>

      <div className="dashboard-grid">
        <CardMetrica titulo="Total Ventas" valor={`$ ${totalVentas}`} />
        <CardMetrica titulo="Número Transacciones" valor={ventasMock.length} />
      </div>
    </div>
  );
}

export default ConsultasDashboard;