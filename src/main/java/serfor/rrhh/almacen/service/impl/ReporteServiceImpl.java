package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.ReporteEntity;

import serfor.rrhh.almacen.repository.ReporteRepository;
import serfor.rrhh.almacen.service.ReporteService;

import java.util.Date;
import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService{

    @Autowired
    private ReporteRepository reporteRepository;

    @Override
    public Pageable<List<ReporteEntity>> ListarReporteSalidas(String tipoTransferencia, Integer nuIdAlmacen,
                                                              String tipoEspecie, String periodo, Date fechaInicio, Date fechaFin, Page page)
    throws Exception {
        return reporteRepository.ListarReporteSalidas( tipoTransferencia, nuIdAlmacen,tipoEspecie,periodo, fechaInicio, fechaFin, page);
    }

    @Override
    public Pageable<List<ReporteEntity>> ListarReporteIndicadores(Integer nuIdAlmacen,String periodo,String tipoAccion, Page page)
            throws Exception {
        return reporteRepository.ListarReporteIndicadores( nuIdAlmacen,periodo,tipoAccion,page);
    }

}
