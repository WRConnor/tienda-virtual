import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";
import { clientesMock, ventasMock } from "../../../config/mockData";
import TablaGenerica from "../../../shared/components/TablaGenerica";

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

  const fecha = new Date().toISOString().split("T")[0];

  // Encabezado con separador ;
  const encabezado = "Cédula;Nombre;Total Compras\n";

  const filas = datosAgrupados
    .map(c =>
      `${c.cedula};${c.nombre};${c.totalCompras.toLocaleString()}`
    )
    .join("\n");

  // BOM para que Excel reconozca UTF-8 correctamente
  const BOM = "\uFEFF";

  const blob = new Blob(
    [BOM + encabezado + filas],
    { type: "text/csv;charset=utf-8;" }
  );

  const url = URL.createObjectURL(blob);

  const link = document.createElement("a");
  link.href = url;
  link.download = `reporte_ventas_por_cliente_${fecha}.csv`;
  link.click();

  URL.revokeObjectURL(url);
};

  // ===== EXPORTAR PDF =====
  const exportarPDF = () => {

  const doc = new jsPDF();

  doc.setFontSize(18);
  doc.text("Reporte de Ventas por Cliente", 14, 20);

  doc.setFontSize(12);
  doc.text(`Fecha: ${new Date().toLocaleDateString()}`, 14, 30);

  autoTable(doc, {
    startY: 40,
    head: [["Cédula", "Nombre", "Total Compras"]],
    body: datosAgrupados.map(cliente => [
      cliente.cedula,
      cliente.nombre,
      `$ ${cliente.totalCompras.toLocaleString()}`
    ]),
  });

  const finalY = doc.lastAutoTable.finalY + 10;

  doc.setFont(undefined, "bold");
  doc.text(
    `Total General: $ ${totalGeneral.toLocaleString()}`,
    14,
    finalY
  );

  const fecha = new Date();

  const fechaFormateada = fecha.toISOString().split("T")[0]; 
  // formato: 2026-02-26

  doc.save(`reporte_ventas_por_cliente_${fechaFormateada}.pdf`);
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