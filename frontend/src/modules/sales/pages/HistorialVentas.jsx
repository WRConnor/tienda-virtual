import { useEffect, useState } from "react";
import { api } from "../../api/api";

function HistorialVentas(){

const [ventas,setVentas] = useState([]);

useEffect(()=>{

cargarVentas();

},[])

const cargarVentas = async () =>{

try{

const data = await api.obtenerVentas();
setVentas(data);

}catch(error){

console.error(error);

}

}

return(

<div className="contenedor">

<h2>Historial de Ventas</h2>

<table className="tabla-venta">

<thead>

<tr>

<th>Codigo</th>
<th>Cliente</th>
<th>Usuario</th>
<th>Valor Venta</th>
<th>IVA</th>
<th>Total</th>

</tr>

</thead>

<tbody>

{ventas.map((v)=>(
<tr key={v.codigoVenta}>

<td>{v.codigoVenta}</td>
<td>{v.cedulaCliente}</td>
<td>{v.cedulaUsuario}</td>
<td>{v.valorVenta}</td>
<td>{v.ivaventa}</td>
<td>{v.totalVenta}</td>

</tr>
))}

</tbody>

</table>

</div>

)

}

export default HistorialVentas;