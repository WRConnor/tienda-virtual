import { clientesMock } from "../../config/mockData";
import TablaGenerica from "../../components/TablaGenerica";

function ListadoClientes() {

  const columnas = ["Cédula", "Nombre", "Teléfono", "Correo"];

  return (
    <div>
      <h2>Listado de Clientes</h2>
      <TablaGenerica columnas={columnas} datos={clientesMock} />
    </div>
  );
}

export default ListadoClientes;