package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import serfor.rrhh.almacen.entity.PuestoControlEntity;
import serfor.rrhh.almacen.service.PuestoControlService;


import java.util.List;

@RestController
@RequestMapping("/api/serfor/puestoControl")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PuestodeControlController {
    @Autowired
    private PuestoControlService puestocontrolService;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(PuestodeControlController.class);

    @GetMapping("")
    public List<PuestoControlEntity> ListarPuestoControl(@RequestParam(required = false) Integer idAtf)
            throws Exception {
        log.info("PuestodeControlController - ListarPuestoControl");
        try {
            List<PuestoControlEntity> response = puestocontrolService.ListarPuestoControl(idAtf);
            log.info("PuestodeControlController - ListarPuestoControl", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("PuestodeControlController -ListarPuestoControl", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

}
