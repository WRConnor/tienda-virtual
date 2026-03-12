package co.edu.unbosque.venta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.venta.model.DetalleVenta;
import co.edu.unbosque.venta.repository.DetalleVentaRepository;

@Service
public class DetalleVentaService implements CRUDOperations<DetalleVenta> {

    @Autowired
    DetalleVentaRepository detalleVentaRepo;

    @Override
    public int crear(DetalleVenta o) {

        if (findTitleAlreadyTaken(o)) {
            return 1;
        }

        detalleVentaRepo.save(o);

        return 0;
    }

    @Override
    public int eliminar(Long id) {

        if (detalleVentaRepo.existsById(id)) {

            detalleVentaRepo.deleteById(id);

            return 0;
        }

        return 1;
    }

    @Override
    public List<DetalleVenta> mostrarTodo() {

        return detalleVentaRepo.findAll();
    }

    @Override
    public int actualizar(Long id, DetalleVenta nuevaData) {

        Optional<DetalleVenta> found = detalleVentaRepo.findById(id);

        if (found.isPresent()) {

            DetalleVenta temp = found.get();

            temp.setCantidadProducto(nuevaData.getCantidadProducto());
            temp.setValorTotal(nuevaData.getValorTotal());
            temp.setValorVenta(nuevaData.getValorVenta());
            temp.setValorIva(nuevaData.getValorIva());
            temp.setCodigoProducto(nuevaData.getCodigoProducto());
            temp.setVenta(nuevaData.getVenta());

            detalleVentaRepo.save(temp);

            return 0;
        }

        return 1;
    }

    @Override
    public Optional<DetalleVenta> buscarPorId(Long id) {

        return detalleVentaRepo.findById(id);
    }

    public boolean findTitleAlreadyTaken(DetalleVenta newDetalleVenta) {

        Optional<DetalleVenta> found =
                detalleVentaRepo.findByCodigoDetalleVenta(newDetalleVenta.getCodigoDetalleVenta());

        return found.isPresent();
    }
}
