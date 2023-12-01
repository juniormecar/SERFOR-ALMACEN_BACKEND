package serfor.rrhh.almacen.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.service.ParametroService;

import java.util.List;

@RestController
@RequestMapping("/api/serfor/parametro")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ParametroController {

    @Autowired
    private ParametroService parametroValorService;
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ParametroController.class);

    @GetMapping("")
    public List<ParametroEntity> listaParametro(@RequestParam(required = false) String prefijo)
        throws Exception {
        log.info("ParametroController - listaParametro", prefijo);
        try {
            List<ParametroEntity> response = parametroValorService.listaParametro(prefijo);
            log.info("ParametroController - listaParametro", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("ParametroController -listaParametro", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @PostMapping("")
    public ResultClassEntity saveParametro(@RequestBody List<TipoparametroEntity> lstParametros)
            throws Exception {
        //log.info("ParametroController - saveParametro", lstParametros);
        try {
            ResultClassEntity response = parametroValorService.saveParametros(lstParametros);
            log.info("ParametroController - saveParametro", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("ParametroController -saveParametro", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

    @DeleteMapping("/eliminarParametro")
    public ResponseEntity EliminarParametro(@RequestParam(required = false) Integer idParametro,
                                            @RequestParam(required = false) Integer idUsuarioElimina){
        log.info("Ingreso al metodo eliminar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = parametroValorService.EliminarParametro(idParametro, idUsuarioElimina);
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
    @PostMapping("/registrarParametro")
    public ResponseEntity registrarParametro(@RequestBody ParametroEntity parametro){
        log.info("Ingreso al metodo registrar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = parametroValorService.registrarParametro(parametro);
            if(response.getSuccess()){
                log.info("registrarParametro - exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("Parametro -registrarParametro", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/registrartipoParametro")
    public ResponseEntity registrarTipoParametro(@RequestBody TipoparametroEntity tipoparametro){
        log.info("Ingreso al metodo registrar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = parametroValorService.registrarTipoParametro(tipoparametro);
            if(response.getSuccess()){
                log.info("registrarTipoParametro - exitoso");
                return new ResponseEntity(response, HttpStatus.OK);
            }else {
                return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error("TipoParametro -registrarTipoParametro", "Ocurrió un error :" + e.getMessage());
            return new ResponseEntity(null,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminarTipoParametro")
    public ResponseEntity EliminarTipoParametro(@RequestParam(required = false) Integer idTipoParametro,
                                                @RequestParam(required = false) Integer idUsuarioElimina){
        log.info("Ingreso al metodo eliminar");
        ResponseEntity result = null;
        ResultClassEntity response;

        try{
            response = parametroValorService.EliminarTipoParametro(idTipoParametro, idUsuarioElimina);
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

    @GetMapping("/listarTipoParametro")
    public Pageable<List<TipoparametroEntity>> listarTipoParametro (@RequestParam(required = false, defaultValue = "1") Long pageNumber,
                                                                    @RequestParam(required = false, defaultValue = "100") Long pageSize,
                                                                    @RequestParam(required = false) String sortField,
                                                                    @RequestParam(required = false, defaultValue = "ASC") String sortType)
        throws Exception {
        log.info("ParametroController - listarTipoParametro");
        Page p = new Page(pageNumber, pageSize, sortField, sortType);
        try {
            Pageable<List<TipoparametroEntity>> response = parametroValorService.listarTipoParametro(p);
            log.info("TipoParametro - listar", "Proceso realizado correctamente");
            return response;
        } catch (Exception e) {
            log.error("TipoParametro - listar", "Ocurrió un error :" + e.getMessage());
            throw new Exception(e);
        }
    }

}
