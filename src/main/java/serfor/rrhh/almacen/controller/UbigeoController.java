package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.service.UbigeoService;
import serfor.rrhh.almacen.entity.DepartamentoEntity;
import serfor.rrhh.almacen.entity.ProvinciaEntity;
import serfor.rrhh.almacen.entity.DistritoEntity;

import java.util.List;


@RestController
@RequestMapping("/api/serfor/almacen/rrhh/ubigeo")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class UbigeoController {
    @Autowired
    private UbigeoService ubigeoService;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(RecursoController.class);

    @GetMapping("/listarDepartamento")
    public List<DepartamentoEntity> ListarDepartamento()
            throws Exception {
        log.info("UbigeoController - ListarDepartamento");
        try {
            List<DepartamentoEntity> response = ubigeoService.ListarDepartamento();
            log.info("UbigeoController - ListarDepartamento", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("UbigeoController -ListarDepartamento", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @GetMapping("/listarProvincia")
    public List<ProvinciaEntity> ListarProvincia(@RequestParam(required = true) String codigoDepartamento)
            throws Exception {
        log.info("UbigeoController - ListarProvincia");
        try {
            List<ProvinciaEntity> response = ubigeoService.ListarProvincia(codigoDepartamento);
            log.info("UbigeoController - ListarProvincia", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("UbigeoController -ListarProvincia", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @GetMapping("/listarDistrito")
    public List<DistritoEntity> ListarDistrito(@RequestParam(required = true) String codigoProvincia)
            throws Exception {
        log.info("UbigeoController - ListarDistrito");
        try {
            List<DistritoEntity> response = ubigeoService.ListarDistrito(codigoProvincia);
            log.info("UbigeoController - ListarDistrito", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("UbigeoController -ListarDistrito", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }
}
