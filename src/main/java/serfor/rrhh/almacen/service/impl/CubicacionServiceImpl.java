package serfor.rrhh.almacen.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.CubicacionEntity;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.ResultClassEntity;
import serfor.rrhh.almacen.repository.CubicacionRepository;
import serfor.rrhh.almacen.service.CubicacionService;

import java.util.List;

@Service
public class CubicacionServiceImpl implements CubicacionService {

    @Autowired
    private CubicacionRepository cubicacionRepository;
    @Override
    public ResultClassEntity RegistroCubicacion(List<CubicacionEntity> cubicacion) throws Exception {
        return cubicacionRepository.RegistroCubicacion(cubicacion);
    }

    @Override
    public Pageable<List<CubicacionEntity>> ListarCubicacion(Integer nuIdRecursoProducto, Page page)
            throws Exception {
        return cubicacionRepository.ListarCubicacion( nuIdRecursoProducto, page);
    }

    @Override
    public ResultClassEntity EliminarCubicacion (Integer idRecurProCubicacion,Integer idUsuarioElimina) throws Exception {
        return cubicacionRepository.EliminarCubicacion(idRecurProCubicacion, idUsuarioElimina);
    }

}
