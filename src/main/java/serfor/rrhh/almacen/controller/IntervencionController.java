package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.third.pas.entity.IntervencionEntity;
import serfor.rrhh.almacen.third.pas.repository.IntervencionRepository;
import serfor.rrhh.almacen.third.pas.repository.impl.IntervencionDao;
import serfor.rrhh.almacen.third.pas.service.IntervencionService;

import java.util.List;

@RestController
@RequestMapping("/api/serfor/pas/intervencion")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class IntervencionController {

    @Autowired
    private IntervencionDao intervencionDao;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(TransferenciaController.class);
    @GetMapping("")
    public Pageable<List<IntervencionEntity>> ListarIntervencion (@RequestParam(required = false) String nuActa,
                                                                   @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                                   @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                                   @RequestParam(required = false, defaultValue = "NU_IntervencionID") String sortField,
                                                                   @RequestParam(required = false, defaultValue = "DESC") String sortType)
            throws Exception {
        log.info("IntervencionController - listar", nuActa);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);
        try {
            Pageable<List<IntervencionEntity>> response = intervencionDao.ListarIntervencion(nuActa, p);
            log.info("IntervencionController - listar", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("IntervencionController - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }
}
