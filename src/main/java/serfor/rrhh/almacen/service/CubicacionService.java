package serfor.rrhh.almacen.service;


import serfor.rrhh.almacen.entity.CubicacionEntity;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.ResultClassEntity;

import java.util.List;

public interface CubicacionService {

    ResultClassEntity RegistroCubicacion (List<CubicacionEntity> cubicacion) throws Exception;


    Pageable<List<CubicacionEntity>> ListarCubicacion(Integer nuIdRecursoProducto, Page page) throws Exception;

    ResultClassEntity EliminarCubicacion (Integer idRecurProCubicacion,Integer idUsuarioElimina) throws Exception;
}
