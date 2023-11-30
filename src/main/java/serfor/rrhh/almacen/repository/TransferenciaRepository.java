package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.*;

import java.util.List;

public interface TransferenciaRepository {
    ResultClassEntity RegistrarTransferencia(List<TransferenciaEntity> recurso) throws Exception;

    Pageable<List<TransferenciaEntity>> ListarTransferencia(Integer nuIdAlmacen,String documento,
                                                            String tipoTransferencia,Integer nuIdTransferencia,Page page) throws Exception;

    Pageable<List<TransferenciaEntity>> ListarReportesAvanzados(Integer nuIdAlmacen,
                                                            String tipoTransferencia, Integer nuIdTransferencia,Page page) throws Exception;

}
