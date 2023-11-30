package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.EspecieFaunaEntity;
import serfor.rrhh.almacen.entity.EspecieForestalEntity;
import serfor.rrhh.almacen.service.CoreCentralService;
import serfor.rrhh.almacen.entity.*;
import java.util.List;

@RestController
@RequestMapping("/api/serfor/almacen/rrhh/coreCentral")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CoreCentralController {

    @Autowired
    private CoreCentralService coreCentral;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(CoreCentralController.class);


    @GetMapping("/listarFauna")
    public Pageable<List<EspecieFaunaEntity>> listaPorFiltroEspecieFauna(@RequestParam(required = false) String nombreEspecie,
                                                               @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                               @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                               @RequestParam(required = false) String sortField,
                                                               @RequestParam(required = false, defaultValue = "DESC") String sortType)
        throws Exception {
        log.info("CoreCentralController - listaPorFiltroEspecieFauna", nombreEspecie );
        Page p = new Page(pageNumber, pageSize, sortField, sortType);
        try {
            Pageable<List<EspecieFaunaEntity>> response = coreCentral.listaPorFiltroEspecieFauna(nombreEspecie,p);
            log.info("CoreCentralController - listaPorFiltroEspecieFauna", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("CoreCentralController -listaPorFiltroEspecieFauna", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @GetMapping("/listarForestal")
    public Pageable<List<EspecieForestalEntity>> listaPorFiltroEspecieForestal(@RequestParam(required = false) Integer idEspecie,
                                                                     @RequestParam(required = false) String nombreComun,
                                                                     @RequestParam(required = false) String nombreCientifico,
                                                                     @RequestParam(required = false) String autor,
                                                                     @RequestParam(required = false) String familia,
                                                                     @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                                     @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                                     @RequestParam(required = false) String sortField,
                                                                     @RequestParam(required = false, defaultValue = "DESC") String sortType)
            throws Exception {
        log.info("CoreCentralController - listaPorFiltroEspecieForestal", idEspecie, nombreComun, nombreCientifico,autor, familia );
        Page p = new Page(pageNumber, pageSize, sortField, sortType);
        try {
            Pageable<List<EspecieForestalEntity>> response = coreCentral.listaPorFiltroEspecieForestal(idEspecie, nombreComun, nombreCientifico ,autor,familia,p);
            log.info("CoreCentralController - listaPorFiltroEspecieForestal", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("CoreCentralController -listaPorFiltroEspecieForestal", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }
}
