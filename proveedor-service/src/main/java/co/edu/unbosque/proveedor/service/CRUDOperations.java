package co.edu.unbosque.proveedor.service;

import java.util.List;
import java.util.Optional;

import co.edu.unbosque.proveedor.model.Proveedor;

public interface CRUDOperations<T> {

	public int crear(T o);

	public int eliminar(Long in);

	public List<T> mostrarTodo();

	public int actualizar(Long id, T ob);

	public Optional<T> buscarPorId(Long id);

	Optional<Proveedor> buscarPorNIT(Long nit);

}
