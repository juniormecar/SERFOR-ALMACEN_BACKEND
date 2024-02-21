package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.TransferenciaRepository;
import serfor.rrhh.almacen.service.TransferenciaService;

import java.util.List;

@Service
public class TransferenciaServiceImpl implements TransferenciaService {

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Override
    public ResultClassEntity RegistrarTransferencia(List<TransferenciaEntity> recurso) throws Exception {
        return transferenciaRepository.RegistrarTransferencia(recurso);
    }

    @Override
    public ResultClassEntity RegistrarRetorno(List<ReporteEntity> reporte) throws Exception {
        return transferenciaRepository.RegistrarRetorno(reporte);
    }

    @Override
    public Pageable<List<TransferenciaEntity>> ListarTransferencia(Integer nuIdAlmacen,String documento,
                                                                   String tipoTransferencia,Integer nuIdTransferencia, Page page)
            throws Exception {
        return transferenciaRepository.ListarTransferencia( nuIdAlmacen, documento,tipoTransferencia,nuIdTransferencia, page);
    }

    @Override
    public Pageable<List<TransferenciaEntity>> ListarReportesAvanzados(Integer nuIdAlmacen,
                                                                   String tipoTransferencia, Integer nuIdTransferencia, String tipoTransferenciaDetalle, Page page)
            throws Exception {
        return transferenciaRepository.ListarReportesAvanzados( nuIdAlmacen,tipoTransferencia,nuIdTransferencia,tipoTransferenciaDetalle, page);
    }
}
