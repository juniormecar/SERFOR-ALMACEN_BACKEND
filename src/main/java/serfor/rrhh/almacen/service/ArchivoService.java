package serfor.rrhh.almacen.service;

import org.springframework.web.multipart.MultipartFile;
import serfor.rrhh.almacen.entity.ArchivoEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;

public interface ArchivoService {
    ResultClassEntity<Integer> registrarArchivoGeneralCod(MultipartFile file, Integer IdUsuarioCreacion, Integer idRecursoProducto, String TipoDocumento, String codigo) throws Exception;
    ResultClassEntity DescargarArchivoGeneral(ArchivoEntity param);

}
