package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.AtfEntity;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.ResultClassEntity;
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
    public Pageable<List<AtfEntity>> ListarATF(@RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                               @RequestParam(required = false, defaultValue = "100") Long pageSize,
                                               @RequestParam(required = false) String sortField,
                                               @RequestParam(required = false, defaultValue = "ASC") String sortType)
        throws Exception {
        log.info("AtfController - ListarATF");
        Page p = new Page(pageNumber, pageSize, sortField, sortType);
        try {
            Pageable<List<AtfEntity>> response = atfService.ListarATF(p);
            log.info("AtfController - ListarATF", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("AtfController -ListarATF", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @PostMapping("/registrarATF")
    public ResponseEntity RegistrarATF(@RequestBody AtfEntity atf){
        log.info("Ingreso al metodo registrar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = atfService.RegistrarATF(atf);
            if(response.getSuccess()){
                log.info("RegistrarATF - exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("RegistrarATF", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminarATF")
    public ResponseEntity EliminarATF(@RequestParam(required = false) Integer idAtf){
        log.info("Ingreso al metodo eliminar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = atfService.EliminarATF(idAtf);
            if(response.getSuccess()){
                log.info("eliminar - exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("eliminar", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
