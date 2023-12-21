package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.ReporteEntity;

import java.util.Date;
import java.util.List;

public interface ReporteRepository {
    Pageable<List<ReporteEntity>> ListarReporteSalidas(String tipoTransferencia, Integer nuIdAlmacen,
                                                       String tipoEspecie, String periodo, Date fechaInicio, Date fechaFin,String numeroDocumento, Page page) throws Exception;


    Pageable<List<ReporteEntity>> ListarReporteIndicadores(Integer nuIdAlmacen,String periodo,String tipoAccion,
                                                           String numeroDocumento,String detalleReporte, Page page) throws Exception;
    Pageable<List<ReporteEntity>> ListarReporteDisponibilidad(Integer nuIdAlmacen,
                                                           String numeroDocumento, Page page) throws Exception;
}
