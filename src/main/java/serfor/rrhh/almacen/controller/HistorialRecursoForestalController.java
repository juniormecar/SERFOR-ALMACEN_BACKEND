package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.service.HistorialRecursoForestalService;

import java.util.List;

@RestController
@RequestMapping("/api/serfor/historial")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class HistorialRecursoForestalController {
    @Autowired
    private HistorialRecursoForestalService historialrecursoforestalService;
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(HistorialRecursoForestalController.class);

    @PostMapping("")
    public ResponseEntity RegistroHistorialRecursoProducto(@RequestBody HistorialRecursoProductoEntity historialRecursoProducto){
        log.info("Ingreso al metodo registrar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = historialrecursoforestalService.RegistroHistorialRecursoProducto(historialRecursoProducto);
            if(response.getSuccess()){
                log.info("RegistrarHistorial - exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("RegistrarHistorial ", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public Pageable<List<HistorialRecursoProductoEntity>> ListarHistorialRecursoForestal(@RequestParam(required = false) Integer nuIdAlmacen,
                                                                           @RequestParam(required = false) String nombreEspecie,
                                                                           @RequestParam(required = false) String tipoProducto,
                                                                           @RequestParam(required = false) String tipoIngreso,
                                                                           @RequestParam(required = false) String disponible,
                                                                           @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                                           @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                                           @RequestParam(required = false) String sortField,
                                                                           @RequestParam(required = false, defaultValue = "DESC") String sortType)
            throws Exception {
        log.info("HistorialRecursoForestalController - listar", nuIdAlmacen, nombreEspecie, tipoProducto, tipoIngreso, disponible);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);

        try{
            Pageable<List<HistorialRecursoProductoEntity>> response = historialrecursoforestalService.ListarHistorialRecursoForestal(nuIdAlmacen,nombreEspecie, tipoProducto, tipoIngreso, disponible, p);
            log.info("HistorialRecursoForestalController - listar", "Proceso realizado correctamente");
            return response;
        }catch (Exception e){
            log.error("HistorialRecursoForestalController - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }
}

