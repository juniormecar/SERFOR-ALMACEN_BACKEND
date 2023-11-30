package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.service.ExternoService;
import serfor.rrhh.almacen.service.UbigeoService;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/serfor/almacen/externos")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ExternosController {
    @Autowired
    private ExternoService externoService;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ExternosController.class);

    @PostMapping("/login")
    ResponseLogin login(@RequestBody RequestLogin param)
            throws Exception {
        log.info("ExtController - login");
        try {
            ResponseLogin response = externoService.getLogin(param);
            log.info("UbigeoController - ListarDepartamento", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("UbigeoController -ListarDepartamento", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

}
