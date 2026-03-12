import axios from "axios";

const axiosClient = axios.create({
  baseURL: "/api" // proxy al backend
});

// INTERCEPTOR JWT
axiosClient.interceptors.request.use((config) => {
  if (!config.url.includes("/login")) {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }
  return config;
});

export const api = {
  // Usuarios
  login: async ({ username, password }) => {
    const response = await axiosClient.post(
      "/usuarios/login",
      null,
      { params: { usuario: username, password: password } }
    );
    return response.data;
  },
  getUsuarios: async () => {
    const response = await axiosClient.get("/usuarios/mostrartodo");
    return response.data;
  },
  crearUsuario: async (usuario) => {
    const response = await axiosClient.post("/usuarios/crear", usuario);
    return response.data;
  },
  actualizarUsuario: async (id, usuario) => {
    const response = await axiosClient.put(`/usuarios/actualizar/${id}`, usuario);
    return response.data;
  },
  borrarUsuario: async (id) => {
    const response = await axiosClient.delete(`/usuarios/eliminar/${id}`);
    return response.data;
  },

  //clientes
  getClientes: async () => {
    const response = await axiosClient.get("/clientes/mostrartodo");
    return response.data;
  },
  crearCliente: async (data) => {
    const response = await axiosClient.post("/clientes/crear", data); 
    return response.data;
  },
  actualizarCliente: async (id, data) => {
    const response = await axiosClient.put(`/clientes/actualizar/${id}`, data);   
    return response.data;
  },  
  borrarCliente: async (id) => {
    const response = await axiosClient.delete(`/clientes/eliminar/${id}`);    
    return response.data;
  },

  // Proveedores
  getProveedores: async () => {
    const response = await axiosClient.get("/proveedores/mostrartodo");
    return response.data;
  },
  crearProveedor: async (data) => {
    const response = await axiosClient.post("/proveedores/crear", data);
    return response.data;
  },
  actualizarProveedor: async (id, data) => {
    const response = await axiosClient.put(`/proveedores/actualizar/${id}`, data);
    return response.data;
  },
  borrarProveedor: async (id) => {
    const response = await axiosClient.delete(`/proveedores/eliminar/${id}`);
    return response.data;
  },

  // Productos
  getProductos: async () => {
    const response = await axiosClient.get("/productos/mostrartodo");
    return response.data;
  },
  crearProducto: async (data) => {
    const response = await axiosClient.post("/productos/crear", data);
    return response.data;
  },
  actualizarProducto: async (id, data) => {
    const response = await axiosClient.put(`/productos/actualizar/${id}`, data);
    return response.data;
  },
  borrarProducto: async (id) => {
    const response = await axiosClient.delete(`/productos/eliminar/${id}`);
    return response.data;
  },

  //reportes
  obtenerUsuarios: async () => {
    const response = await axiosClient.get("/usuarios/mostrartodo");
    return response.data?.data || response.data?.usuarios || response.data || [];
  },

  obtenerClientes: async () => {
    const response = await axiosClient.get("/clientes/mostrartodo");
    return response.data?.data || response.data?.clientes || response.data || [];
    },

  obtenerVentas: async () => {
    const res = await axiosClient.get("/ventas/mostrartodo");
    return res.data;
  },

};

// =======================
// VENTAS
// =======================

export const apiVentas = {
  // Crear una nueva venta
  crearVenta: async (venta) => {
    const res = await axiosClient.post("/ventas/crear", venta);
    return res.data;
  },

  // Obtener todas las ventas
  obtenerVentas: async () => {
    const res = await axiosClient.get("/ventas/mostrartodo");
    return res.data;
  },

  // =======================
  // BUSCAR CLIENTE
  // =======================
  obtenerCliente: async (cedula) => {
    // Cambiado a GET porque el backend solo acepta GET
    const res = await axiosClient.get(`/ventas/clientes/${cedula}`);
    return res.data;
  },

  // =======================
  // BUSCAR PRODUCTO
  // =======================
  obtenerProducto: async (codigo) => {
    const res = await axiosClient.get(`/ventas/buscarPorCodigo/${codigo}`);
    return res.data;
  },

  // =======================
  // REPORTE DE VENTAS POR CLIENTE
  // =======================
  reporteVentasCliente: async () => {
    const res = await axiosClient.get("/ventas/reporte-cliente");
    return res.data;
  },
};