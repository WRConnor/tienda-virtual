import { useEffect, useState, useMemo } from "react";
import { api } from "../../../api/api";
import "../../../css/global.css";

import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";

import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  CartesianGrid,
  ResponsiveContainer
} from "recharts";

function VentasPorCliente() {

  const [ventas, setVentas] = useState([]);
  const [datosAgrupados, setDatosAgrupados] = useState([]);
  const [busqueda, setBusqueda] = useState("");

  // ===== CARGAR VENTAS =====
  const cargarVentas = async () => {

    try {

      const data = await api.obtenerVentas();
      const ventasArray = Array.isArray(data) ? data : [];

      setVentas(ventasArray);

      agruparVentasPorCliente(ventasArray);

    } catch (err) {
      console.error("Error cargando ventas:", err);
    }

  };

  useEffect(() => {
    cargarVentas();
  }, []);

  // ===== AGRUPAR =====
  const agruparVentasPorCliente = (ventas) => {

    const mapa = {};

    ventas.forEach(v => {

      const cliente = v.nombreCliente || "Sin nombre";
      const total = Number(v.totalVenta) || 0;

      if (!mapa[cliente]) {
        mapa[cliente] = 0;
      }

      mapa[cliente] += total;

    });

    const resultado = Object.keys(mapa)
      .map(cliente => ({
        cliente,
        total: mapa[cliente]
      }))
      .sort((a, b) => b.total - a.total);

    setDatosAgrupados(resultado);

  };

  // ===== TOTAL =====
  const totalGeneral = useMemo(() => {

    return datosAgrupados.reduce(
      (sum, c) => sum + c.total,
      0
    );

  }, [datosAgrupados]);

  // ===== AGREGAR PORCENTAJE =====
  const datosConPorcentaje = useMemo(() => {

    return datosAgrupados.map(c => ({
      ...c,
      porcentaje: totalGeneral
        ? ((c.total / totalGeneral) * 100).toFixed(2)
        : 0
    }));

  }, [datosAgrupados, totalGeneral]);

  // ===== FILTRO BUSQUEDA =====
  const datosFiltrados = useMemo(() => {

    return datosConPorcentaje.filter(c =>
      c.cliente.toLowerCase().includes(
        busqueda.toLowerCase()
      )
    );

  }, [datosConPorcentaje, busqueda]);

  // ===== TOP 5 =====
  const topClientes = datosConPorcentaje.slice(0, 5);

  // ===== EXPORTAR CSV =====
  const exportarCSV = () => {

    const fecha = new Date().toISOString().split("T")[0];

    const encabezado =
      "Cliente;Total Compras;Porcentaje\n";

    const filas = datosFiltrados
      .map(c =>
        `${c.cliente};${c.total};${c.porcentaje}%`
      )
      .join("\n");

    const blob = new Blob(
      ["\uFEFF" + encabezado + filas],
      { type: "text/csv;charset=utf-8;" }
    );

    const url = URL.createObjectURL(blob);

    const link = document.createElement("a");
    link.href = url;
    link.download = `ventas_clientes_${fecha}.csv`;
    link.click();

    URL.revokeObjectURL(url);

  };

  // ===== EXPORTAR PDF =====
  const exportarPDF = () => {

    const doc = new jsPDF();

    doc.setFontSize(18);
    doc.text("Reporte Ventas por Cliente", 14, 20);

    doc.setFontSize(11);
    doc.text(
      `Fecha: ${new Date().toLocaleDateString()}`,
      14,
      30
    );

    autoTable(doc, {

      startY: 40,

      head: [["Cliente", "Total", "% Ventas"]],

      body: datosFiltrados.map(c => [
        c.cliente,
        `$ ${c.total.toLocaleString()}`,
        `${c.porcentaje}%`
      ])

    });

    const finalY = doc.lastAutoTable.finalY + 10;

    doc.setFont(undefined, "bold");

    doc.text(
      `Total General: $ ${totalGeneral.toLocaleString()}`,
      14,
      finalY
    );

    const fecha = new Date().toISOString().split("T")[0];

    doc.save(`ventas_clientes_${fecha}.pdf`);

  };

  return (

    <div className="reporte-seccion">

      <h2 className="titulo-reporte">
        Ventas por Cliente
      </h2>

      {/* BUSCADOR */}

      <input
        type="text"
        placeholder="Buscar cliente..."
        value={busqueda}
        onChange={(e) =>
          setBusqueda(e.target.value)
        }
        style={{
          padding: "6px",
          marginBottom: "15px",
          width: "250px"
        }}
      />

      {/* BOTONES */}

      <div style={{ marginBottom: "15px" }}>

        <button onClick={exportarCSV}>
          Exportar CSV
        </button>

        <button
          onClick={exportarPDF}
          style={{ marginLeft: "10px" }}
        >
          Exportar PDF
        </button>

      </div>

      {/* TOP CLIENTES */}

      <div style={{ marginBottom: "20px" }}>

        <h3>Top 5 Clientes</h3>

        <ol>
          {topClientes.map((c, i) => (
            <li key={i}>
              {c.cliente} — $
              {c.total.toLocaleString()}
            </li>
          ))}
        </ol>

      </div>

      {/* GRAFICO */}

      <div
        style={{
          width: "100%",
          height: "400px",
          minWidth: 0
        }}
      >

        <ResponsiveContainer width="100%" height="100%">

          <BarChart data={datosFiltrados}>

            <CartesianGrid strokeDasharray="3 3" />

            <XAxis
              dataKey="cliente"
              interval={0}
              angle={-20}
              textAnchor="end"
              height={80}
            />

            <YAxis />

            <Tooltip
              formatter={(v) =>
                `$ ${v.toLocaleString()}`
              }
            />

            <Bar dataKey="total" />

          </BarChart>

        </ResponsiveContainer>

      </div>

      {/* TABLA */}

      <div className="tabla-container">

        <table className="tabla-reporte">

          <thead>
            <tr>
              <th>Cliente</th>
              <th>Total</th>
              <th>% Ventas</th>
            </tr>
          </thead>

          <tbody>

            {datosFiltrados.length === 0 ? (

              <tr>
                <td colSpan="3">
                  No hay datos
                </td>
              </tr>

            ) : (

              datosFiltrados.map((c, i) => (

                <tr key={i}>
                  <td>{c.cliente}</td>
                  <td>
                    $ {c.total.toLocaleString()}
                  </td>
                  <td>{c.porcentaje}%</td>
                </tr>

              ))

            )}

          </tbody>

        </table>

      </div>

      {/* TOTAL */}

      <div
        style={{
          marginTop: "15px",
          fontWeight: "bold"
        }}
      >

        Total General: $
        {totalGeneral.toLocaleString()}

      </div>

    </div>

  );

}

export default VentasPorCliente;