import { useState } from "react";
import "../css/Producto.css";

function Producto() {
  const [productos, setProductos] = useState([]);
  const [error, setError] = useState("");
  const [archivo, setArchivo] = useState(null);

  const proveedoresExistentes = [1, 2, 3, 4, 5];

  const handleFile = (e) => {
    const file = e.target.files[0];

    if (!file) return;

    setArchivo(file);
  };

  const cargarArchivo = () => {
    if (!archivo) {
      setError("Debe seleccionar un archivo");
      return;
    }

    // Validar extensión CSV
    if (!archivo.name.endsWith(".csv")) {
      setError("El archivo debe ser formato CSV");
      return;
    }

    const reader = new FileReader();

    reader.onload = (event) => {
      const contenido = event.target.result;
      procesarCSV(contenido);
    };

    reader.readAsText(archivo);
  };

  const procesarCSV = (contenido) => {
    const lineas = contenido.split("\n");
    const nuevosProductos = [];

    for (let linea of lineas) {
      if (!linea.trim()) continue;

      const datos = linea.split(",");

      if (datos.length !== 6) {
        setError("Formato incorrecto en el archivo");
        return;
      }

      const [
        codigo_producto,
        nombre_producto,
        nitproveedor,
        precio_compra,
        ivacompra,
        precio_venta
      ] = datos;

      if (!proveedoresExistentes.includes(Number(nitproveedor))) {
        setError(`Proveedor con NIT ${nitproveedor} no existe`);
        return;
      }

      nuevosProductos.push({
        codigo_producto: Number(codigo_producto),
        nombre_producto,
        nitproveedor: Number(nitproveedor),
        precio_compra: Number(precio_compra),
        ivacompra: Number(ivacompra),
        precio_venta: Number(precio_venta)
      });
    }

    setProductos(nuevosProductos);
    setError("");
  };

  return (
    <div className="producto-wrapper">

      <div className="producto-card">
        <h2>Productos</h2>

        {/* ESTRUCTURA COMO LA MAQUETA */}
        <div style={{ marginTop: "60px", textAlign: "center" }}>

          <div style={{ marginBottom: "25px" }}>
            <label style={{ marginRight: "15px" }}>
              Nombre del Archivo
            </label>

            <input
              type="file"
              accept=".csv"
              onChange={handleFile}
            />
          </div>

          <button
            className="btn btn-secondary"
            onClick={cargarArchivo}
          >
            Cargar
          </button>

          {error && (
            <p className="error" style={{ marginTop: "15px" }}>
              {error}
            </p>
          )}

        </div>
      </div>

      {/* Tabla solo si hay productos */}
      {productos.length > 0 && (
        <div className="producto-table-container">
          <table className="producto-table">
            <thead>
              <tr>
                <th>Código</th>
                <th>Nombre</th>
                <th>NIT Proveedor</th>
                <th>Precio Compra</th>
                <th>IVA Compra</th>
                <th>Precio Venta</th>
              </tr>
            </thead>
            <tbody>
              {productos.map((p, index) => (
                <tr key={index}>
                  <td>{p.codigo_producto}</td>
                  <td>{p.nombre_producto}</td>
                  <td>{p.nitproveedor}</td>
                  <td>{p.precio_compra}</td>
                  <td>{p.ivacompra}</td>
                  <td>{p.precio_venta}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

    </div>
  );
}

export default Producto;