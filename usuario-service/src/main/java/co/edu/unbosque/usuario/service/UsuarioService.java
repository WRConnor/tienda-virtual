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
		Optional<Usuario> newFound = usuarioRepo.findByCedulaUsuario(nuevaData.getCedulaUsuario());
		
		if (found.isPresent() && !newFound.isPresent()) {
			Usuario temp = found.get();
			temp.setEmailUsuario(nuevaData.getEmailUsuario());
			temp.setCedulaUsuario(nuevaData.getCedulaUsuario());
			temp.setNombreUsuario(nuevaData.getNombreUsuario());
			temp.setPassword(nuevaData.getPassword());
			temp.setUsuario(nuevaData.getUsuario());
			usuarioRepo.save(temp);
			return 0;
		}
		if (found.isPresent() && newFound.isPresent()) {
			return 1;
		}
		if (!found.isPresent()) {
			return 2;
		} else {
			return 3;
		}
	}

	@Override
	public Optional<Usuario> buscarPorId(Long id) {
		return usuarioRepo.findById(id);
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
