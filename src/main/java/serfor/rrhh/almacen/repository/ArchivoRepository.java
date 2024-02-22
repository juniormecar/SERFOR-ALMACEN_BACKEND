package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.ArchivoEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;

public interface ArchivoRepository {
    ResultClassEntity<Integer> RegistrarArchivoGeneral(ArchivoEntity request) throws Exception;
    ResultClassEntity DescargarArchivoGeneral(ArchivoEntity param) ;

}
