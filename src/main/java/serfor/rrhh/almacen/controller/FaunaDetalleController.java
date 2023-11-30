package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.service.FaunaDetalleService;

import java.util.List;

@RestController
@RequestMapping("/api/serfor/faunaDetalle")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class FaunaDetalleController {
    @Autowired
    private FaunaDetalleService faunaDetalleService;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(CubicacionController.class);

    @PostMapping("")
    public ResponseEntity RegistroFaunaDetalle (@RequestBody List<FaunaDetalleEntity> faunaDetalle){
        log.info("Ingreso al metodo registrar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = faunaDetalleService.RegistroFaunaDetalle(faunaDetalle);
            if(response.getSuccess()){
                log.info("RegistrarCubicación- exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("RegistrarCubicación ", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public Pageable<List<FaunaDetalleEntity>> ListarFaunaDetalle (@RequestParam(required = false) Integer nuIdRecursoProducto,
                                                              @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                              @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                              @RequestParam(required = false) String sortField,
                                                              @RequestParam(required = false, defaultValue = "DESC") String sortType)
            throws Exception {
        log.info("FaunaDetalleController - listar", nuIdRecursoProducto);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);
        try {
            Pageable<List<FaunaDetalleEntity>> response = faunaDetalleService.ListarFaunaDetalle(nuIdRecursoProducto, p);
            log.info("FaunaDetalleController - listar", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("FaunaDetalleController - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @DeleteMapping("")
    public ResponseEntity EliminarFaunaDetalle (@RequestParam(required = false) Integer nuIdFaunaDetalle,
                                              @RequestParam(required = false) Integer idUsuarioElimina){
        log.info("Ingreso al metodo eliminar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = faunaDetalleService.EliminarFaunaDetalle(nuIdFaunaDetalle, idUsuarioElimina);
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
