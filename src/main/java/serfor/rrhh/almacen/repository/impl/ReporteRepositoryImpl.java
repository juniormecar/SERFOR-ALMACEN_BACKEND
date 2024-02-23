package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.hibernate.query.procedure.internal.ProcedureParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.ReporteRepository;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ReporteRepositoryImpl extends JdbcDaoSupport implements ReporteRepository{
    @Autowired
    @Qualifier("dataSourceAlmacen")
    DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ReporteRepositoryImpl.class);

    public void setStoreProcedureEnableNullParameters(StoredProcedureQuery storedProcedureQuery) {
        if (storedProcedureQuery == null || storedProcedureQuery.getParameters() == null)
            return;

        for (Parameter parameter : storedProcedureQuery.getParameters()) {
            ((ProcedureParameterImpl) parameter).enablePassingNulls(true);
        }
    }


    @Override
    public Pageable<List<ReporteEntity>> ListarReporteSalidas(String tipoTransferencia, Integer nuIdAlmacen,
                                                              String tipoEspecie, String periodo,Date fechaInicio, Date fechaFin,String numeroDocumento, String numeroActa, String tipo,Integer nuIdTransferencia, Page p) throws Exception {
        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Reporte_Salidas_Listar");
            sp.registerStoredProcedureParameter("tipoTransferenciaDetalle", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nuIdAlmacen", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipoEspecie", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("periodo", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("fechaInicio", Date.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("fechaFin", Date.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("numeroDocumento", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("numeroActa", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipo", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nuIdTransferencia", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("tipoTransferenciaDetalle", tipoTransferencia);
            sp.setParameter("nuIdAlmacen", nuIdAlmacen);
            sp.setParameter("tipoEspecie", tipoEspecie);
            sp.setParameter("periodo", periodo);
            sp.setParameter("fechaInicio", fechaInicio);
            sp.setParameter("fechaFin", fechaFin);
            sp.setParameter("numeroDocumento", numeroDocumento);
            sp.setParameter("numeroActa", numeroActa);
            sp.setParameter("tipo", tipo);
            sp.setParameter("nuIdTransferencia", nuIdTransferencia);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarReporteSalidas(p, sp.getResultList());
        } catch (Exception e) {
            log.error("ListarReporteSalidas"+"Ocurrió un error :" + e.getMessage());
            return setResultDataListarReporteSalidas(p, null);
        }
    }

    private Pageable<List<ReporteEntity>> setResultDataListarReporteSalidas(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<ReporteEntity>> pageable=new Pageable<>(page);
        List<ReporteEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            ReporteEntity item = new ReporteEntity();
            item.setNuIdTransferencia((Integer) row[0]);
            item.setNombre((String) row[1]);
            item.setApellidos((String) row[2]);
            item.setTipoDocumento((String) row[3]);
            item.setDocumento((String) row[4]);
            item.setNroActa((String) row[5]);
            item.setNuIdRecurso((Integer) row[6]);
            item.setTipoTransferencia((String) row[7]);
            item.setTipoTransferenciaDetalle((String) row[8]);
            item.setObservaciones((String) row[9]);
            item.setNuIdAlmacen((Integer) row[10]);
            item.setFeFechaRegistro((Date) row[11]);
            item.setTxCodigoPuntoControl((String) row[12]);
            item.setTxCodigoPuntoControlDetalle((String) row[13]);
            item.setIdEspecie((Integer) row[14]);
            item.setCantidadProducto((BigDecimal) row[15]);
            item.setNombreComun((String) row[16]);
            item.setNombreCientifico((String) row[17]);
            item.setAlmacenOrigen((String) row[18]);
            item.setAlmacenDestino((String) row[19]);
            item.setMetroCubico((BigDecimal) row[20]);
            item.setFaunaSalida((String) row[22]);
            item.setTipoEspecie((String) row[23]);
            item.setUnidadMedida((String) row[24]);
            item.setTipoIngreso((String) row[25]);
            item.setCodigoUnico((String) row[26]);
            item.setNuIdAlmacenOrigin((Integer) row[27]);
            item.setCantidadProductoRet((BigDecimal) row[28]);
            item.setMetroCubicoRet((BigDecimal) row[29]);
            item.setNuIdArchivoTransferencia((Integer) row[30]);
            items.add(item);
            pageable.setTotalRecords(SpUtil.toLong(row[21]));

        }
        pageable.setData(items);
        pageable.setSuccess(true);
        //pageable.setTotalRecords(Long.valueOf(items.size()));
        if(items.size()>0){
            pageable.setMessage("Se obtuvo data.");
        }else{
            pageable.setMessage("No se encontró data.");
        }
        return pageable;
    }

    @Override

    public Pageable<List<ReporteEntity>> ListarReporteIndicadores(Integer nuIdAlmacen, String periodo,String tipoAccion,
                                                                  String numeroDocumento,String detalleReporte,Page p) throws Exception {

        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Reporte_Indicadores_Listar");
            sp.registerStoredProcedureParameter("nuIdAlmacen", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("periodo", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipoAccion", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("numeroDocumento", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("detalleReporte", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("nuIdAlmacen", nuIdAlmacen);
            sp.setParameter("periodo", periodo);
            sp.setParameter("tipoAccion", tipoAccion);
            sp.setParameter("numeroDocumento", numeroDocumento);
            sp.setParameter("detalleReporte", detalleReporte);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarReporteIndicadores(p, sp.getResultList());
        } catch (Exception e) {
            log.error("ListarReporteIndicadores"+"Ocurrió un error :" + e.getMessage());
            return setResultDataListarReporteIndicadores(p, null);
        }
    }

    private Pageable<List<ReporteEntity>> setResultDataListarReporteIndicadores(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<ReporteEntity>> pageable=new Pageable<>(page);
        List<ReporteEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            ReporteEntity item = new ReporteEntity();
            item.setAlmacenOrigen((String) row[0]);
            item.setNuIdAlmacen((Integer) row[1]);
            item.setTipoAccion((String) row[2]);
            item.setCantidadTotalIngresos((Integer) row[3]);
            item.setCantidadTotalSalidas((Integer) row[4]);
            item.setTipoEspecie((String) row[5]);
            item.setCantidadTotalMAD((BigDecimal) row[6]);
            item.setCantidadTotalNOMAD((BigDecimal) row[7]);
            item.setCantidadTotalFA((BigDecimal) row[8]);
            item.setCantidadTotalXtipoYunidadMedida((BigDecimal) row[9]);
            item.setUnidadMedida((String) row[10]);
            items.add(item);
            pageable.setTotalRecords(SpUtil.toLong(row[11]));

        }
        pageable.setData(items);
        pageable.setSuccess(true);
        if(items.size()>0){
            pageable.setMessage("Se obtuvo data.");
        }else{
            pageable.setMessage("No se encontró data.");
        }
        return pageable;
    }

    @Override

    public Pageable<List<ReporteEntity>> ListarReporteDisponibilidad(Integer nuIdAlmacen,
                                                                  String numeroDocumento,Page p) throws Exception {

        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Reporte_Disponibilidad_Listar");
            sp.registerStoredProcedureParameter("nuIdAlmacen", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("numeroDocumento", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("nuIdAlmacen", nuIdAlmacen);
            sp.setParameter("numeroDocumento", numeroDocumento);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarReporteDisponibilidad(p, sp.getResultList());
        } catch (Exception e) {
            log.error("ListarReporteDisponibilidad"+"Ocurrió un error :" + e.getMessage());
            return setResultDataListarReporteDisponibilidad(p, null);
        }
    }

    private Pageable<List<ReporteEntity>> setResultDataListarReporteDisponibilidad(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<ReporteEntity>> pageable=new Pageable<>(page);
        List<ReporteEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            ReporteEntity item = new ReporteEntity();
            item.setNuIdAlmacen((Integer) row[0]);
            item.setNombreAlmacen((String) row[1]);
            item.setAtf((String) row[2]);
            item.setPuestoControl((String) row[3]);
            item.setCantidadCapacidadMAD((BigDecimal) row[4]);
            item.setCantidadCapacidadNOMAD((BigDecimal) row[5]);
            item.setCantidadCapacidadFA((BigDecimal) row[6]);
            item.setCantidadTotalMAD((BigDecimal) row[7]);
            item.setCantidadTotalNOMAD((BigDecimal) row[8]);
            item.setCantidadTotalFA((BigDecimal) row[9]);
            items.add(item);
            pageable.setTotalRecords(SpUtil.toLong(row[10]));

        }
        pageable.setData(items);
        pageable.setSuccess(true);
        if(items.size()>0){
            pageable.setMessage("Se obtuvo data.");
        }else{
            pageable.setMessage("No se encontró data.");
        }
        return pageable;
    }
}
