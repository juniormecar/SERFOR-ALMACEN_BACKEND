package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.CubicacionEntity;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.ResultClassEntity;

import java.util.List;

public interface CubicacionRepository {
    ResultClassEntity RegistroCubicacion(List<CubicacionEntity> cubicacion) throws Exception;

    Pageable<List<CubicacionEntity>> ListarCubicacion(Integer nuIdRecursoProducto, Page page) throws Exception;

    List<CubicacionEntity> ListarCubicacionPDF(Integer nuIdRecursoProducto, Page page) throws Exception;

    ResultClassEntity EliminarCubicacion (Integer idRecurProCubicacion,Integer idUsuarioElimina) throws Exception;
}
