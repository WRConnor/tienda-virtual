import { NavLink, Outlet, useNavigate } from "react-router-dom";
import { useAuth } from "../../modules/auth/context/authContext";
import "../styles/Layout.css";

function Layout() {
  const navigate = useNavigate();
  const { esAdmin, esCliente, logout } = useAuth();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className="app-container">
      
      <header className="app-header">
        <div className="header-left">
          <h1>Tienda Genérica</h1>
          <span className="header-subtitle">
            {esAdmin() ? "Sistema Administrativo" : "Panel del Cliente"}
          </span>
        </div>

        <button className="logout-btn" onClick={handleLogout}>
          Cerrar sesión
        </button>
      </header>

      <div className="app-body">

        <aside className="sidebar">
          <nav>

            {esAdmin() && (
              <>
                <NavLink to="/usuarios" className="nav-item">Usuarios</NavLink>
                <NavLink to="/clientes" className="nav-item">Clientes</NavLink>
                <NavLink to="/proveedores" className="nav-item">Proveedores</NavLink>
                <NavLink to="/productos" className="nav-item">Productos</NavLink>
                <NavLink to="/ventas" className="nav-item">Ventas</NavLink>
                <NavLink to="/reportes" className="nav-item">Reportes</NavLink>
              </>
            )}

            {esCliente() && (
              <>
                <NavLink to="/productos" className="nav-item">Ver Productos</NavLink>
                <NavLink to="/carrito" className="nav-item">Carrito</NavLink>
                <NavLink to="/mis-compras" className="nav-item">Mis Compras</NavLink>
                <NavLink to="/perfil" className="nav-item">Mi Perfil</NavLink>
              </>
            )}

          </nav>
        </aside>

        <main className="main-content">
          <Outlet />
        </main>

      </div>
    </div>
  );
}

export default Layout;