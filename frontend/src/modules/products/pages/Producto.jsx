import { useState, useEffect } from "react";
import { api } from "../../../api/api";
import "../styles/Producto.css";

function Producto() {

  const [productos, setProductos] = useState([]);
  const [proveedores, setProveedores] = useState([]);

  const [busqueda, setBusqueda] = useState("");

  const [paginaActual, setPaginaActual] = useState(1);
  const productosPorPagina = 5;

  const [orden, setOrden] = useState({
    campo: "",
    direccion: "asc"
  });

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
  const [mostrarPreview, setMostrarPreview] = useState(false);

  const safeValue = (v) => (v === undefined || v === null ? "" : v);

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

  useEffect(() => {
    cargarProductos();
    cargarProveedores();
  }, []);

  const cargarProductos = async () => {
    try {
      const data = await api.getProductos();
      setProductos(data || []);
    } catch (err) {
      setError("Error cargando productos: " + err.message);
    }
  };

  const cargarProveedores = async () => {
    try {
      const data = await api.getProveedores();
      setProveedores(data || []);
    } catch (err) {
      console.error(err);
    }
  };

  const obtenerNombreProveedor = (nit) => {
    const proveedor = proveedores.find(
      (p) => Number(p.nitProveedor) === Number(nit)
    );
    return proveedor ? proveedor.nombreProveedor : "Desconocido";
  };

  const validarProveedor = () => {
    return proveedores.some(
      (p) => Number(p.nitProveedor) === Number(form.nitproveedor)
    );
  };

  const crearProducto = async () => {

    if (!validarProveedor()) {
      setError("El proveedor no existe");
      return;
    }

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
      alert("Producto creado");

    } catch (err) {
      setError("Error al crear producto: " + err.message);
    }
  };

  const actualizarProducto = async () => {

    const producto = productos.find(
      (p) => p.codigoProducto === Number(form.codigo_producto)
    );

    if (!producto) {
      setError("Producto no encontrado");
      return;
    }

    try {

      await api.actualizarProducto(producto.idProducto, {
        codigoProducto: Number(form.codigo_producto),
        nombreProducto: form.nombre_producto,
        nitProveedor: Number(form.nitproveedor),
        precioCompra: Number(form.precio_compra),
        ivaCompra: Number(form.ivacompra),
        precioVenta: Number(form.precio_venta)
      });

      await cargarProductos();
      limpiarForm();

    } catch (err) {
      setError("Error actualizando: " + err.message);
    }
  };

  const borrarProducto = async () => {

    const producto = productos.find(
      (p) => p.codigoProducto === Number(form.codigo_producto)
    );

    if (!producto) {
      setError("Producto no encontrado");
      return;
    }

    try {

      await api.borrarProducto(producto.idProducto);
      await cargarProductos();
      limpiarForm();

    } catch (err) {
      setError("Error borrando: " + err.message);
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

  };

  const ordenar = (campo) => {

    const direccion =
      orden.campo === campo && orden.direccion === "asc"
        ? "desc"
        : "asc";

    const ordenados = [...productos].sort((a, b) => {

      if (a[campo] < b[campo]) return direccion === "asc" ? -1 : 1;
      if (a[campo] > b[campo]) return direccion === "asc" ? 1 : -1;
      return 0;

    });

    setProductos(ordenados);
    setOrden({ campo, direccion });
  };

  const productosFiltrados = productos.filter((p) =>
    p.nombreProducto?.toLowerCase().includes(busqueda.toLowerCase()) ||
    p.codigoProducto?.toString().includes(busqueda)
  );

  const indiceUltimo = paginaActual * productosPorPagina;
  const indicePrimero = indiceUltimo - productosPorPagina;

  const productosPagina = productosFiltrados.slice(
    indicePrimero,
    indiceUltimo
  );

  const exportarCSV = () => {

    const header =
      "codigo_producto,nombre_producto,nitproveedor,precio_compra,ivacompra,precio_venta";

    const filas = productos.map((p) =>
      [
        p.codigoProducto,
        p.nombreProducto,
        p.nitProveedor,
        p.precioCompra,
        p.ivaCompra,
        p.precioVenta
      ].join(",")
    );

    const csv = [header, ...filas].join("\n");

    const blob = new Blob([csv], { type: "text/csv" });

    const url = window.URL.createObjectURL(blob);

    const link = document.createElement("a");
    link.href = url;
    link.download = "productos.csv";
    link.click();
  };

  const handleFile = (e) => {
    const file = e.target.files[0];
    if (!file) return;
    setArchivo(file);
    setCsvPreview([]);
  };

  const procesarCSV = (contenido) => {

    const lineas = contenido
      .split("\n")
      .slice(1)
      .filter((l) => l.trim());

    const nuevos = [];

    for (let linea of lineas) {

      const datos = linea.split(",");

      if (datos.length !== 6) continue;

      const [
        codigo,
        nombre,
        nitproveedor,
        precioCompra,
        iva,
        precioVenta
      ] = datos;

      const nit = Number(nitproveedor);

      const proveedorExiste = proveedores.some(
        (p) => Number(p.nitProveedor) === nit
      );

      nuevos.push({
        codigoProducto: Number(codigo),
        nombreProducto: nombre,
        nitProveedor: nit,
        precioCompra: Number(precioCompra),
        ivaCompra: Number(iva),
        precioVenta: Number(precioVenta),
        valido: proveedorExiste
      });

    }

    setCsvPreview(nuevos);
    setMostrarPreview(true);
  };

  const cargarArchivo = () => {

    if (!archivo) {
      setError("Seleccione un CSV");
      return;
    }

    const reader = new FileReader();

    reader.onload = (e) => procesarCSV(e.target.result);

    reader.readAsText(archivo);
  };

  const enviarCSV = async () => {

  const validos = csvPreview.filter(p => p.valido);

  if (!validos.length) {
    setError("No hay productos válidos en el CSV");
    return;
  }

  try {

    for (const p of validos) {

      const producto = {
        codigoProducto: Number(p.codigoProducto),
        nombreProducto: p.nombreProducto,
        nitProveedor: Number(p.nitProveedor),
        precioCompra: Number(p.precioCompra),
        ivaCompra: Number(p.ivaCompra),
        precioVenta: Number(p.precioVenta)
      };

      console.log("Enviando producto:", producto);

      await api.crearProducto(producto);

    }

    await cargarProductos();

    setCsvPreview([]);
    setArchivo(null);
    setMostrarPreview(false);

    alert("CSV importado correctamente");

  } catch (err) {

    console.error(err);
    setError("Error CSV: " + err.message);

  }

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

        <div style={{marginTop:20}}>

          <input
            placeholder="Buscar producto..."
            value={busqueda}
            onChange={(e)=>setBusqueda(e.target.value)}
          />

          <button onClick={exportarCSV}>
            Exportar CSV
          </button>

        </div>

        <div style={{ marginTop: "30px", textAlign: "center" }}>

          <input type="file" accept=".csv" onChange={handleFile} />
          <button onClick={cargarArchivo}>Previsualizar CSV</button>

        </div>

        {error && <p className="error">{error}</p>}

      </div>

      <div className="producto-table-container">

        <h3>Productos</h3>

        <table className="producto-table">

          <thead>
            <tr>
              <th onClick={()=>ordenar("codigoProducto")}>Código</th>
              <th onClick={()=>ordenar("nombreProducto")}>Nombre</th>
              <th>NIT</th>
              <th>Proveedor</th>
              <th onClick={()=>ordenar("precioCompra")}>Precio Compra</th>
              <th>IVA</th>
              <th onClick={()=>ordenar("precioVenta")}>Precio Venta</th>
            </tr>
          </thead>

          <tbody>

            {productosPagina.map((p)=>(
              <tr key={p.idProducto} onClick={()=>seleccionarProducto(p)}>

                <td>{p.codigoProducto}</td>
                <td>{p.nombreProducto}</td>
                <td>{p.nitProveedor}</td>
                <td>{obtenerNombreProveedor(p.nitProveedor)}</td>
                <td>{p.precioCompra}</td>
                <td>{p.ivaCompra}</td>
                <td>{p.precioVenta}</td>

              </tr>
            ))}

          </tbody>

        </table>

        <div style={{marginTop:20}}>

          <button
            disabled={paginaActual===1}
            onClick={()=>setPaginaActual(paginaActual-1)}
          >
            Previous
          </button>

          <span style={{margin:"0 10px"}}>
            Page {paginaActual}
          </span>

          <button
            disabled={indiceUltimo >= productosFiltrados.length}
            onClick={()=>setPaginaActual(paginaActual+1)}
          >
            Next
          </button>

        </div>

      </div>

      {mostrarPreview && (

        <div className="csv-modal-overlay">

          <div className="csv-modal">

            <h3>Previsualización CSV</h3>

            <p>
              Mostrando {csvPreview.length} registros
            </p>

            <table className="producto-table">

              <thead>
                <tr>
                  <th>Código</th>
                  <th>Nombre</th>
                  <th>NIT</th>
                  <th>Proveedor</th>
                  <th>Precio Compra</th>
                  <th>IVA</th>
                  <th>Precio Venta</th>
                </tr>
              </thead>

              <tbody>

                {csvPreview.map((p,i)=>(

                  <tr key={i} style={{
                    backgroundColor: p.valido ? "white" : "#ffdddd"
                  }}>

                    <td>{p.codigoProducto}</td>
                    <td>{p.nombreProducto}</td>
                    <td>{p.nitProveedor}</td>
                    <td>{obtenerNombreProveedor(p.nitProveedor)}</td>
                    <td>{p.precioCompra}</td>
                    <td>{p.ivaCompra}</td>
                    <td>{p.precioVenta}</td>

                  </tr>

                ))}

              </tbody>

            </table>

            <div style={{marginTop:20}}>

              <button onClick={enviarCSV}>
                Confirmar Importación
              </button>

              <button
                style={{marginLeft:10}}
                onClick={()=>{
                  setMostrarPreview(false);
                  setCsvPreview([]);
                  setArchivo(null);
                }}
              >
                Cancelar
              </button>

            </div>

          </div>

        </div>

      )}

    </div>
  );
}

export default Producto;