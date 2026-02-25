import { clientesMock, ventasMock } from "../../config/mockData";
import TablaGenerica from "../../components/TablaGenerica";

function VentasPorCliente() {

  const datosAgrupados = clientesMock.map(cliente => {
    const total = ventasMock
      .filter(v => v.cedulaCliente === cliente.cedula)
      .reduce((acc, v) => acc + v.total, 0);

    return {
      cedula: cliente.cedula,
      nombre: cliente.nombre,
      totalCompras: total
    };
  });

  const totalGeneral = datosAgrupados.reduce(
    (acc, cliente) => acc + cliente.totalCompras,
    0
  );

  const columnas = ["Cédula", "Nombre", "Total Compras"];

  // ===== EXPORTAR CSV =====
  const exportarCSV = () => {
    const encabezado = "Cedula,Nombre,TotalCompras\n";
    const filas = datosAgrupados
      .map(c => `${c.cedula},${c.nombre},${c.totalCompras}`)
      .join("\n");

    const blob = new Blob([encabezado + filas], { type: "text/csv" });
    const url = URL.createObjectURL(blob);

    const link = document.createElement("a");
    link.href = url;
    link.download = "ventas_por_cliente.csv";
    link.click();
  };

  // ===== EXPORTAR PDF (simple con print) =====
  const exportarPDF = () => {
    window.print();
  };

  return (
    <div>
      <h2>Total de Ventas por Cliente</h2>

      <div style={{ marginBottom: "15px" }}>
        <button className="btn btn-success" onClick={exportarCSV}>
          Exportar CSV
        </button>

        <button className="btn btn-secondary" onClick={exportarPDF}>
          Exportar PDF
        </button>
      </div>

      <TablaGenerica columnas={columnas} datos={
        datosAgrupados.map(c => ({
          ...c,
          totalCompras: `$ ${c.totalCompras.toLocaleString()}`
        }))
      } />

      <div className="total-card">
        Total General de Ventas: $ {totalGeneral.toLocaleString()}
      </div>
    </div>
  );
}

export default VentasPorCliente;