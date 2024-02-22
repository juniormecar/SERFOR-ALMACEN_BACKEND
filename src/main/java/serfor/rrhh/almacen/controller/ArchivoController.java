package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import serfor.rrhh.almacen.entity.ArchivoEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;
import serfor.rrhh.almacen.service.ArchivoService;

import java.io.IOException;

@RestController
@RequestMapping("/api/serfor/archivo")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ArchivoController {

    @Autowired
    private ArchivoService arServ;
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(RecursoController.class);

    @PostMapping("/cargarArchivoGeneralCod")
    public ResponseEntity cargarArchivoGeneralCod(@RequestParam("file") MultipartFile file,
                                                  @RequestParam Integer IdUsuarioCreacion,
                                                  @RequestParam String TipoDocumento,
                                                  @RequestParam Integer idRecursoProducto,
                                                  @RequestParam String codigo) throws IOException {
        ResultClassEntity<Integer> response = new ResultClassEntity();

        try {
            response = arServ.registrarArchivoGeneralCod(file, IdUsuarioCreacion, idRecursoProducto, TipoDocumento,codigo);

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
}
