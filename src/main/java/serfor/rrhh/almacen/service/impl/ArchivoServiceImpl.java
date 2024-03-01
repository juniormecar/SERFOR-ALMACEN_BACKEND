package serfor.rrhh.almacen.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import serfor.rrhh.almacen.entity.ArchivoEntity;
import serfor.rrhh.almacen.entity.ParametroEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;
import serfor.rrhh.almacen.repository.ArchivoRepository;
import serfor.rrhh.almacen.repository.util.FileServerConexion;
import serfor.rrhh.almacen.service.ArchivoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArchivoServiceImpl implements ArchivoService {


    @Autowired
    ArchivoRepository arRepo;

    @Autowired
    private FileServerConexion fileCn;

    @Value("${smb.file.server.path}")
    private String fileServerPath;
    private static final Logger log = LogManager.getLogger(ArchivoServiceImpl.class);

    @Override
    public ResultClassEntity<Integer> registrarArchivoGeneralCodRecurso(MultipartFile file, Integer IdUsuarioCreacion,Integer idRecurso, Integer idRecursoProducto,
                                                                 String TipoDocumento, String codigo) throws Exception {

        ResultClassEntity<Integer> res = new ResultClassEntity();

        String path = codigo;

        if(idRecurso == null && idRecursoProducto == null){
            log.error("ArchivoServiceImpl - RegistrarArchivoGeneral occurrio un error: Archivo no Cargado");
            res.setMessage("No envia recurso o recursoProducto a cargar");
            res.setSuccess(false);
            return res;
        }

        //TODO CARGAFILE
        //String nombreGenerado = fileCn.uploadFile(file, path);
        //String nombreGenerado = "CARGA PRUEBA";

        if (file !=null && !file.isEmpty()) {
            ArchivoEntity archivo = new ArchivoEntity();
            archivo.setIdUsuarioRegistro(IdUsuarioCreacion);
            archivo.setTipoDocumento(TipoDocumento);
            archivo.setEstado("A");
            archivo.setRuta(fileServerPath + path);
            archivo.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."),
                    file.getOriginalFilename().length()));
            archivo.setNombre(file.getOriginalFilename());
            //archivo.setNombreGenerado((!nombreGenerado.equals("") ? nombreGenerado : file.getOriginalFilename()));
            archivo.setIdRecursoProducto(idRecursoProducto);
            archivo.setIdRecurso(idRecurso);
            archivo.setFile(file.getBytes());
            archivo.setType(file.getContentType());
            archivo.setNombreGenerado(file.getOriginalFilename());

            log.info("ArchivoServiceImpl - RegistrarArchivoGeneral" + archivo.toString());
            return arRepo.registrarArchivoGeneralCodRecurso(archivo);

        } else {
            log.error("ArchivoServiceImpl - RegistrarArchivoGeneral occurrio un error: Archivo no Cargado");
            res.setMessage("Archivo no Cargado");
            res.setSuccess(false);
            return res;
        }
    }

    @Override
    public ResultClassEntity DescargarArchivoGeneral(ArchivoEntity param) {
        return arRepo.DescargarArchivoGeneral(param);
    }

    @Override
    public ResultClassEntity<Integer> EliminarArchivoGeneral(Integer idArchivo, Integer idUsuario) {
        return arRepo.EliminarArchivoGeneral(idArchivo, idUsuario);
    }

    @Override
    public ResultClassEntity<Integer> registrarArchivoGeneral(MultipartFile file, Integer IdUsuarioCreacion,
                                                                        String TipoDocumento, String codigo) throws Exception {

        ResultClassEntity<Integer> res = new ResultClassEntity();

        String path = codigo;

        //TODO CARGAFILE
        //String nombreGenerado = fileCn.uploadFile(file, path);
        //String nombreGenerado = "CARGA PRUEBA";

        if (file !=null && !file.isEmpty()) {
            ArchivoEntity archivo = new ArchivoEntity();
            archivo.setIdUsuarioRegistro(IdUsuarioCreacion);
            archivo.setTipoDocumento(TipoDocumento);
            archivo.setEstado("A");
            archivo.setRuta(fileServerPath + path);
            archivo.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."),
                    file.getOriginalFilename().length()));
            archivo.setNombre(file.getOriginalFilename());
            //archivo.setNombreGenerado((!nombreGenerado.equals("") ? nombreGenerado : file.getOriginalFilename()));
            archivo.setFile(file.getBytes());
            archivo.setType(file.getContentType());
            archivo.setNombreGenerado(file.getOriginalFilename());

            log.info("ArchivoServiceImpl - RegistrarArchivoGeneral" + archivo.toString());
            return arRepo.registrarArchivoGeneral(archivo);

        } else {
            log.error("ArchivoServiceImpl - RegistrarArchivoGeneral occurrio un error: Archivo no Cargado");
            res.setMessage("Archivo no Cargado");
            res.setSuccess(false);
            return res;
        }
    }
    @Override
    public ResultClassEntity<Integer> registrarMultipleArchivoGeneral(MultipartFile file, Integer IdUsuarioCreacion,
                                                              String TipoDocumento, String codigo, Integer nuIdArchivo) throws Exception {

        ResultClassEntity<Integer> res = new ResultClassEntity();

        String path = codigo;

        //TODO CARGAFILE
        //String nombreGenerado = fileCn.uploadFile(file, path);
        //String nombreGenerado = "CARGA PRUEBA";

        if (file !=null && !file.isEmpty()) {
            ArchivoEntity archivo = new ArchivoEntity();
            archivo.setIdUsuarioRegistro(IdUsuarioCreacion);
            archivo.setTipoDocumento(TipoDocumento);
            archivo.setEstado("A");
            archivo.setRuta(fileServerPath + path);
            archivo.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."),
                    file.getOriginalFilename().length()));
            archivo.setNombre(file.getOriginalFilename());
            //archivo.setNombreGenerado((!nombreGenerado.equals("") ? nombreGenerado : file.getOriginalFilename()));
            archivo.setFile(file.getBytes());
            archivo.setType(file.getContentType());
            archivo.setIdArchivo(nuIdArchivo);
            archivo.setNombreGenerado(file.getOriginalFilename());

            log.info("ArchivoServiceImpl - RegistrarArchivoGeneral" + archivo.toString());
            return arRepo.registrarMultipleArchivoGeneral(archivo);

        } else {
            log.error("ArchivoServiceImpl - RegistrarArchivoGeneral occurrio un error: Archivo no Cargado");
            res.setMessage("Archivo no Cargado");
            res.setSuccess(false);
            return res;
        }
    }

    @Override
    public ResultClassEntity ListarMultiplesArchivosGeneral(Integer nuIdArchivo, Integer nuIdArchivoDet) {
        return arRepo.ListarMultiplesArchivosGeneral(nuIdArchivo, nuIdArchivoDet);
    }

    @Override
    public ResultClassEntity<Integer> EliminarMultiplesArchivos(Integer nuIdArchivoDetalle, Integer idUsuario) {
        return arRepo.EliminarMultiplesArchivos(nuIdArchivoDetalle, idUsuario);
    }
}
