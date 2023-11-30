package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.EspecieFaunaEntity;
import serfor.rrhh.almacen.entity.EspecieForestalEntity;
import serfor.rrhh.almacen.entity.*;
import java.util.List;

public interface CoreCentralRepository {
    Pageable<List<EspecieFaunaEntity>> listaPorFiltroEspecieFauna(String nombreEspecie, Page page) throws Exception;

    Pageable<List<EspecieForestalEntity>> listaPorFiltroEspecieForestal(Integer idEspecie, String nombreComun,
                                                              String nombreCientifico , String autor,
                                                              String familia,Page p) throws Exception;
}
