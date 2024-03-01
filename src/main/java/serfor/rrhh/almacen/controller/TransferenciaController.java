package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.util.Constants;
import serfor.rrhh.almacen.service.TransferenciaService;

import java.util.List;

@RestController
@RequestMapping("/api/serfor/transferencia")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(TransferenciaController.class);

    @PostMapping("")
    public ResponseEntity registrorTransferencia(@RequestBody List<TransferenciaEntity> transferencia){
        log.info("Ingreso al metodo registrar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = transferenciaService.RegistrarTransferencia(transferencia);
            if(response.getSuccess()){
                log.info("registroTransferencia - exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("Recurso -registroTransferencia", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/Retorno")
    public ResponseEntity registrarRetorno(@RequestBody List<ReporteEntity> reporte){
        log.info("Ingreso al metodo registrar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = transferenciaService.RegistrarRetorno(reporte);
            if(response.getSuccess()){
                log.info("registroRetorno - exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("Recurso -registroRetorno", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public Pageable<List<TransferenciaEntity>> ListarTransferencia (@RequestParam(required = false) Integer nuIdAlmacen,
                                                                    @RequestParam(required = false) String documento,
                                                                    @RequestParam(required = false) String tipoTransferencia,
                                                                    @RequestParam(required = false) Integer nuIdTransferencia,
                                                                    @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                                    @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                                    @RequestParam(required = false, defaultValue = "idTransferencia") String sortField,
                                                                    @RequestParam(required = false, defaultValue = "DESC") String sortType)
            throws Exception {
        log.info("AlmacenController - listar", nuIdAlmacen,documento,tipoTransferencia);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);
        try {
            Pageable<List<TransferenciaEntity>> response = transferenciaService.ListarTransferencia(nuIdAlmacen,documento,tipoTransferencia,nuIdTransferencia, p);
            log.info("AlmacenController - listar", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("AlmacenController - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @GetMapping("/ReportesAvanzados")
    public Pageable<List<TransferenciaEntity>> ListarReportesAvanzados (@RequestParam(required = false) Integer nuIdAlmacen,
                                                                    @RequestParam(required = false) String tipoTransferencia,
                                                                        @RequestParam(required = false) Integer nuIdTransferencia,
                                                                        @RequestParam(required = false) String tipoTransferenciaDetalle,
                                                                        @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                                        @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                                        @RequestParam(required = false) String sortField,
                                                                        @RequestParam(required = false, defaultValue = "DESC") String sortType)
            throws Exception {
        log.info("TransferenciaController - listar", nuIdAlmacen,tipoTransferencia,nuIdTransferencia,tipoTransferenciaDetalle);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);
        try {
            Pageable<List<TransferenciaEntity>> response = transferenciaService.ListarReportesAvanzados(nuIdAlmacen,tipoTransferencia,nuIdTransferencia,tipoTransferenciaDetalle, p);
            log.info("TransferenciaController - listar", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("TransferenciaController - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @PostMapping(path = "/actualizarTransferenciaArchivo")
    public org.springframework.http.ResponseEntity<ResultClassEntity<Integer>> ActualizarTransferenciaArchivo(
            @RequestBody TransferenciaEntity archivo) {
        log.info("ArchivoController - eliminarArchivo", archivo.getNuIdArchivo(), archivo.getNuIdUsuarioElimina());
        ResultClassEntity<Integer> response = new ResultClassEntity<>();
        try {
            response = transferenciaService.ActualizarTransferenciaArchivo(archivo.getNuIdTransferencia(), archivo.getNuIdArchivo(), archivo.getNuIdUsuarioModificacion(), archivo.getTypeAccion());
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

    @PutMapping("")
    public ResponseEntity actualizarTransferencia(@RequestBody List<TransferenciaEntity> transferencia){
        log.info("Ingreso al metodo actualizar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = transferenciaService.actualizarTransferencia(transferencia);
            if(response.getSuccess()){
                log.info("actualizarTransferencia - exitoso");
                return new ResponseEntity(response,HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("almacen - actualizarTransferencia", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
