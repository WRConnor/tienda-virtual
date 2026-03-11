import { useState, useRef } from "react";
import { useAuth } from "../../auth/context/authContext"; // 🔹 importamos el contexto
import { apiVentas } from "../../../api/api";
import jsPDF from "jspdf";
import "../styles/Venta.css";

function Venta() {
  const inputProductoRef = useRef(null);
  const inputCantidadRef = useRef(null);

  const [cedulaCliente, setCedulaCliente] = useState("");
  const [clienteEncontrado, setClienteEncontrado] = useState(null);
  const [codigoProducto, setCodigoProducto] = useState("");
  const [productoActual, setProductoActual] = useState(null);
  const [cantidad, setCantidad] = useState("");
  const [detalle, setDetalle] = useState([]);
  const [ventaConfirmada, setVentaConfirmada] = useState(null);
  const [mostrarFactura, setMostrarFactura] = useState(false);

  // 🔹 obtenemos el usuario logueado del contexto
  const { user } = useAuth();

  const cedulaUsuario = user?.cedula;
  const usuarioLogueado = user?.usuario;
  console.log(user);

  const formatoCOP = (valor) => {
    return new Intl.NumberFormat("es-CO", {
      style: "currency",
      currency: "COP",
      minimumFractionDigits: 0,
    }).format(valor || 0);
  };

  const vaciarVenta = () => {
    setDetalle([]);
  };

  const nuevaVenta = () => {
    setDetalle([]);
    setCedulaCliente("");
    setClienteEncontrado(null);
    setCodigoProducto("");
    setProductoActual(null);
    setCantidad("");
  };

  const eliminarProducto = (index) => {
    const nuevoDetalle = [...detalle];
    nuevoDetalle.splice(index, 1);
    setDetalle(nuevoDetalle);
  };

  const editarCantidad = (index, nuevaCantidad) => {
    const nuevoDetalle = [...detalle];
    const producto = nuevoDetalle[index];

    producto.cantidad = Number(nuevaCantidad);
    producto.total = producto.cantidad * producto.valorUnitario;

    setDetalle(nuevoDetalle);
  };

  // =========================
  // FUNCIONES DE VENTA
  // =========================
  const buscarCliente = async () => {
    try {
      const cliente = await apiVentas.obtenerCliente(cedulaCliente);
      setClienteEncontrado(cliente);
      inputProductoRef.current.focus();
    } catch (error) {
      alert("Cliente no encontrado");
    }
  };

  const buscarProducto = async () => {
    try {
      const producto = await apiVentas.obtenerProducto(codigoProducto);
      setProductoActual(producto);
      inputCantidadRef.current.focus();
    } catch (error) {
      alert("Producto no encontrado");
    }
  };

  const agregarProducto = () => {
    if (!productoActual || !cantidad) return alert("Seleccione producto y cantidad");

    const total = Number(productoActual.precioVenta) * Number(cantidad);
    setDetalle([
      ...detalle,
      {
        codigo: productoActual.codigoProducto,
        nombre: productoActual.nombreProducto,
        cantidad: Number(cantidad),
        valorUnitario: Number(productoActual.precioVenta),
        iva: productoActual.ivaCompra,
        total,
      },
    ]);
    setCodigoProducto("");
    setCantidad("");
    setProductoActual(null);
    inputProductoRef.current.focus();
  };
  

  const totalizar = async () => {
    // 🔹 usamos el contexto en lugar de localStorage
    if (!cedulaUsuario || !usuarioLogueado)
      return alert("No se pudo obtener la cédula del usuario logueado");

    if (!clienteEncontrado) return alert("Debe seleccionar un cliente");
    if (detalle.length === 0) return alert("Debe agregar productos");

    const subtotal = detalle.reduce((acc, item) => acc + item.total, 0);
    const totalIVA = detalle.reduce((acc, item) => acc + item.total * item.iva / 100, 0);
    const totalVenta = subtotal + totalIVA;

    const venta = {
      cedulaCliente: Number(cedulaCliente),
      cedulaUsuario: Number(cedulaUsuario),
      valorVenta: subtotal,
      ivaVenta: totalIVA,
      totalVenta,
      detalles: detalle.map((d) => ({
        codigoProducto: d.codigo,
        cantidadProducto: d.cantidad,
      })),
    };

    console.log("VENTA ENVIADA:", venta);

    try {
      const ventaGuardada = await apiVentas.crearVenta(venta);
      setVentaConfirmada({
        ...ventaGuardada,   // 🔹 usa directamente la venta devuelta
        fecha: new Date().toLocaleString(),
        cliente: clienteEncontrado,
        usuario: usuarioLogueado,
        productos: detalle,
        subtotal,
        iva: totalIVA,
        total: totalVenta
      });
      setMostrarFactura(true);
      setDetalle([]);
      setCedulaCliente("");
    } catch (error) {
      alert("Error registrando venta");
      console.error(error);
    }
  };

  const generarFactura = () => {
    const doc = new jsPDF();
    doc.setFontSize(22);
    doc.text("TIENDA GENERICA", 20, 20);
    doc.setFontSize(11);
    doc.text("Sistema de Facturación", 20, 27);
    doc.text("Bogotá - Colombia", 20, 33);
    doc.text("FACTURA N°: " + ventaConfirmada.codigoVenta, 140, 20);
    doc.text("Fecha: " + ventaConfirmada.fecha, 140, 28);
    doc.text("Cliente: " + ventaConfirmada.cliente.nombreCliente, 20, 50);
    doc.text("Cédula: " + ventaConfirmada.cliente?.cedulaCliente, 20, 58);
    doc.text("Usuario que registró la venta: " + ventaConfirmada.usuario, 20, 65);

    let y = 80;
    doc.text("Producto", 20, y);
    doc.text("Cant", 110, y);
    doc.text("Precio", 140, y);
    doc.text("Total", 170, y);

    y += 10;
    ventaConfirmada.productos.forEach((p) => {
      doc.text(p.nombre, 20, y);
      doc.text(String(p.cantidad), 110, y);
      doc.text(formatoCOP(p.valorUnitario), 140, y);
      doc.text(formatoCOP(p.total), 170, y);
      y += 8;
    });

    y += 10;
    doc.text("Subtotal: " + formatoCOP(ventaConfirmada.subtotal), 140, y);
    y += 8;
    doc.text("IVA: " + formatoCOP(ventaConfirmada.iva), 140, y);
    y += 8;
    doc.text("TOTAL: " + formatoCOP(ventaConfirmada.total), 140, y);

    doc.save("factura_" + ventaConfirmada.codigoVenta + ".pdf");
  };

  const subtotal = detalle.reduce((a, b) => a + b.total, 0);
  const totalIVA = detalle.reduce((a, b) => a + b.total * b.iva / 100, 0);
  const totalVenta = subtotal + totalIVA;

  return (
    <div className="venta-wrapper">
      <h2>Ventas</h2>
      <div className="venta-card">
        {/* CLIENTE */}
        <div className="fila-superior">
          <input
            placeholder="Cédula"
            value={cedulaCliente}
            onChange={(e) => setCedulaCliente(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && buscarCliente()}
          />
          <button onClick={buscarCliente}>Consultar</button>
          <input placeholder="Cliente" value={clienteEncontrado?.nombreCliente || ""} readOnly />
        </div>

        {/* PRODUCTO */}
        <div className="fila-producto">
          <input
            ref={inputProductoRef}
            placeholder="Código Producto"
            value={codigoProducto}
            onChange={(e) => setCodigoProducto(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && buscarProducto()}
          />
          <button onClick={buscarProducto}>Consultar</button>
          <input placeholder="Producto" value={productoActual?.nombreProducto || ""} readOnly />
          <input
            ref={inputCantidadRef}
            type="number"
            placeholder="Cantidad"
            value={cantidad}
            onChange={(e) => setCantidad(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && agregarProducto()}
          />
          <input
            placeholder="Total"
            value={productoActual && cantidad ? formatoCOP(Number(productoActual.precioVenta) * Number(cantidad)) : ""}
            readOnly
          />
          <button onClick={agregarProducto}>Agregar</button>
        </div>

        <button className="btn-vaciar" onClick={vaciarVenta}>
          Vaciar venta
        </button>
        <button className="btn-nueva" onClick={nuevaVenta}>
          Nueva venta
        </button>

        {/* DETALLE DE VENTA */}
        <table className="tabla-venta">
          <thead>
            <tr>
              <th>Cod</th>
              <th>Producto</th>
              <th>Cant</th>
              <th>Precio</th>
              <th>Total</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {detalle.map((d, i) => (
              <tr key={i}>
                <td>{d.codigo}</td>
                <td>{d.nombre}</td>
                <td>
                  <input
                    type="number"
                    value={d.cantidad}
                    onChange={(e) => editarCantidad(i, e.target.value)}
                    style={{ width: "60px" }}
                  />
                </td>
                <td>{formatoCOP(d.valorUnitario)}</td>
                <td>{formatoCOP(d.total)}</td>
                <td>
                  <button onClick={() => eliminarProducto(i)}>Eliminar</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* RESUMEN */}
        <div className="resumen-precios">
          <div className="precio-box">
            <span>Subtotal</span>
            <strong>{formatoCOP(subtotal)}</strong>
          </div>
          <div className="precio-box">
            <span>IVA</span>
            <strong>{formatoCOP(totalIVA)}</strong>
          </div>
          <div className="precio-box total">
            <span>Total</span>
            <strong>{formatoCOP(totalVenta)}</strong>
          </div>
        </div>

        <button className="btn-confirmar" onClick={totalizar}>
          Confirmar Venta
        </button>
      </div>

      {/* MODAL FACTURA */}
      {mostrarFactura && ventaConfirmada && (
        <div className="modal-overlay">
          <div className="modal-factura">
            <div className="factura-header">
              <div>
                <h2>Factura</h2>
                <p>No. {ventaConfirmada.codigoVenta}</p>
              </div>
              <div>
                <b>Fecha</b>
                <p>{ventaConfirmada.fecha}</p>
              </div>
            </div>
            <div className="factura-cliente">
              <div>
                <p className="titulo">Cliente</p>
                <p>{ventaConfirmada.cliente.nombreCliente}</p>
              </div>
              <div>
                <p className="titulo">Cédula</p>
                <p>{ventaConfirmada.cliente?.cedulaCliente}</p>
              </div>
              <div>
                <p className="titulo">Usuario</p>
                <p>{ventaConfirmada.usuario}</p>
              </div>
            </div>
            <table className="factura-tabla">
              <thead>
                <tr>
                  <th>Producto</th>
                  <th>Cant</th>
                  <th>Precio</th>
                  <th>Total</th>
                </tr>
              </thead>
              <tbody>
                {ventaConfirmada.productos.map((p, i) => (
                  <tr key={i}>
                    <td>{p.nombre}</td>
                    <td>{p.cantidad}</td>
                    <td>{formatoCOP(p.valorUnitario)}</td>
                    <td>{formatoCOP(p.total)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            <div className="factura-totales">
              <div>
                <span>Subtotal</span>
                <span>{formatoCOP(ventaConfirmada.subtotal)}</span>
              </div>
              <div>
                <span>IVA</span>
                <span>{formatoCOP(ventaConfirmada.iva)}</span>
              </div>
              <div className="factura-total">
                <span>Total</span>
                <span>{formatoCOP(ventaConfirmada.total)}</span>
              </div>
            </div>
            <div className="factura-botones">
              <button className="btn-pdf" onClick={generarFactura}>
                Descargar PDF
              </button>
              <button className="btn-cerrar" onClick={() => setMostrarFactura(false)}>
                Cerrar
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default Venta;