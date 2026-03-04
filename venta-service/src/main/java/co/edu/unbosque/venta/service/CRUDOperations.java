package co.edu.unbosque.venta.service;

import java.util.List;
import java.util.Optional;

public interface CRUDOperations<T> {

	public int crear(T o);
	public int eliminar(Long in);
	public List<T> mostrarTodo();
	public int actualizar(Long id, T ob);
	public Optional<T> buscarPorId(Long id);

}
