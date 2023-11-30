package serfor.rrhh.almacen.service;

import serfor.rrhh.almacen.entity.EspecieFaunaEntity;
import serfor.rrhh.almacen.entity.EspecieForestalEntity;
import serfor.rrhh.almacen.entity.*;

import java.util.List;

public interface CoreCentralService {
    Pageable<List<EspecieFaunaEntity>> listaPorFiltroEspecieFauna( String nombreEspecie, Page page) throws Exception;

    Pageable<List<EspecieForestalEntity>> listaPorFiltroEspecieForestal(Integer idEspecie, String nombreComun,
                                                              String nombreCientifico , String autor,
                                                              String familia, Page page) throws Exception;
}
