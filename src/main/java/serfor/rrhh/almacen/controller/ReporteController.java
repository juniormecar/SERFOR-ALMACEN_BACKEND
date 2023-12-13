package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.ReporteEntity;
import serfor.rrhh.almacen.service.ReporteService;

import java.util.List;

@RestController
@RequestMapping("/api/serfor/reporte")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ReporteController.class);

    @GetMapping("/reporteSalidas")
    public Pageable<List<ReporteEntity>> ListarReporteSalidas(@RequestParam(required = false) String tipoTransferencia,
                                                              @RequestParam(required = false) Integer nuIdAlmacen,
                                                              @RequestParam(required = false) String tipoEspecie,
                                                              @RequestParam(required = false) String periodo,
                                                              @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                              @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                              @RequestParam(required = false, defaultValue = "idTransferencia") String sortField,
                                                              @RequestParam(required = false, defaultValue = "DESC") String sortType)
            throws Exception {
        log.info("ReporteController - ListarReporteSalidas", tipoTransferencia,nuIdAlmacen,tipoEspecie,periodo);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);
        try {
            Pageable<List<ReporteEntity>> response = reporteService.ListarReporteSalidas(tipoTransferencia,nuIdAlmacen,tipoEspecie,periodo, p);
            log.info("ReporteController - ListarReporteSalidas", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("ReporteController - ListarReporteSalidas", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

}
