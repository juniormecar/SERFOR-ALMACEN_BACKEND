package serfor.rrhh.almacen.service;

import serfor.rrhh.almacen.entity.*;

import java.util.List;

public interface FaunaDetalleService {
    ResultClassEntity RegistroFaunaDetalle (List<FaunaDetalleEntity> faunaDetalle) throws Exception;

    Pageable<List<FaunaDetalleEntity>> ListarFaunaDetalle(Integer nuIdRecursoProducto, Page page) throws Exception;

    ResultClassEntity EliminarFaunaDetalle (Integer nuIdFaunaDetalle,Integer idUsuarioElimina) throws Exception;
}
