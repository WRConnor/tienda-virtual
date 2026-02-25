import { useState } from "react";
import "../css/Venta.css";

function Venta() {

  const clientes = [
    { cedula: "100", nombre: "Carlos Pérez" }
  ];

  const productosDisponibles = [
    { codigo: 1, nombre: "Melocotones", precio_venta: 30351, iva: 19 },
    { codigo: 2, nombre: "Manzanas", precio_venta: 21549, iva: 19 },
    { codigo: 3, nombre: "Plátanos", precio_venta: 35320, iva: 19 }
  ];

  const [cedulaCliente, setCedulaCliente] = useState("");
  const [clienteEncontrado, setClienteEncontrado] = useState(null);

  const [codigoProducto, setCodigoProducto] = useState("");
  const [productoActual, setProductoActual] = useState(null);
  const [cantidad, setCantidad] = useState("");

  const [detalle, setDetalle] = useState([]);
  const [ventas, setVentas] = useState([]);
  const [detalleVentas, setDetalleVentas] = useState([]);

  const buscarCliente = () => {
    const cliente = clientes.find(c => c.cedula === cedulaCliente);
    if (cliente) {
      setClienteEncontrado(cliente);
    } else {
      alert("Cliente no encontrado");
    }
  };

  const buscarProducto = () => {
    const producto = productosDisponibles.find(
      p => p.codigo === Number(codigoProducto)
    );

    if (producto) {
      setProductoActual(producto);
    } else {
      alert("Producto no encontrado");
    }
  };

  const agregarProducto = () => {
    if (!productoActual || !cantidad) return;

    const totalProducto =
      productoActual.precio_venta * Number(cantidad);

    const nuevoDetalle = {
      codigo: productoActual.codigo,
      nombre: productoActual.nombre,
      cantidad: Number(cantidad),
      valorUnitario: productoActual.precio_venta,
      total: totalProducto,
      iva: productoActual.iva
    };

    setDetalle([...detalle, nuevoDetalle]);

    setCodigoProducto("");
    setCantidad("");
    setProductoActual(null);
  };

  const totalizar = () => {

    const subtotal = detalle.reduce(
      (acc, item) => acc + item.total,
      0
    );

    const totalIVA = detalle.reduce(
      (acc, item) => acc + (item.total * item.iva / 100),
      0
    );

    const totalConIVA = subtotal + totalIVA;

    const codigoVenta = ventas.length + 1;

    const nuevaVenta = {
      codigoVenta,
      cedulaCliente,
      cedulaUsuario: "admin",
      subtotal,
      totalIVA,
      totalConIVA
    };

    setVentas([...ventas, nuevaVenta]);

    const nuevoDetalleVentas = detalle.map(item => ({
      codigoVenta,
      codigoProducto: item.codigo,
      cantidad: item.cantidad,
      valorUnitario: item.valorUnitario,
      total: item.total
    }));

    setDetalleVentas([...detalleVentas, ...nuevoDetalleVentas]);

    alert("Venta registrada");

    setDetalle([]);
    setCedulaCliente("");
    setClienteEncontrado(null);
  };

  const subtotal = detalle.reduce((acc, item) => acc + item.total, 0);
  const totalIVA = detalle.reduce(
    (acc, item) => acc + (item.total * item.iva / 100),
    0
  );
  const totalConIVA = subtotal + totalIVA;

  return (
    <div className="venta-wrapper">

      <h2>Ventas</h2>

      {/* BLOQUE SUPERIOR */}
      <div className="venta-card">

        <div className="fila-superior">
          <div className="campo">
            <label>Cédula</label>
            <input
              value={cedulaCliente}
              onChange={(e) => setCedulaCliente(e.target.value)}
            />
          </div>

          <button onClick={buscarCliente}>
            Consultar
          </button>

          <div className="campo">
            <label>Cliente</label>
            <input
              value={clienteEncontrado?.nombre || ""}
              readOnly
            />
          </div>

          <div className="campo">
            <label>Consec.</label>
            <input value={ventas.length + 1} readOnly />
          </div>
        </div>

        {/* PRODUCTO */}
        <div className="fila-producto">

          <div className="campo">
            <label>Cod. Producto</label>
            <input
              value={codigoProducto}
              onChange={(e) => setCodigoProducto(e.target.value)}
            />
          </div>

          <button onClick={buscarProducto}>
            Consultar
          </button>

          <div className="campo">
            <label>Nombre Producto</label>
            <input
              value={productoActual?.nombre || ""}
              readOnly
            />
          </div>

          <div className="campo small">
            <label>Cant.</label>
            <input
              type="number"
              value={cantidad}
              onChange={(e) => setCantidad(e.target.value)}
            />
          </div>

          <div className="campo">
            <label>Vlr. Total</label>
            <input
              value={
                productoActual && cantidad
                  ? productoActual.precio_venta * cantidad
                  : ""
              }
              readOnly
            />
          </div>

          <button onClick={agregarProducto}>
            Agregar
          </button>

        </div>

        {/* DETALLE */}
        <div className="detalle-lista">
          {detalle.map((item, index) => (
            <div key={index} className="detalle-item">
              <span>{item.codigo}</span>
              <span>{item.nombre}</span>
              <span>{item.cantidad}</span>
              <span>{item.total}</span>
            </div>
          ))}
        </div>

        {/* TOTALES + CONFIRMAR */}
        <div className="seccion-final">

          <div className="totales">
            <div>
              <label>Total Venta</label>
              <input value={subtotal} readOnly />
            </div>

            <div>
              <label>Total IVA</label>
              <input value={totalIVA} readOnly />
            </div>

            <div>
              <label>Total con IVA</label>
              <input value={totalConIVA} readOnly />
            </div>
          </div>

          <div className="confirmar">
            <button onClick={totalizar}>
              Confirmar
            </button>
          </div>

        </div>

      </div>

    </div>
  );
}

export default Venta;