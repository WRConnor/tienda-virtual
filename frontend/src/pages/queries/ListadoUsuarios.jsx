import { usuariosMock } from "../../config/mockData";
import TablaGenerica from "../../components/TablaGenerica";

function ListadoUsuarios() {

  const columnas = ["Cédula", "Nombre", "Usuario", "Correo", "Estado"];

  return (
    <div>
      <h2>Listado de Usuarios</h2>
      <TablaGenerica columnas={columnas} datos={usuariosMock} />
    </div>
  );
}

export default ListadoUsuarios;