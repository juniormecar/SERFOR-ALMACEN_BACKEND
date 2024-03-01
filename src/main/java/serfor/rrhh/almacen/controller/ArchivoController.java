package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import serfor.rrhh.almacen.entity.ArchivoEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;
import serfor.rrhh.almacen.repository.util.Constants;
import serfor.rrhh.almacen.service.ArchivoService;

import java.io.IOException;

@RestController
@RequestMapping("/api/serfor/archivo")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ArchivoController {

    @Autowired
    private ArchivoService arServ;
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(RecursoController.class);

    @PostMapping("/cargarArchivoGeneralCodRecurso")
    public ResponseEntity cargarArchivoGeneralCod(@RequestParam("file") MultipartFile file,
                                                  @RequestParam Integer IdUsuarioCreacion,
                                                  @RequestParam String TipoDocumento,
                                                  @RequestParam(required = false) Integer idRecursoProducto,
                                                  @RequestParam(required = false) Integer idRecurso,
                                                  @RequestParam String codigo) throws IOException {
        ResultClassEntity<Integer> response = new ResultClassEntity();

        try {
            response = arServ.registrarArchivoGeneralCodRecurso(file, IdUsuarioCreacion,idRecurso, idRecursoProducto, TipoDocumento,codigo);

            if(response.getSuccess()){
                log.info("ArchivoController - CargarArchivoGeneral", "Proceso realizado correctamente");
                return new org.springframework.http.ResponseEntity(response, HttpStatus.OK);
            } else {
                return new org.springframework.http.ResponseEntity(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Se produjo un error al cargar archivo en Azure Container");
            log.error("ArchivoController - CargarArchivoGeneral", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/DescargarArchivoGeneral")
    public ResponseEntity  DescargarArchivoGeneral(@RequestBody ArchivoEntity param){
        log.info("ArchivoController - DescargarArchivoGeneral", param.toString());
        ResultClassEntity result = new ResultClassEntity();
        try {
            result = arServ.DescargarArchivoGeneral(param);
            if (result.getSuccess()) {
                log.info("ArchivoController - DescargarArchivoGeneral", "Proceso realizado correctamente");
                return new org.springframework.http.ResponseEntity(result, HttpStatus.OK);
            } else {
                return new org.springframework.http.ResponseEntity(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("ArchivoController - DescargarArchivoGeneral", "Ocurrió un error :" + e.getMessage());
            result.setInnerException (e.getMessage());
            return new org.springframework.http.ResponseEntity(result, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping(path = "/eliminarArchivo")
    public org.springframework.http.ResponseEntity<ResultClassEntity<Integer>> eliminarArchivo(
            @RequestBody ArchivoEntity archivo) {
        log.info("ArchivoController - eliminarArchivo", archivo.getIdArchivo(), archivo.getIdUsuarioElimina());
        ResultClassEntity<Integer> response = new ResultClassEntity<>();
        try {
            response = arServ.EliminarArchivoGeneral( archivo.getIdArchivo(), archivo.getIdUsuarioElimina());
            if (response.getSuccess()) {
                log.info("ArchivoController - eliminarArchivo", "Proceso realizado correctamente");
                return new org.springframework.http.ResponseEntity(response, HttpStatus.OK);
            } else {
                return new org.springframework.http.ResponseEntity(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("ArchivoController -eliminarArchivo", "Ocurrió un error :" + e.getMessage());
            response.setError(Constants.MESSAGE_ERROR_500, e);
            return new org.springframework.http.ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cargarArchivoGeneral")
    public ResponseEntity cargarArchivoGeneral(@RequestParam("file") MultipartFile file,
                                                  @RequestParam Integer IdUsuarioCreacion,
                                                  @RequestParam String TipoDocumento,
                                                  @RequestParam String codigo) throws IOException {
        ResultClassEntity<Integer> response = new ResultClassEntity();

        try {
            response = arServ.registrarArchivoGeneral(file, IdUsuarioCreacion, TipoDocumento,codigo);

            if(response.getSuccess()){
                log.info("ArchivoController - CargarArchivoGeneral", "Proceso realizado correctamente");
                return new org.springframework.http.ResponseEntity(response, HttpStatus.OK);
            } else {
                return new org.springframework.http.ResponseEntity(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Se produjo un error al cargar archivo en Azure Container");
            log.error("ArchivoController - CargarArchivoGeneral", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
    //newMetodos
    @PostMapping("/cargarMultipleArchivoGeneral")
    public ResponseEntity cargarMultipleArchivoGeneral(@RequestParam("file") MultipartFile file,
                                                       @RequestParam Integer IdUsuarioCreacion,
                                                       @RequestParam String TipoDocumento,
                                                       @RequestParam(required = false) Integer nuIdArchivo,
                                                       @RequestParam String codigo) throws IOException {
        ResultClassEntity<Integer> response = new ResultClassEntity();

        try {
            response = arServ.registrarMultipleArchivoGeneral(file, IdUsuarioCreacion, TipoDocumento,codigo, nuIdArchivo);

            if(response.getSuccess()){
                log.info("ArchivoController - CargarArchivoGeneral", "Proceso realizado correctamente");
                return new org.springframework.http.ResponseEntity(response, HttpStatus.OK);
            } else {
                return new org.springframework.http.ResponseEntity(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Se produjo un error al cargar archivo en Azure Container");
            log.error("ArchivoController - CargarArchivoGeneral", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/listarMultiplesArchivosGeneral")
    public ResponseEntity  ListarMultiplesArchivosGeneral(@RequestParam Integer nuIdArchivo,
                                                          @RequestParam(required = false) Integer nuIdArchivoDet){
        log.info("ArchivoController - listarMultiplesArchivosGeneral");
        ResultClassEntity result = new ResultClassEntity();
        try {
            result = arServ.ListarMultiplesArchivosGeneral(nuIdArchivo, nuIdArchivoDet);
            if (result.getSuccess()) {
                log.info("ArchivoController - DescargarArchivoGeneral", "Proceso realizado correctamente");
                return new org.springframework.http.ResponseEntity(result, HttpStatus.OK);
            } else {
                return new org.springframework.http.ResponseEntity(result, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("ArchivoController - DescargarArchivoGeneral", "Ocurrió un error :" + e.getMessage());
            result.setInnerException (e.getMessage());
            return new org.springframework.http.ResponseEntity(result, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping(path = "/eliminarMultiplesArchivos")
    public org.springframework.http.ResponseEntity<ResultClassEntity<Integer>> EliminarMultiplesArchivos(
            @RequestBody ArchivoEntity archivo) {
        log.info("ArchivoController - eliminarArchivo", archivo.getIdArchivoDetalle(), archivo.getIdUsuarioElimina());
        ResultClassEntity<Integer> response = new ResultClassEntity<>();
        try {
            response = arServ.EliminarMultiplesArchivos( archivo.getIdArchivoDetalle(), archivo.getIdUsuarioElimina());
            if (response.getSuccess()) {
                log.info("ArchivoController - eliminarMultiplesArchivos", "Proceso realizado correctamente");
                return new org.springframework.http.ResponseEntity(response, HttpStatus.OK);
            } else {
                return new org.springframework.http.ResponseEntity(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("ArchivoController -eliminarMultiplesArchivos", "Ocurrió un error :" + e.getMessage());
            response.setError(Constants.MESSAGE_ERROR_500, e);
            return new org.springframework.http.ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
