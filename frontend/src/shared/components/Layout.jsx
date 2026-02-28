import { NavLink, Outlet, useNavigate } from "react-router-dom";
import "../styles/Layout.css";

function Layout() {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <div className="app-container">
      
      {/* Header */}
      <header className="app-header">
        <div className="header-left">
          <h1>Tienda Genérica</h1>
          <span className="header-subtitle">Sistema Administrativo</span>
        </div>

        <button className="logout-btn" onClick={logout}>
          Cerrar sesión
        </button>
      </header>

      {/* Body */}
      <div className="app-body">

        {/* Sidebar */}
        <aside className="sidebar">
          <nav>
            <NavLink to="/usuarios" className="nav-item">
              Usuarios
            </NavLink>

            <NavLink to="/clientes" className="nav-item">
              Clientes
            </NavLink>

            <NavLink to="/proveedores" className="nav-item">
              Proveedores
            </NavLink>

            <NavLink to="/productos" className="nav-item">
              Productos
            </NavLink>

            <NavLink to="/ventas" className="nav-item">
              Ventas
            </NavLink>

            <NavLink to="/reportes" className="nav-item">
              Reportes
            </NavLink>
          </nav>
        </aside>

        {/* Main Content */}
        <main className="main-content">
          <Outlet />
        </main>

      </div>
    </div>
  );
}

export default Layout;