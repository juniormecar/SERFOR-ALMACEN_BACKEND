package serfor.rrhh.almacen.service;

import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.ReporteEntity;

import java.util.List;

public interface ReporteService {

    Pageable<List<ReporteEntity>> ListarReporteSalidas(String tipoTransferencia, Integer nuIdAlmacen,
                                                       String tipoEspecie, String periodo, Page page) throws Exception;

    Pageable<List<ReporteEntity>> ListarReporteIndicadores(Integer nuIdAlmacen,String periodo, String tipoAccion, Page page) throws Exception;
}
