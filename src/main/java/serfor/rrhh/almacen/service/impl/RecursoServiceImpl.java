package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.RecursoRepository;
import serfor.rrhh.almacen.service.RecursoService;

import java.util.Date;
import java.util.List;

@Service
public class RecursoServiceImpl implements RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;

    @Override
    public ResultClassEntity RegistrarRecurso(RecursoEntity recurso) throws Exception {
        return recursoRepository.RegistrarRecurso(recurso);
    }

    @Override
    public ResultClassEntity ActualizarRecurso(RecursoEntity recurso) throws Exception {
        return recursoRepository.ActualizarRecurso(recurso);
    }

    @Override
    public Pageable<List<RecursoEntity>> ListarRecurso(String numeroDocumento, String numeroActa,
                                                       String txNroGuiaTransporteForestal,String tipoIngreso,Integer nuIdAlmacen, String documentoSesion, Page page)
        throws Exception {
                return recursoRepository.ListarRecurso(numeroDocumento,numeroActa,txNroGuiaTransporteForestal,tipoIngreso,nuIdAlmacen,documentoSesion,page);
    }

    @Override
    public ResultClassEntity EliminarRecurso(Integer nuIdRecurso,Integer nuIdUsuarioElimina) throws Exception {
        return recursoRepository.EliminarRecurso(nuIdRecurso, nuIdUsuarioElimina);
    }

    @Override
    public Pageable<List<RecursoProductoEntity>> ListarRecursoEspecie(Integer idEspecie, Integer idRecurso,
                                                              Page page) throws Exception {
        return recursoRepository.ListarRecursoEspecie(idEspecie,idRecurso,page);
    }

    @Override
    public Pageable<List<RecursoProductoEntity>> ListarRecursoVerProductos(Integer nuIdAlmacen, String guiaForestal,
                                                                           String numeroActa, String nombres,
                                                                           String nombreProducto, Integer idEspecie,
                                                                           String numeroDocumento, String tipoDetalle,
                                                                           String nombreCientifico, String nombreComun,
                                                                           String txNumeroATF,String txPuestoControl,String nombreAlmacen,
                                                                           String tipo,String unidadMedida,String tipoIngreso,String disponibilidadActa, Page page)
            throws Exception {
        return recursoRepository.ListarRecursoVerProductos(nuIdAlmacen, guiaForestal , numeroActa, nombres, nombreProducto, idEspecie, numeroDocumento, tipoDetalle,
                                                           nombreCientifico,nombreComun,txNumeroATF,txPuestoControl,nombreAlmacen,
                                                            tipo,unidadMedida,tipoIngreso,disponibilidadActa ,page);
    }

    @Override
    public ResultClassEntity EliminarRecursoEspecieForestal(RecursoProductoEntity recursoespecieforestal) throws Exception {
        return recursoRepository.EliminarRecursoEspecieForestal(recursoespecieforestal);
    }

    @Override
    public Pageable<List<RecursoEntity>> ListarRecursoValidacion(String numeroActa,Page page)
            throws Exception {
        return recursoRepository.ListarRecursoValidacion(numeroActa,page);
    }

    @Override
    public ResultClassEntity ActualizarDisponibilidadActa(RecursoEntity recurso) throws Exception {
        return recursoRepository.ActualizarDisponibilidadActa(recurso);
    }

    @Override
    public Pageable<List<RecursoProductoEntity>> BuscarActaProductos(String numeroActa,String tipo,Page page)
            throws Exception {
        return recursoRepository.BuscarActaProductos(numeroActa,tipo,page);
    }

    @Override
    public ResultClassEntity actualizarRecursoEspecie(List<RecursoProductoEntity> list) throws Exception {
        return recursoRepository.actualizarRecursoEspecie(list);
    }

    @Override
    public Pageable<List<RecursoPersonaEntity>> ListarRecursoPersona( Integer nuIdRecurso,String tipoPersona,Page page)
            throws Exception {
        return recursoRepository.ListarRecursoPersona(nuIdRecurso,tipoPersona,page);
    }

    @Override
    public ResultClassEntity<Integer> ActualizarRecursoArchivos(RecursoEntity archivo) {
        return recursoRepository.ActualizarRecursoArchivos(archivo);
    }
}
