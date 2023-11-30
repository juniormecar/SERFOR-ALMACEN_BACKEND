package serfor.rrhh.almacen.controller;


import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.CubicacionEntity;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.ResultClassEntity;
import serfor.rrhh.almacen.service.CubicacionService;

import java.util.List;

@RestController
@RequestMapping("/api/serfor/cubicacion")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CubicacionController {

    @Autowired
    private CubicacionService cubicacionService;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(CubicacionController.class);

    @PostMapping("")
    public ResponseEntity RegistroCubicacion (@RequestBody List<CubicacionEntity> cubicacion){
        log.info("Ingreso al metodo registrar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = cubicacionService.RegistroCubicacion(cubicacion);
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
    public Pageable<List<CubicacionEntity>> ListarCubicacion (@RequestParam(required = false) Integer nuIdRecursoProducto,
                                                              @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                              @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                              @RequestParam(required = false) String sortField,
                                                              @RequestParam(required = false, defaultValue = "DESC") String sortType)
            throws Exception {
        log.info("CubicaciónController - listar", nuIdRecursoProducto);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);
        try {
            Pageable<List<CubicacionEntity>> response = cubicacionService.ListarCubicacion(nuIdRecursoProducto, p);
            log.info("CubicaciónController - listar", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("CubicaciónController - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @DeleteMapping("")
    public ResponseEntity EliminarCubicacion (@RequestParam(required = false) Integer idRecurProCubicacion,
                                              @RequestParam(required = false) Integer idUsuarioElimina){
        log.info("Ingreso al metodo eliminar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = cubicacionService.EliminarCubicacion(idRecurProCubicacion, idUsuarioElimina);
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
