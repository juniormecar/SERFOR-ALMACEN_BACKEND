package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.FaunaDetalleRepository;
import serfor.rrhh.almacen.service.FaunaDetalleService;

import java.util.List;

@Service
public class FaunaDetalleServiceImpl implements FaunaDetalleService {

    @Autowired
    private FaunaDetalleRepository faunaDetalleRepository;

    @Override
    public ResultClassEntity RegistroFaunaDetalle(List<FaunaDetalleEntity> faunaDetalle) throws Exception {
        return faunaDetalleRepository.RegistroFaunaDetalle(faunaDetalle);
    }

    @Override
    public Pageable<List<FaunaDetalleEntity>> ListarFaunaDetalle(Integer nuIdRecursoProducto, Page page)
            throws Exception {
        return faunaDetalleRepository.ListarFaunaDetalle( nuIdRecursoProducto, page);
    }

    @Override
    public ResultClassEntity EliminarFaunaDetalle (Integer nuIdFaunaDetalle,Integer idUsuarioElimina) throws Exception {
        return faunaDetalleRepository.EliminarFaunaDetalle(nuIdFaunaDetalle, idUsuarioElimina);
    }
}
