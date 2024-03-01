package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.ArchivoEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;

public interface ArchivoRepository {
    ResultClassEntity<Integer> registrarArchivoGeneralCodRecurso(ArchivoEntity request) throws Exception;
    ResultClassEntity DescargarArchivoGeneral(ArchivoEntity param) ;
    ResultClassEntity<Integer> EliminarArchivoGeneral(Integer idArchivo, Integer idUsuario);
    ResultClassEntity<Integer> registrarArchivoGeneral(ArchivoEntity request) throws Exception;
    ResultClassEntity<Integer> registrarMultipleArchivoGeneral(ArchivoEntity request);
    ResultClassEntity ListarMultiplesArchivosGeneral(Integer nuIdArchivo, Integer nuIdArchivoDet) ;
    ResultClassEntity<Integer> EliminarMultiplesArchivos(Integer nuIdArchivoDetalle, Integer idUsuario);

}
