package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.ReporteEntity;

import serfor.rrhh.almacen.repository.ReporteRepository;
import serfor.rrhh.almacen.service.ReporteService;

import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService{

    @Autowired
    private ReporteRepository reporteRepository;

    @Override
    public Pageable<List<ReporteEntity>> ListarReporteSalidas(String tipoTransferencia, Integer nuIdAlmacen,
                                                              String tipoEspecie, String periodo, Page page)
    throws Exception {
        return reporteRepository.ListarReporteSalidas( tipoTransferencia, nuIdAlmacen,tipoEspecie,periodo, page);
    }

}
