package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.HistorialRecursoProductoRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class HistorialRecursoProductoRepositoryImpl extends JdbcDaoSupport implements HistorialRecursoProductoRepository {

    @Autowired
    @Qualifier("dataSourceAlmacen")
    DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(HistorialRecursoProductoRepositoryImpl.class);

    @Override
    public ResultClassEntity RegistroHistorialRecursoProducto(HistorialRecursoProductoEntity historialRecursoProducto){
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        HistorialRecursoProductoEntity historialRecursoProductonEntity = new HistorialRecursoProductoEntity();
        try {
            StoredProcedureQuery spa = em.createStoredProcedureQuery("almacen.pa_Historial_Recurso_Forestal_Registrar");
            spa.registerStoredProcedureParameter("IdRecursoProducto", Integer.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("CantidadProducto", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("TotalProducto", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("IdUsuarioRegistro", Integer.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("IdEspecie", Integer.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("TipoProducto", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("UnidadMedida", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("IdHistorialRecursoProducto", Integer.class, ParameterMode.OUT);
            SpUtil.enableNullParams(spa);
            spa.setParameter("IdRecursoProducto", historialRecursoProducto.getNuIdRecursoProducto());
            spa.setParameter("IdRecurso", historialRecursoProducto.getNuIdRecurso());
            spa.setParameter("CantidadProducto", historialRecursoProducto.getCantidadProducto());
            spa.setParameter("TotalProducto", historialRecursoProducto.getTotalProducto());
            spa.setParameter("IdUsuarioRegistro", historialRecursoProducto.getIdUsuarioRegistro());
            spa.setParameter("IdEspecie", historialRecursoProducto.getIdEspecie());
            spa.setParameter("TipoProducto", historialRecursoProducto.getTipoProducto());
            spa.setParameter("UnidadMedida", historialRecursoProducto.getUnidadMedida());
            spa.execute();
            Integer idHistorialRecursoProductoReturn =(Integer)spa.getOutputParameterValue("IdHistorialRecursoProducto");

            historialRecursoProductonEntity.setIdUsuarioRegistro(historialRecursoProducto.getIdUsuarioRegistro());
            historialRecursoProductonEntity.setIdHistorialRecursoForestal(idHistorialRecursoProductoReturn);
            resultClassEntity.setData(historialRecursoProductonEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        }catch (Exception e){
            log.error("Almacen - registrarAlmacen"+"Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

    @Override
    public Pageable<List<HistorialRecursoProductoEntity>> ListarHistorialRecursoForestal(Integer nuIdAlmacen, String nombreEspecie,
                                                                                         String tipoProducto, String tipoIngreso,
                                                                                         String disponible, Page p) throws Exception {

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Historial_Recurso_Forestal_Listar");
            sp.registerStoredProcedureParameter("nuIdAlmacen", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nombreEspecie", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipoProducto", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipoIngreso", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipoDisponibilidad", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);

            SpUtil.enableNullParams(sp);
            sp.setParameter("nuIdAlmacen", nuIdAlmacen);
            sp.setParameter("nombreEspecie", nombreEspecie);
            sp.setParameter("tipoProducto",tipoProducto );
            sp.setParameter("tipoIngreso",tipoIngreso );
            sp.setParameter("tipoDisponibilidad",disponible );
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());

            sp.execute();
            return setResultDataListarHistorialRecursoForestal(p, sp.getResultList());
        } catch (Exception e) {
            log.error("HistorialRecursoProductoRepositoryImpl - listar" + "Ocurrió un error :" + e.getMessage());
            return setResultDataListarHistorialRecursoForestal(p, null);
        }
    }
    private Pageable<List<HistorialRecursoProductoEntity>> setResultDataListarHistorialRecursoForestal(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<HistorialRecursoProductoEntity>> pageable = new Pageable<>(page);
        List<HistorialRecursoProductoEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            HistorialRecursoProductoEntity hrpEntity = new HistorialRecursoProductoEntity();
            hrpEntity.setFechaRegistro((Date) row[0]);
            hrpEntity.setNombreCientifico((String) row[1]);
            hrpEntity.setNombreComun((String) row[2]);
            hrpEntity.setDisponible((String) row[3]);
            hrpEntity.setTipoIngreso((String) row[4]);
            hrpEntity.setCantidadIngreso((BigDecimal) row[5]);
            hrpEntity.setSaldoTotalIngreso((BigDecimal) row[6]);
            hrpEntity.setTipoSAlida((String) row[7]);
            hrpEntity.setCantidadSalida((BigDecimal) row[8]);
            hrpEntity.setSaldoTotalSalida((BigDecimal) row[9]);
            hrpEntity.setNomAlmacen((String) row[11]);
            hrpEntity.setATF((String) row[12]);
            hrpEntity.setPuestoControl((String) row[13]);
            hrpEntity.setCantidadM3Ingreso((BigDecimal) row[14]);
            hrpEntity.setSaldoTotalM3Ingreso((BigDecimal) row[15]);
            hrpEntity.setCantidadM3Salida((BigDecimal) row[16]);
            hrpEntity.setSaldoTotalM3Salida((BigDecimal) row[17]);
            hrpEntity.setTipoProducto((String) row[18]);
            hrpEntity.setUnidadMedida((String) row[19]);
            hrpEntity.setCodigoUnico((String) row[20]);
            hrpEntity.setNroActa((String) row[21]);
            items.add(hrpEntity);
            pageable.setTotalRecords(SpUtil.toLong(row[10]));
        }
        pageable.setData(items);
        pageable.setSuccess(true);
        //pageable.setTotalRecords(Long.valueOf(items.size()));
        if (items.size() > 0) {
            pageable.setMessage("Se obtuvo data.");
        } else {
            pageable.setMessage("No se encontró data.");
        }
        return pageable;
    }
}
