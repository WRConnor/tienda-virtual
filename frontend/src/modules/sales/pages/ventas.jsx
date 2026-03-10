import { useState } from "react";
import { api } from "../../../api/api";
import "../styles/Venta.css";

function Venta() {

  const [cedulaCliente, setCedulaCliente] = useState("");
  const [clienteEncontrado, setClienteEncontrado] = useState(null);

  const [codigoProducto, setCodigoProducto] = useState("");
  const [productoActual, setProductoActual] = useState(null);
  const [cantidad, setCantidad] = useState("");

  const [detalle, setDetalle] = useState([]);
  const [consecutivo, setConsecutivo] = useState(1);

  // =============================
  // BUSCAR CLIENTE
  // =============================
  const buscarCliente = async () => {

    if (!cedulaCliente) return;

    try {

      const cliente = await api.obtenerCliente(cedulaCliente);

      console.log("Cliente recibido:", cliente); // 👈 IMPORTANTE

      setClienteEncontrado(cliente);

    } catch (error) {

      console.error(error);
      alert("Cliente no encontrado");

    }

  };

  // =============================
  // BUSCAR PRODUCTO
  // =============================
  const buscarProducto = async () => {

    if (!codigoProducto) return;

    try {

      const producto = await api.obtenerProducto(codigoProducto);

      if (!producto) {
        alert("Producto no encontrado");
        setProductoActual(null);
        return;
      }

      setProductoActual(producto);

    } catch (error) {

      console.error("Error consultando producto:", error);

      if (error.response?.status === 500) {
        alert("Producto no encontrado");
      } else {
        alert("Error consultando producto");
      }

      setProductoActual(null);

    }

  };

  // =============================
  // AGREGAR PRODUCTO
  // =============================
  const agregarProducto = () => {

    if (!productoActual || !cantidad) {
      alert("Seleccione producto y cantidad");
      return;
    }

    const totalProducto =
    productoActual.precioVenta * cantidad;

    const nuevoDetalle = {

      codigo: productoActual.codigoProducto,
      nombre: productoActual.nombreProducto,
      cantidad: Number(cantidad),
      valorUnitario: productoActual.precioVenta,
      total: totalProducto,
      iva: productoActual.iva

    };

    setDetalle([...detalle, nuevoDetalle]);

    setCodigoProducto("");
    setCantidad("");
    setProductoActual(null);

  };

  // =============================
  // CONFIRMAR VENTA
  // =============================
  const totalizar = async () => {

    if (!clienteEncontrado) {
      alert("Debe seleccionar un cliente");
      return;
    }

    if (detalle.length === 0) {
      alert("Debe agregar productos");
      return;
    }

    const subtotal = detalle.reduce(
      (acc, item) => acc + item.total,
      0
    );

    const totalIVA = detalle.reduce(
      (acc, item) => acc + (item.total * item.iva / 100),
      0
    );

    const totalConIVA = subtotal + totalIVA;

    const venta = {

      cedulaCliente: cedulaCliente,
      cedulaUsuario: "admin",
      subtotal: subtotal,
      totalIVA: totalIVA,
      totalVenta: totalConIVA

    };

    try {

      const ventaCreada = await api.crearVenta(venta);

      const codigoVenta =
        ventaCreada?.codigoVenta || consecutivo;

      for (const item of detalle) {

        const detalleVenta = {

          codigoVenta: codigoVenta,
          codigoProducto: item.codigo,
          cantidad: item.cantidad,
          valorUnitario: item.valorUnitario,
          total: item.total

        };

        await api.crearDetalleVenta(detalleVenta);

      }

      alert("Venta registrada correctamente");

      setDetalle([]);
      setCedulaCliente("");
      setClienteEncontrado(null);
      setConsecutivo(consecutivo + 1);

    } catch (error) {

      console.error(error);
      alert("Error registrando venta");

    }

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

      <div className="venta-card">

        {/* ========================= */}
        {/* CLIENTE */}
        {/* ========================= */}

        <div className="fila-superior">

          <div className="campo">
            <label>Cédula</label>
            <input
              value={cedulaCliente}
              onChange={(e) =>
                setCedulaCliente(e.target.value)
              }
            />
          </div>

          <button onClick={buscarCliente}>
            Consultar
          </button>

          <div className="campo">
            <label>Cliente</label>
            <input
              value={clienteEncontrado?.nombreCliente || ""}
              readOnly
            />
          </div>

          <div className="campo">
            <label>Consec.</label>
            <input value={consecutivo} readOnly />
          </div>

        </div>

        {/* ========================= */}
        {/* PRODUCTO */}
        {/* ========================= */}

        <div className="fila-producto">

          <div className="campo">
            <label>Cod. Producto</label>
            <input
              value={codigoProducto}
              onChange={(e) =>
                setCodigoProducto(e.target.value)
              }
            />
          </div>

          <button onClick={buscarProducto}>
            Consultar
          </button>

          <div className="campo">
            <label>Nombre Producto</label>
            <input
              value={productoActual?.nombreProducto || ""}
              readOnly
            />
          </div>

          <div className="campo small">
            <label>Cant.</label>
            <input
              type="number"
              value={cantidad}
              onChange={(e) =>
                setCantidad(e.target.value)
              }
            />
          </div>

          <div className="campo">
            <label>Vlr. Total</label>
            <input
              value={
                productoActual && cantidad
                  ? productoActual.precio_venta *
                    cantidad
                  : ""
              }
              readOnly
            />
          </div>

          <button onClick={agregarProducto}>
            Agregar
          </button>

        </div>

        {/* ========================= */}
        {/* DETALLE */}
        {/* ========================= */}

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

        {/* ========================= */}
        {/* TOTALES */}
        {/* ========================= */}

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