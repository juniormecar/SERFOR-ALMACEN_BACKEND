package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.*;

import java.util.List;

public interface AlmacenRepository {
    Pageable<List<AlmacenEntity>> ListarAlmacen(String txUbigeo, String txNombreAlmacen,
                                                String txTipoAlmacen, String txPuestoControl, String txNumeroATF,
                                                String txNumeroDocumento, Page page) throws Exception;
    ResultClassEntity EliminarAlmacen(Integer nuIdAlmacen,Integer nuIdUsuarioElimina) throws Exception;
    ResultClassEntity RegistroAlmacen(AlmacenEntity almacen) throws Exception;
    Pageable<List<AlmacenResponsableEntity>> ListarAlmacenResponsable(Integer nuIdAlmacen,String numeroDocumento, Page page) throws Exception;
    ResultClassEntity EliminarAlmacenResponsable(Integer idAlmacenResponsable,Integer idUsuarioElimina) throws Exception;
}
