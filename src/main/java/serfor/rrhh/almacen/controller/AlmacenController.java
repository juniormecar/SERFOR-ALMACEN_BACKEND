package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.service.AlmacenService;

import java.util.List;

@RestController
@RequestMapping("/api/serfor/almacen")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class AlmacenController {

    @Autowired
    private AlmacenService almacenService;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(RecursoController.class);


    @GetMapping("")
    public Pageable<List<AlmacenEntity>> ListarAlmacen(@RequestParam(required = false) String txUbigeo, //
                                                   @RequestParam(required = false) String txNombreAlmacen, //
                                                   @RequestParam(required = false) String txTipoAlmacen, //
                                                   @RequestParam(required = false) String txPuestoControl, //
                                                   @RequestParam(required = false) String txNumeroATF,
                                                   @RequestParam(required = false) String txNumeroDocumento,
                                                   @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                   @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                   @RequestParam(required = false) String sortField,
                                                   @RequestParam(required = false, defaultValue = "DESC") String sortType)
        throws Exception {
        log.info("AlmacenController - listar", txUbigeo, txNombreAlmacen, txTipoAlmacen, txPuestoControl,txNumeroATF,txNumeroDocumento);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);
        try {
            Pageable<List<AlmacenEntity>> response = almacenService.ListarAlmacen(txUbigeo, txNombreAlmacen,
                    txTipoAlmacen, txPuestoControl, txNumeroATF,txNumeroDocumento, p);
            log.info("AlmacenController - listar", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("AlmacenController - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @DeleteMapping("")
    public ResponseEntity EliminarAlmacen(@RequestParam(required = false) Integer nuIdAlmacen,
                                          @RequestParam(required = false) Integer nuIdUsuarioElimina){
        log.info("Ingreso al metodo eliminar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = almacenService.EliminarAlmacen(nuIdAlmacen, nuIdUsuarioElimina);
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

    @PostMapping("")
    public ResponseEntity RegistroAlmacen(@RequestBody AlmacenEntity almacen){
        log.info("Ingreso al metodo registrar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = almacenService.RegistroAlmacen(almacen);
            if(response.getSuccess()){
                log.info("RegistrarAlmacen - exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("RegistrarAlmacen -listar", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listarAlmacenResponsable")
    public Pageable<List<AlmacenResponsableEntity>> ListarAlmacenResponsable
                                                                 (@RequestParam(required = false) Integer nuIdAlmacen, //
                                                                  @RequestParam(required = false) String numeroDocumento,
                                                                  @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                                  @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                                  @RequestParam(required = false) String sortField,
                                                                  @RequestParam(required = false, defaultValue = "DESC") String sortType)
        throws Exception {
        log.info("AlmacenController - listarAlmacenResponsable", nuIdAlmacen, numeroDocumento);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);
        try {
            Pageable<List<AlmacenResponsableEntity>> response = almacenService.ListarAlmacenResponsable(nuIdAlmacen, numeroDocumento, p);
            log.info("AlmacenController - listarAlmacenResponsable", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("AlmacenController - listarAlmacenResponsable", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @DeleteMapping("/eliminarResponsable")
    public ResponseEntity EliminarAlmacenResponsable(@RequestParam(required = false) Integer idAlmacenResponsable,
                                                     @RequestParam(required = false) Integer idUsuarioElimina){
        log.info("Ingreso al metodo eliminar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = almacenService.EliminarAlmacenResponsable(idAlmacenResponsable, idUsuarioElimina);
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
