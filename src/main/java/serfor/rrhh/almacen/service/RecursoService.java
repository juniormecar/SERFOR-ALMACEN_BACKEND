package serfor.rrhh.almacen.service;

import serfor.rrhh.almacen.entity.*;

import java.util.Date;
import java.util.List;

public interface RecursoService {

    ResultClassEntity RegistrarRecurso(RecursoEntity recurso) throws Exception;

    ResultClassEntity ActualizarRecurso(RecursoEntity recurso) throws Exception;

    Pageable<List<RecursoEntity>> ListarRecurso(String numeroDocumento, String numeroActa,
                                                String txNroGuiaTransporteForestal,String tipoIngreso,Integer nuIdAlmacen,String documentoSesion, Page page) throws Exception;
    ResultClassEntity EliminarRecurso(Integer nuIdRecurso,Integer nuIdUsuarioElimina) throws Exception;

    Pageable<List<RecursoProductoEntity>> ListarRecursoEspecie(Integer idEspecie, Integer idRecurso,
                                                       Page page) throws Exception;
    Pageable<List<RecursoProductoEntity>> ListarRecursoVerProductos(Integer nuIdAlmacen, String guiaForestal, String numeroActa,
                                                                    String nombres, String nombreProducto, Integer idEspecie,
                                                                    String numeroDocumento, String tipoDetalle,
                                                                    String nombreCientifico, String nombreComun,
                                                                    String txNumeroATF,String txPuestoControl,String nombreAlmacen,
                                                                    String tipo,String unidadMedida,String tipoIngreso,String disponibilidadActa, Page page) throws Exception;

    ResultClassEntity EliminarRecursoEspecieForestal(RecursoProductoEntity recursoespecieforestal) throws Exception;

    Pageable<List<RecursoEntity>> ListarRecursoValidacion(String numeroActa,Page page) throws Exception;

    ResultClassEntity ActualizarDisponibilidadActa(RecursoEntity recurso) throws Exception;

    Pageable<List<RecursoProductoEntity>> BuscarActaProductos( String numeroActa,String tipo,Page page) throws Exception;

    ResultClassEntity actualizarRecursoEspecie(List<RecursoProductoEntity> list) throws Exception;

    Pageable<List<RecursoPersonaEntity>> ListarRecursoPersona( Integer nuIdRecurso,String tipoPersona,Page page) throws Exception;


}
