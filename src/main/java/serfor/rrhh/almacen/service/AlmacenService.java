package serfor.rrhh.almacen.service;

import serfor.rrhh.almacen.entity.*;

import java.util.List;

public interface AlmacenService {
    Pageable<List<AlmacenEntity>> ListarAlmacen(String txUbigeo, String txNombreAlmacen, String txTipoAlmacen,
                                                String txPuestoControl, String txNumeroATF, String txNumeroDocumento, Page page) throws Exception;

    ResultClassEntity EliminarAlmacen(Integer nuIdAlmacen,Integer nuIdUsuarioElimina) throws Exception;

    ResultClassEntity RegistroAlmacen(AlmacenEntity almacen) throws Exception;

    Pageable<List<AlmacenResponsableEntity>> ListarAlmacenResponsable(Integer nuIdAlmacen,String numeroDocumento, Page page) throws Exception;

    ResultClassEntity EliminarAlmacenResponsable(Integer idAlmacenResponsable,Integer idUsuarioElimina) throws Exception;

    AlmacenEntity getAlmacen(Integer idAlmacen) throws Exception;

}
