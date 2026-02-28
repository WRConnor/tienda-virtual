export const usuariosMock = [
  { cedula: 1010, nombre: "Carlos Ruiz", usuario: "cruiz", correo: "carlos@mail.com", estado: "Activo" },
  { cedula: 2020, nombre: "Laura Gómez", usuario: "lgomez", correo: "laura@mail.com", estado: "Activo" }
];

export const clientesMock = [
  { cedula: 3001, nombre: "Andrés Torres", telefono: "3001234567", correo: "andres@mail.com" },
  { cedula: 3002, nombre: "María López", telefono: "3019876543", correo: "maria@mail.com" }
];

export const ventasMock = [
  { cedulaCliente: 3001, total: 500000 },
  { cedulaCliente: 3001, total: 200000 },
  { cedulaCliente: 3002, total: 800000 }
];

export const roles = [
  {
    id: 1,
    username: "admin",
    password: "admin123456",
    rol: "ADMIN"
  },
  {
    id: 2,
    username: "Carlos Ruiz",
    password: "1234",
    rol: "USER"
  }
];