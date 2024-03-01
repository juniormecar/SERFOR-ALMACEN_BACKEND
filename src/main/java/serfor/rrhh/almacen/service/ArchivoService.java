package serfor.rrhh.almacen.service;

import org.springframework.web.multipart.MultipartFile;
import serfor.rrhh.almacen.entity.ArchivoEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;

public interface ArchivoService {
    ResultClassEntity<Integer> registrarArchivoGeneralCodRecurso(MultipartFile file, Integer IdUsuarioCreacion,Integer idRecurso, Integer idRecursoProducto, String TipoDocumento, String codigo) throws Exception;
    ResultClassEntity DescargarArchivoGeneral(ArchivoEntity param);
    ResultClassEntity<Integer> EliminarArchivoGeneral(Integer idArchivo, Integer idUsuario);
    ResultClassEntity<Integer> registrarArchivoGeneral(MultipartFile file, Integer IdUsuarioCreacion, String TipoDocumento, String codigo) throws Exception;
    ResultClassEntity<Integer> registrarMultipleArchivoGeneral(MultipartFile file, Integer IdUsuarioCreacion, String TipoDocumento, String codigo, Integer nuIdArchivo) throws Exception;
    ResultClassEntity ListarMultiplesArchivosGeneral(Integer nuIdAarchivo, Integer nuIdArchivoDet);
    ResultClassEntity<Integer> EliminarMultiplesArchivos(Integer nuIdArchivoDetalle, Integer idUsuario);

}
