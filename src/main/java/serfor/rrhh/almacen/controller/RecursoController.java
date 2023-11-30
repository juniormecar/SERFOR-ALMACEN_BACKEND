package serfor.rrhh.almacen.controller;


import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.service.RecursoService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/serfor/recurso")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RecursoController {

    @Autowired
    private RecursoService recursoService;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(RecursoController.class);

    @PostMapping("")
    public ResponseEntity registrorRecurso(@RequestBody RecursoEntity recurso){
        log.info("Ingreso al metodo registrar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = recursoService.RegistrarRecurso(recurso);
            if(response.getSuccess()){
                log.info("registroRecurso - exitoso");
                return new ResponseEntity(response,HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("Recurso -recursoRegistrar", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizarRecursoEspecie")
    public ResponseEntity actualizarRecursoEspecie(@RequestBody List<RecursoProductoEntity> list){
        log.info("Ingreso al metodo actualizar recurso especie");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = recursoService.actualizarRecursoEspecie(list);
            if(response.getSuccess()){
                log.info("actualizarRecursoEspecie - exitoso");
                return new ResponseEntity(response,HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("Recurso - actualizarRecursoEspecie", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity actualizarRecurso(@RequestBody RecursoEntity recurso){
        log.info("Ingreso al metodo actualizar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = recursoService.ActualizarRecurso(recurso);
            if(response.getSuccess()){
                log.info("actualizarRecurso - exitoso");
                return new ResponseEntity(response,HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("actualizarRecurso -recursoRegistrar", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public Pageable<List<RecursoEntity>> ListarRecurso (@RequestParam(required = false) String numeroDocumento,
                                                        @RequestParam(required = false) String numeroActa,
                                                        @RequestParam(required = false) String txNroGuiaTransporteForestal,
                                                        @RequestParam(required = false) String tipoIngreso,
                                                        @RequestParam(required = false) Integer nuIdAlmacen,
                                                        @RequestParam(required = false) String documentoSesion,
                                                        @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                        @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                        @RequestParam(required = false) String sortField,
                                                        @RequestParam(required = false, defaultValue = "DESC") String sortType)
        throws Exception {
        log.info("RecursoController - listar", numeroDocumento, numeroActa, txNroGuiaTransporteForestal,tipoIngreso,nuIdAlmacen,documentoSesion);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);

        try{
            Pageable<List<RecursoEntity>> response = recursoService.ListarRecurso(numeroDocumento, numeroActa, txNroGuiaTransporteForestal,tipoIngreso,nuIdAlmacen,documentoSesion,p);
            log.info("RecursoController - listar", "Proceso realizado correctamente");
            return response;
        }catch (Exception e){
            log.error("RecursoController - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @DeleteMapping("")
    public ResponseEntity EliminarRecurso(@RequestParam(required = false) Integer nuIdRecurso,
                                          @RequestParam(required = false) Integer nuIdUsuarioElimina){
        log.info("Ingreso al metodo eliminar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = recursoService.EliminarRecurso(nuIdRecurso, nuIdUsuarioElimina);
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

    @GetMapping("/especies")
    public Pageable<List<RecursoProductoEntity>> ListarRecursoEspecie (@RequestParam(required = false) Integer idEspecie,
                                                                       @RequestParam(required = false) Integer idRecurso,
                                                                       @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                                       @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                                       @RequestParam(required = false) String sortField,
                                                                       @RequestParam(required = false, defaultValue = "DESC") String sortType)
            throws Exception {
        log.info("RecursoEspecieController - listar", idEspecie, idRecurso);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);

        try{
            Pageable<List<RecursoProductoEntity>> response = recursoService.ListarRecursoEspecie(idEspecie,idRecurso,p);
            log.info("RecursoEspecieController - listar", "Proceso realizado correctamente");
            return response;
        }catch (Exception e){
            log.error("RecursoEspecieController - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }
    @GetMapping("/verProductos")
    public Pageable<List<RecursoProductoEntity>> ListarRecursoVerProductos(@RequestParam(required = false) Integer nuIdAlmacen,
                                                                           @RequestParam(required = false) String guiaForestal,
                                                                           @RequestParam(required = false) String numeroActa,
                                                                           @RequestParam(required = false) String nombres,
                                                                           @RequestParam(required = false) String nombreProducto,
                                                                           @RequestParam(required = false) Integer idEspecie,
                                                                           @RequestParam(required = false) String numeroDocumento,
                                                                           @RequestParam(required = false) String tipoDetalle,
                                                                           @RequestParam(required = false) String nombreCientifico,
                                                                           @RequestParam(required = false) String nombreComun,
                                                                           @RequestParam(required = false) String txNumeroATF,
                                                                           @RequestParam(required = false) String txPuestoControl,
                                                                           @RequestParam(required = false) String nombreAlmacen,
                                                                           @RequestParam(required = false) String tipo,
                                                                           @RequestParam(required = false) String unidadMedida,
                                                                           @RequestParam(required = false) String tipoIngreso,
                                                                           @RequestParam(required = false) String disponibilidadActa,
                                                                           @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                                           @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                                           @RequestParam(required = false) String sortField,
                                                                           @RequestParam(required = false, defaultValue = "DESC") String sortType)
        throws Exception {
        log.info("RecursoController - listar", nuIdAlmacen, guiaForestal, numeroActa, nombres, nombreProducto, idEspecie, numeroDocumento);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);

        try{
            Pageable<List<RecursoProductoEntity>> response = recursoService.ListarRecursoVerProductos(nuIdAlmacen, guiaForestal, numeroActa, nombres, nombreProducto, idEspecie,numeroDocumento,
                                                                                                      tipoDetalle,nombreCientifico,nombreComun,txNumeroATF,txPuestoControl,
                                                                                                       nombreAlmacen,tipo,unidadMedida,tipoIngreso,disponibilidadActa, p);
            log.info("RecursoController - listar", "Proceso realizado correctamente");
            return response;
        }catch (Exception e){
            log.error("RecursoController - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @DeleteMapping("/eliminarRecursoEspecieForestal")
    public ResponseEntity EliminarRecursoEspecieForestal (@RequestParam Integer nuIdRecursoProducto,
                                                          @RequestParam Integer nuIdUsuarioElimina){
        log.info("Ingreso al metodo EliminarRecursoEspecieForestal");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            RecursoProductoEntity recursoespecieforestal = new RecursoProductoEntity();
            recursoespecieforestal.setNuIdRecursoProducto(nuIdRecursoProducto);
            recursoespecieforestal.setNuIdUsuarioElimina(nuIdUsuarioElimina);
            response = recursoService.EliminarRecursoEspecieForestal(recursoespecieforestal);
            if(response.getSuccess()){
                log.info("EliminarRecursoEspecieForestal - exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("EliminarRecursoEspecieForestal", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        @GetMapping("/validacion")
    public Pageable<List<RecursoEntity>> ListarRecurso (
            @RequestParam(required = false) String numeroActa,
            @RequestParam(required = false, defaultValue = "1") Long pageNumber,
            @RequestParam(required = false, defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false, defaultValue = "DESC")  String sortType) throws Exception {
        log.info("RecursoEspecieController - listar", numeroActa);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);

        try{
            Pageable<List<RecursoEntity>> response = recursoService.ListarRecursoValidacion(numeroActa,p);
            log.info("RecursoEspecieController - listar", "Proceso realizado correctamente");
            return response;
        }catch (Exception e){
            log.error("RecursoEspecieController - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }
    @PutMapping("/ActualizarDisponibilidadActa")
    public ResponseEntity ActualizarDisponibilidadActa(@RequestBody RecursoEntity recurso){
        log.info("Ingreso al metodo actualizar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = recursoService.ActualizarDisponibilidadActa(recurso);
            if(response.getSuccess()){
                log.info("ActualizarFlag - exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("ActualizarDisponibilidadActa", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/BuscarActaProductos")
    public Pageable<List<RecursoProductoEntity>> BuscarActaProductos (@RequestParam(required = false) String numeroActa,String tipo,
                                                        @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                        @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                        @RequestParam(required = false) String sortField,
                                                        @RequestParam(required = false, defaultValue = "DESC") String sortType)
            throws Exception {
        log.info("RecursoController - listar",  numeroActa);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);

        try{
            Pageable<List<RecursoProductoEntity>> response = recursoService.BuscarActaProductos(numeroActa,tipo,p);
            log.info("RecursoController - listar", "Proceso realizado correctamente");
            return response;
        }catch (Exception e){
            log.error("RecursoController - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @GetMapping("/recursopersona")
    public Pageable<List<RecursoPersonaEntity>> ListarRecursoPersona (@RequestParam(required = false) Integer nuIdRecurso,
                                                                      @RequestParam(required = false) String tipoPersona,
                                                                      @RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                                      @RequestParam(required = false, defaultValue = "10") Long pageSize,
                                                                      @RequestParam(required = false) String sortField,
                                                                      @RequestParam(required = false, defaultValue = "DESC") String sortType)
        throws Exception {
        log.info("RecursoController - listar",  nuIdRecurso,tipoPersona);
        Page p = new Page(pageNumber, pageSize, sortField, sortType);

        try{
            Pageable<List<RecursoPersonaEntity>> response = recursoService.ListarRecursoPersona(nuIdRecurso,tipoPersona,p);
            log.info("RecursoController - listar", "Proceso realizado correctamente");
            return response;
        }catch (Exception e){
            log.error("RecursoController - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }
}
