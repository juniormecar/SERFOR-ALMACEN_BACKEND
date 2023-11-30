package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.*;

import java.util.List;

public interface FaunaDetalleRepository {
    ResultClassEntity RegistroFaunaDetalle(List<FaunaDetalleEntity> faunaDetalle) throws Exception;
    Pageable<List<FaunaDetalleEntity>> ListarFaunaDetalle(Integer nuIdRecursoProducto, Page page) throws Exception;

    ResultClassEntity EliminarFaunaDetalle (Integer nuIdFaunaDetalle,Integer idUsuarioElimina) throws Exception;
}
