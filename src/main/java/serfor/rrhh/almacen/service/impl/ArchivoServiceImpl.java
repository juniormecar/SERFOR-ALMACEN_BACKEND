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
    public ResultClassEntity<Integer> registrarArchivoGeneralCod(MultipartFile file, Integer IdUsuarioCreacion, Integer idRecursoProducto,
                                                                 String TipoDocumento, String codigo) throws Exception {

        ResultClassEntity<Integer> res = new ResultClassEntity();

        //ParametroEntity e = new ParametroEntity();
        //e.setCodigo(codigo);
        String path = codigo;

        String nombreGenerado = fileCn.uploadFile(file, path);
        //String nombreGenerado = "CARGA PRUEBA";

        if (!nombreGenerado.equals("") && path != null && !path.equals("")) {
            ArchivoEntity archivo = new ArchivoEntity();
            archivo.setIdUsuarioRegistro(IdUsuarioCreacion);
            archivo.setTipoDocumento(TipoDocumento);
            archivo.setEstado("A");
            archivo.setRuta(fileServerPath + path);
            archivo.setExtension(file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."),
                    file.getOriginalFilename().length()));
            archivo.setNombre(file.getOriginalFilename());
            archivo.setNombreGenerado((!nombreGenerado.equals("") ? nombreGenerado : file.getOriginalFilename()));
            archivo.setIdRecursoProducto(idRecursoProducto);

            log.info("ArchivoServiceImpl - RegistrarArchivoGeneral" + archivo.toString());
            return arRepo.RegistrarArchivoGeneral(archivo);

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
}
