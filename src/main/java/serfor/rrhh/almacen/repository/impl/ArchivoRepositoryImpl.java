package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.ArchivoEntity;
import serfor.rrhh.almacen.entity.ResultArchivoEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;
import serfor.rrhh.almacen.entity.SpUtil;
import serfor.rrhh.almacen.repository.ArchivoRepository;
import serfor.rrhh.almacen.repository.util.FileServerConexion;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArchivoRepositoryImpl extends JdbcDaoSupport implements ArchivoRepository {

    @Autowired
    @Qualifier("dataSourceAlmacen")
    DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    private static final String SEPARADOR_ARCHIVO = ".";
    @Autowired
    private FileServerConexion fileServerConexion;
    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ActaRepositoryImpl.class);
    @Override
    public ResultClassEntity<Integer> registrarArchivoGeneralCodRecurso(ArchivoEntity request) throws Exception {

        ResultClassEntity<Integer> result = new ResultClassEntity<>();

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_ArchivoGeneral_Registrar");
            sp.registerStoredProcedureParameter("ruta", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nombre", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nombreGenerado", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("extension", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipoDocumento", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("idUsuarioRegistro", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("bitFile", byte[].class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("idArchivo", Integer.class, ParameterMode.OUT);
            SpUtil.enableNullParams(sp);
            sp.setParameter("ruta", request.getRuta());
            sp.setParameter("nombre", request.getNombre());
            sp.setParameter("nombreGenerado", request.getNombreGenerado());
            sp.setParameter("extension", request.getExtension());
            sp.setParameter("tipoDocumento", request.getTipoDocumento());
            sp.setParameter("idUsuarioRegistro", request.getIdUsuarioRegistro());
            sp.setParameter("bitFile", request.getFile());
            sp.execute();

            Integer idArchivo = (Integer) sp.getOutputParameterValue("idArchivo");

            if(idArchivo > 0){
                StoredProcedureQuery spa = em.createStoredProcedureQuery("almacen.pa_Actualizar_Recurso_Archivo");
                spa.registerStoredProcedureParameter("nuIdRecurso", Integer.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("nuIdRecursoProducto", Integer.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("nuIdArchivo", Integer.class, ParameterMode.IN);
                SpUtil.enableNullParams(spa);
                spa.setParameter("nuIdRecurso", request.getIdRecurso());
                spa.setParameter("nuIdRecursoProducto", request.getIdRecursoProducto());
                spa.setParameter("nuIdArchivo", idArchivo);
                spa.execute();
            }

            result.setCodigo(idArchivo);
            result.setData(idArchivo);
            result.setMessage("Información Registrada");
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            log.error("ArchivoRepositoryImpl - registrarArchivoGeneral"+"Ocurrió un error :" + e.getMessage());
            result.setCodigo(0);
            result.setSuccess(false);
            result.setMessage("Ocurrió un error.");
            return result;
        }
    }
    @Override
    public ResultClassEntity DescargarArchivoGeneral(ArchivoEntity param) {
        ResultClassEntity result = new ResultClassEntity();
        try {
            StoredProcedureQuery processStored = em.createStoredProcedureQuery("almacen.pa_Archivo_Listar");
            processStored.registerStoredProcedureParameter("nombreGenerado", String.class, ParameterMode.IN);
            processStored.registerStoredProcedureParameter("idArchivo", Integer.class, ParameterMode.IN);
            processStored.registerStoredProcedureParameter("tipoDocumento", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(processStored);
            processStored.setParameter("nombreGenerado", param.getNombreGenerado());
            processStored.setParameter("idArchivo", param.getIdArchivo());
            processStored.setParameter("tipoDocumento", param.getTipoDocumento());
            processStored.execute();

            List<Object[]> spResult_ = processStored.getResultList();
            ResultArchivoEntity resultArchivo = new ResultArchivoEntity();
            for (Object[] row_ : spResult_) {
                String ruta = ((String) row_[1]);
                String nombreArchivoGenerado = ((String) row_[4]);
                String nombreArchivo = ((String) row_[2]);
                byte[] byteFile = ((byte[]) row_[6]);
                //byte[] byteFile = fileServerConexion.loadFileAsResource(nombreArchivoGenerado,ruta);
                resultArchivo.setArchivo(byteFile);
                resultArchivo.setNombeArchivo(nombreArchivo);
                resultArchivo.setContenTypeArchivo("application/octet-stream");
                resultArchivo.setSuccess(true);
                resultArchivo.setMessage("Se descargo el archivo del file server  con éxito.");
            }

            result.setSuccess(true);
            result.setMessage("Se descargo el archivo del file server  con éxito.");
            result.setData(resultArchivo);
            return result;
        } catch (Exception e) {
            log.error("ArchivoRepositoryImpl - descargarArchivoGeneral"+"Ocurrió un error :" + e.getMessage());
            result.setSuccess(false);
            result.setMessage("Ocurrió un error.");
            result.setData(null);
            return result;
        }
    }

    @Override
    public ResultClassEntity<Integer> EliminarArchivoGeneral(Integer idArchivo, Integer idUsuario) {

        execEliminar(idArchivo, idUsuario);
        ResultClassEntity<Integer> result = new ResultClassEntity<>();
        result.setData(idArchivo);
        result.setSuccess(true);
        return result;
    }
    private void execEliminar(Integer idArchivo, Integer idUsuario) {
        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Archivo_Eliminar");
            sp.registerStoredProcedureParameter("idArchivo", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("idUsuarioElimina", Integer.class, ParameterMode.IN);
            sp.setParameter("idArchivo", idArchivo);
            sp.setParameter("idUsuarioElimina", idUsuario);
            sp.execute();
        } catch (Exception e) {
            log.error("ArchivoRepositoryImpl - execEliminar"+"Ocurrió un error :" + e.getMessage());
            throw e;
        }
    }

    @Override
    public ResultClassEntity<Integer> registrarArchivoGeneral(ArchivoEntity request) throws Exception {

        ResultClassEntity<Integer> result = new ResultClassEntity<>();

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_ArchivoGeneral_Registrar");
            sp.registerStoredProcedureParameter("ruta", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nombre", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nombreGenerado", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("extension", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipoDocumento", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("idUsuarioRegistro", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("bitFile", byte[].class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("idArchivo", Integer.class, ParameterMode.OUT);
            SpUtil.enableNullParams(sp);
            sp.setParameter("ruta", request.getRuta());
            sp.setParameter("nombre", request.getNombre());
            sp.setParameter("nombreGenerado", request.getNombreGenerado());
            sp.setParameter("extension", request.getExtension());
            sp.setParameter("tipoDocumento", request.getTipoDocumento());
            sp.setParameter("idUsuarioRegistro", request.getIdUsuarioRegistro());
            sp.setParameter("bitFile", request.getFile());
            sp.execute();

            Integer idArchivo = (Integer) sp.getOutputParameterValue("idArchivo");

            result.setCodigo(idArchivo);
            result.setData(idArchivo);
            result.setMessage("Información Registrada");
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            log.error("ArchivoRepositoryImpl - registrarArchivoGeneral"+"Ocurrió un error :" + e.getMessage());
            result.setCodigo(0);
            result.setSuccess(false);
            result.setMessage("Ocurrió un error.");
            return result;
        }
    }
}
