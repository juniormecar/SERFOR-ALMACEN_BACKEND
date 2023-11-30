package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.service.ActaService;

import java.util.List;

@RestController
@RequestMapping("/api/serfor/acta")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ActaController {

    @Autowired
    private ActaService actaService;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ActaController.class);

    @PostMapping("")
    public ResponseEntity RegistroActa(@RequestBody ActaEntity acta){
        log.info("Ingreso al metodo registrar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = actaService.RegistroActa(acta);
            if(response.getSuccess()){
                log.info("RegistrarAlmacen - exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("RegistarActa -listar", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResultClassEntity<ActaEntity> ListarActa(@RequestParam Integer nuIdRecurso)
        throws Exception {
        log.info("ActaController - ListarActa", nuIdRecurso);
        try {
            ResultClassEntity<ActaEntity> response = actaService.ListarActa(nuIdRecurso);
            log.info("ActaController - ListarActa", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("ActaController -ListarActa", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @PutMapping("")
    public ResponseEntity ActualizarFlag(@RequestBody ActaEntity acta){
        log.info("Ingreso al metodo actualizar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = actaService.ActualizarFlag(acta);
            if(response.getSuccess()){
                log.info("ActualizarFlag - exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("ActualizarFlag", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/pdf/acta")
    public ResponseEntity<ResultArchivoEntity> consolidadoActa_PDF(@RequestParam(required = true) Integer idRecurso)
            throws Exception {
        ResultArchivoEntity result = new ResultArchivoEntity();
        log.info("ActaController - consolidadoActa_PDF", idRecurso);
        try {

            ByteArrayResource bytes = actaService.consolidadoActa_PDF(idRecurso);
            result.setArchivo(bytes.getByteArray());
            result.setNombeArchivo("consolidadoActa.pdf");
            result.setContenTypeArchivo("application/octet-stream");
            result.setSuccess(true);
            result.setMessage("Se generó el consolidado del Acta de los artículos.");
            if (!result.getSuccess()) {
                return new org.springframework.http.ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            } else {
                log.info("ActaController - consolidadoActa_PDF", "Proceso realizado correctamente");
                return new org.springframework.http.ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("ActaController - consolidadoActa_PDF", "Ocurrió un error :"+ e.getMessage());
            throw new Exception(e);
        }
    }
}
