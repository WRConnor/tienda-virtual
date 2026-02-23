package co.edu.unbosque.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.tienda.model.Usuario;
import co.edu.unbosque.tienda.repository.UsuarioRepository;

@Service
public class UsuarioService implements CRUDOperations<Usuario> {
	
	@Autowired
	UsuarioRepository usuarioRepo;

	@Override
	public int crear(Usuario o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminar(Long in) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Usuario> mostrarTodo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int actualizar(Long id, Usuario ob) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Optional<Usuario> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
