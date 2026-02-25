function TablaGenerica({ columnas, datos }) {
  return (
    <div className="tabla-container">
      <table>
        <thead>
          <tr>
            {columnas.map((col, index) => (
              <th key={index}>{col}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {datos.length === 0 ? (
            <tr>
              <td colSpan={columnas.length}>No hay registros</td>
            </tr>
          ) : (
            datos.map((fila, index) => (
              <tr key={index}>
                {Object.values(fila).map((valor, i) => (
                  <td key={i}>{valor}</td>
                ))}
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}

export default TablaGenerica;