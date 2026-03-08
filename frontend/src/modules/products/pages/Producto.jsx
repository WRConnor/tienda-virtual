import { useState, useEffect } from "react";
import { api } from "../../../api/api";
import "../styles/Producto.css";

function Producto() {
  const [productos, setProductos] = useState([]);
  const [form, setForm] = useState({
    codigo_producto: "",
    nombre_producto: "",
    nitproveedor: "",
    precio_compra: "",
    ivacompra: "",
    precio_venta: ""
  });
  const [error, setError] = useState("");
  const [archivo, setArchivo] = useState(null);
  const [csvPreview, setCsvPreview] = useState([]);

  const safeValue = (val) => (val === undefined || val === null ? "" : val);

  const handleChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const limpiarForm = () =>
    setForm({
      codigo_producto: "",
      nombre_producto: "",
      nitproveedor: "",
      precio_compra: "",
      ivacompra: "",
      precio_venta: ""
    });

  // 🔹 Cargar productos desde API
  useEffect(() => {
    cargarProductos();
  }, []);

  const cargarProductos = async () => {
    try {
      const data = await api.getProductos();
      setProductos(data && data.length ? data : []);
    } catch (err) {
      console.error(err);
      setError("Error al cargar productos: " + err.message);
    }
  };

  // 🔹 Crear producto individual
  const crearProducto = async () => {
    try {
      await api.crearProducto({
        codigoProducto: Number(form.codigo_producto),
        nombreProducto: form.nombre_producto,
        nitProveedor: Number(form.nitproveedor),
        precioCompra: Number(form.precio_compra),
        ivaCompra: Number(form.ivacompra),
        precioVenta: Number(form.precio_venta)
      });
      await cargarProductos();
      limpiarForm();
      setError("");
      alert("Producto creado con éxito");
    } catch (err) {
      console.error(err);
      setError("Error al crear producto: " + err.message);
    }
  };

  const actualizarProducto = async () => {
    const productoEncontrado = productos.find(
      (p) => p.codigoProducto === Number(form.codigo_producto)
    );

    if (!productoEncontrado || !productoEncontrado.idProducto) {
      setError("Producto no encontrado en la base de datos");
      return;
    }

    try {
      await api.actualizarProducto(productoEncontrado.idProducto, {
        codigoProducto: Number(form.codigo_producto),
        nombreProducto: form.nombre_producto,
        nitProveedor: Number(form.nitproveedor),
        precioCompra: Number(form.precio_compra),
        ivaCompra: Number(form.ivacompra),
        precioVenta: Number(form.precio_venta)
      });
      await cargarProductos();
      limpiarForm();
      setError("");
      alert("Producto actualizado con éxito");
    } catch (err) {
      console.error(err);
      setError("Error al actualizar producto: " + err.message);
    }
  };

  const borrarProducto = async () => {
    const productoEncontrado = productos.find(
      (p) => p.codigoProducto === Number(form.codigo_producto)
    );
    if (!productoEncontrado) {
      setError("Producto no encontrado");
      return;
    }

    try {
      await api.borrarProducto(productoEncontrado.idProducto);
      await cargarProductos();
      limpiarForm();
      setError("");
      alert("Producto eliminado con éxito");
    } catch (err) {
      console.error(err);
      setError("Error al borrar producto: " + err.message);
    }
  };

  // 🔹 Manejo de CSV
  const handleFile = (e) => {
    const file = e.target.files[0];
    if (!file) return;
    setArchivo(file);
    setCsvPreview([]);
  };

  // 🔹 Ajustamos la función para que coincida con tu tabla
  const procesarCSV = (contenido) => {
    const lineas = contenido.split("\n").filter((l) => l.trim());
    const nuevosProductos = [];

    for (let linea of lineas) {
      const datos = linea.split(",");
      if (datos.length !== 6) {
        setError("Formato incorrecto en el archivo");
        return;
      }

      // CSV: codigo_producto,nombre_producto,N°?,nitproveedor,ivaCompra,precioVenta
      const [codigo_producto, nombre_producto, nro, nitproveedor, ivacompra, precio_venta] = datos;

      // Ajustamos para que coincida con la tabla
      const precio_compra = Number(nitproveedor); // columna 4 como precio_compra
      const nit = Number(nro); // columna 3 como nitProveedor

      nuevosProductos.push({
        codigoProducto: Number(codigo_producto),
        nombreProducto: nombre_producto,
        nitProveedor: nit,
        precioCompra: precio_compra,
        ivaCompra: Number(ivacompra),
        precioVenta: Number(precio_venta)
      });
    }

    setCsvPreview(nuevosProductos);
    setError("");
  };

  const cargarArchivo = () => {
    if (!archivo) {
      setError("Debe seleccionar un archivo");
      return;
    }
    if (!archivo.name.endsWith(".csv")) {
      setError("El archivo debe ser formato CSV");
      return;
    }

    const reader = new FileReader();
    reader.onload = (event) => procesarCSV(event.target.result);
    reader.readAsText(archivo);
  };

  // 🔹 Enviar CSV al backend
  const enviarCSV = async () => {
    if (!csvPreview.length) {
      setError("No hay productos para enviar");
      return;
    }
    try {
      for (let prod of csvPreview) {
        await api.crearProducto(prod);
      }
      await cargarProductos();
      setCsvPreview([]);
      setArchivo(null);
      setError("");
      alert("Productos del CSV enviados con éxito");
    } catch (err) {
      console.error(err);
      setError("Error al enviar CSV: " + err.message);
    }
  };

  const seleccionarProducto = (p) => {
    setForm({
      codigo_producto: p.codigoProducto,
      nombre_producto: p.nombreProducto,
      nitproveedor: p.nitProveedor,
      precio_compra: p.precioCompra,
      ivacompra: p.ivaCompra,
      precio_venta: p.precioVenta
    });
    setError("");
  };

  return (
    <div className="producto-wrapper">
      <div className="producto-card">
        <h2>Gestión de Productos</h2>

        <div className="producto-form">
          <div className="form-column">
            <label>Código</label>
            <input name="codigo_producto" value={safeValue(form.codigo_producto)} onChange={handleChange} />
            <label>Nombre</label>
            <input name="nombre_producto" value={safeValue(form.nombre_producto)} onChange={handleChange} />
            <label>NIT Proveedor</label>
            <input name="nitproveedor" value={safeValue(form.nitproveedor)} onChange={handleChange} />
          </div>

          <div className="form-column">
            <label>Precio Compra</label>
            <input name="precio_compra" value={safeValue(form.precio_compra)} onChange={handleChange} />
            <label>IVA Compra</label>
            <input name="ivacompra" value={safeValue(form.ivacompra)} onChange={handleChange} />
            <label>Precio Venta</label>
            <input name="precio_venta" value={safeValue(form.precio_venta)} onChange={handleChange} />
          </div>
        </div>

        <div className="producto-buttons">
          <button onClick={crearProducto}>Crear</button>
          <button onClick={actualizarProducto}>Actualizar</button>
          <button onClick={borrarProducto}>Borrar</button>
          <button onClick={limpiarForm}>Limpiar</button>
        </div>

        <div style={{ marginTop: "40px", textAlign: "center" }}>
          <input type="file" accept=".csv" onChange={handleFile} />
          <button onClick={cargarArchivo}>Previsualizar CSV</button>
          {csvPreview.length > 0 && <button onClick={enviarCSV}>Enviar CSV al Backend</button>}
          {error && <p className="error">{error}</p>}
        </div>

        {csvPreview.length > 0 && (
          <div style={{ marginTop: "20px" }}>
            <h4>Previsualización CSV</h4>
            <table>
              <thead>
                <tr>
                  <th>Código</th>
                  <th>Nombre</th>
                  <th>NIT</th>
                  <th>Precio Compra</th>
                  <th>IVA</th>
                  <th>Precio Venta</th>
                </tr>
              </thead>
              <tbody>
                {csvPreview.map((p, idx) => (
                  <tr key={`csv-${p.codigoProducto}-${idx}`}>
                    <td>{p.codigoProducto}</td>
                    <td>{p.nombreProducto}</td>
                    <td>{p.nitProveedor}</td>
                    <td>{p.precioCompra}</td>
                    <td>{p.ivaCompra}</td>
                    <td>{p.precioVenta}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {productos.length > 0 && (
        <div className="producto-table-container">
          <h3>Productos en DB</h3>
          <table className="producto-table">
            <thead>
              <tr>
                <th>Código</th>
                <th>Nombre</th>
                <th>NIT</th>
                <th>Precio Compra</th>
                <th>IVA</th>
                <th>Precio Venta</th>
              </tr>
            </thead>
            <tbody>
              {productos.map((p) => (
                <tr key={p.idProducto} onClick={() => seleccionarProducto(p)}>
                  <td>{p.codigoProducto}</td>
                  <td>{p.nombreProducto}</td>
                  <td>{p.nitProveedor}</td>
                  <td>{p.precioCompra}</td>
                  <td>{p.ivaCompra}</td>
                  <td>{p.precioVenta}</td>
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