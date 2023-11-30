package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.AtfEntity;
import serfor.rrhh.almacen.service.AtfService;

import java.util.List;


@RestController
@RequestMapping("/api/serfor/Atf")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AtfController {
    @Autowired
    private AtfService atfService;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(AtfController.class);

    @GetMapping("")
    public List<AtfEntity> ListarATF()
            throws Exception {
        log.info("UbigeoController - ListarDepartamento");
        try {
            List<AtfEntity> response = atfService.ListarATF();
            log.info("UbigeoController - ListarDepartamento", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("UbigeoController -ListarDepartamento", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

}
