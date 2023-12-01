package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.PuestoControlEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;
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
    public Pageable<List<PuestoControlEntity>> ListarPuestoControl(@RequestParam(required = false) Integer idAtf,
                                                                   @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                                   @RequestParam(required = false, defaultValue = "100") Long pageSize,
                                                                   @RequestParam(required = false) String sortField,
                                                                   @RequestParam(required = false, defaultValue = "ASC") String sortType)
        throws Exception {
        log.info("PuestodeControlController - ListarPuestoControl");
        Page p = new Page(pageNumber, pageSize, sortField, sortType);
        try {
            Pageable<List<PuestoControlEntity>> response = puestocontrolService.ListarPuestoControl(idAtf,p);
            log.info("PuestodeControlController - ListarPuestoControl", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("PuestodeControlController -ListarPuestoControl", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @PostMapping("/registrarPuestoControl")
    public ResponseEntity registrarPuestoControl(@RequestBody PuestoControlEntity puestoControl){
        log.info("Ingreso al metodo registrar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = puestocontrolService.registrarPuestoControl(puestoControl);
            if(response.getSuccess()){
                log.info("registrarPuestoControl - exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("TipoParametro -registrarTipoParametro", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminarPuestoControl")
    public ResponseEntity EliminarPuestoControl(@RequestParam(required = false) Integer idPuestoControl){
        log.info("Ingreso al metodo eliminar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = puestocontrolService.EliminarPuestoControl(idPuestoControl);
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
