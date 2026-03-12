package co.edu.unbosque.usuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.usuario.model.Usuario;
import co.edu.unbosque.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService implements CRUDOperations<Usuario> {
	
	@Autowired
	UsuarioRepository usuarioRepo;

	@Override
	public int crear(Usuario o) {
		if(findTitleAlreadyTaken(o)) {
			return 1;
		}else {
			usuarioRepo.save(o);
			return 0;
		}
	}

	@Override
	public int eliminar(Long in) {
		if(usuarioRepo.existsById(in)) {
			usuarioRepo.deleteById(in);
			return 0;
		}else {
			return 1;
		}
	}

	@Override
	public List<Usuario> mostrarTodo() {
		return usuarioRepo.findAll();
	}

	@Override
	public int actualizar(Long id, Usuario nuevaData) {

		Optional<Usuario> found = usuarioRepo.findById(id);

		if (!found.isPresent()) {
			return 2; 
		}

		Usuario existente = found.get();

		Optional<Usuario> cedulaExistente = usuarioRepo.findByCedulaUsuario(nuevaData.getCedulaUsuario());


		if (cedulaExistente.isPresent() &&
			!cedulaExistente.get().getIdUsuario().equals(id)) {
			return 1; 
		}

		existente.setEmailUsuario(nuevaData.getEmailUsuario());
		existente.setCedulaUsuario(nuevaData.getCedulaUsuario());
		existente.setNombreUsuario(nuevaData.getNombreUsuario());
		existente.setPassword(nuevaData.getPassword());
		existente.setUsuario(nuevaData.getUsuario());

		usuarioRepo.save(existente);

		return 0; // éxito
	}
	public Optional<Usuario> buscarPorId(Long id) {
		return usuarioRepo.findById(id);
	}
	
	public Optional<Usuario> login(String user, String password) {
		for (Usuario u : mostrarTodo()) {
			if (u.getUsuario().equals(user) && u.getPassword().equals(password)){
				return Optional.of(u);
			}
		}
		return Optional.empty();
	}


	
	
	public boolean findTitleAlreadyTaken(Usuario newUsuario) {
		Optional<Usuario> found = usuarioRepo.findByCedulaUsuario(newUsuario.getCedulaUsuario());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	

}
