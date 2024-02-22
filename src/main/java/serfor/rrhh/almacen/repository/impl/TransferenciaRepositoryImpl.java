package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.hibernate.query.procedure.internal.ProcedureParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.TransferenciaRepository;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class TransferenciaRepositoryImpl extends JdbcDaoSupport implements TransferenciaRepository {
    @Autowired
    @Qualifier("dataSourceAlmacen")
    DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(RecursoRepositoryImpl.class);

    public void setStoreProcedureEnableNullParameters(StoredProcedureQuery storedProcedureQuery) {
        if (storedProcedureQuery == null || storedProcedureQuery.getParameters() == null)
            return;

        for (Parameter parameter : storedProcedureQuery.getParameters()) {
            ((ProcedureParameterImpl) parameter).enablePassingNulls(true);
        }
    }

    @Override
    public ResultClassEntity RegistrarTransferencia(List<TransferenciaEntity> transferenciaEntityList) {
        ResultClassEntity result = new ResultClassEntity();
        List<TransferenciaEntity> transEntityList = new ArrayList<>();
        TransferenciaEntity transEntity = new TransferenciaEntity();

        try {
            for(TransferenciaEntity transferenciaEntity: transferenciaEntityList){
                StoredProcedureQuery spr = em.createStoredProcedureQuery("almacen.[pa_Transferencia_Registrar]");
                spr.registerStoredProcedureParameter("nuIdRecurso", Integer.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("tipoTransferencia", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nuIdUser", Integer.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nombreBeneficiario", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("apellidosBeneficiario", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("documentoBeneficiario", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("tipoDocumento", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nroGuiaBeneficiario", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("observaciones", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("txCodigoPuntoControl", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nuIdAlmacen", Integer.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nuIdAlmacenOrigin", Integer.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nuIdTransferencia", Integer.class, ParameterMode.OUT);
                spr.registerStoredProcedureParameter("nuIdRecursoHijo", Integer.class, ParameterMode.OUT);
                spr.registerStoredProcedureParameter("nuActaTransferencia", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nuResolucion", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nroActaTraslado", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("faunaSalida", String.class, ParameterMode.IN);
                setStoreProcedureEnableNullParameters(spr);
                spr.setParameter("nuIdRecurso", transferenciaEntity.getNuIdRecurso());
                spr.setParameter("tipoTransferencia", transferenciaEntity.getTipoTransferencia());
                spr.setParameter("nuIdUser", transferenciaEntity.getNuIdUsuarioRegistro());
                spr.setParameter("nombreBeneficiario", transferenciaEntity.getNombre());
                spr.setParameter("apellidosBeneficiario", transferenciaEntity.getApellidos());
                spr.setParameter("documentoBeneficiario", transferenciaEntity.getDocumento());
                spr.setParameter("tipoDocumento", transferenciaEntity.getTipoDocumento());
                spr.setParameter("nroGuiaBeneficiario", transferenciaEntity.getNroActa());
                spr.setParameter("observaciones", transferenciaEntity.getObservaciones());
                spr.setParameter("txCodigoPuntoControl", transferenciaEntity.getTxCodigoPuntoControl());
                spr.setParameter("nuIdAlmacen", transferenciaEntity.getNuIdAlmacen());
                spr.setParameter("nuIdAlmacenOrigin", transferenciaEntity.getNuIdAlmacenOrigin());
                spr.setParameter("nuActaTransferencia", transferenciaEntity.getNroActaTransferencia());
                spr.setParameter("nuResolucion", transferenciaEntity.getNroResolucion());
                spr.setParameter("nroActaTraslado", transferenciaEntity.getNroActaTraslado());
                spr.setParameter("faunaSalida", transferenciaEntity.getFaunaSalida());
                spr.execute();
                Integer idTransferenciaReturn = (Integer) spr.getOutputParameterValue("nuIdTransferencia");
                Integer idRecursoHijoReturn = (Integer) spr.getOutputParameterValue("nuIdRecursoHijo");

                /*** listar especieForestal***/
                for (TransferenciaDetalleEntity trans : transferenciaEntity.getLstTransferenciaDetalle()) {

                    StoredProcedureQuery srepro = em.createStoredProcedureQuery("almacen.pa_Transferencia_Detalle_Registrar");
                    srepro.registerStoredProcedureParameter("nuIdTransferencia", Integer.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("nuIdRecursoHijo", Integer.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("nuIdRecurso", Integer.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("nuIdProductoRecurso", Integer.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("nuIdEspecie", Integer.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("nuIdUser", Integer.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("nuCantidadProducto", BigDecimal.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("txTipoTransferencia", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("nombreComun", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("nombreCientifico", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("tipo", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("unidadMedida", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("nuMetroCubico", BigDecimal.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("disponibilidadActa", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("nuIdDetalleTransferencia", Integer.class, ParameterMode.OUT);
                    setStoreProcedureEnableNullParameters(srepro);
                    srepro.setParameter("nuIdTransferencia", idTransferenciaReturn);    
                    srepro.setParameter("nuIdRecursoHijo", idRecursoHijoReturn);
                    srepro.setParameter("nuIdRecurso", transferenciaEntity.getNuIdRecurso());
                    srepro.setParameter("nuIdProductoRecurso", trans.getNuIdRecursoProducto());
                    srepro.setParameter("nuIdEspecie", trans.getIdEspecie());
                    srepro.setParameter("nuIdUser", trans.getNuIdUsuarioRegistro());
                    srepro.setParameter("nuCantidadProducto", trans.getDescontar());
                    srepro.setParameter("nombreComun", trans.getNombreComun());
                    srepro.setParameter("nombreCientifico", trans.getNombreCientifico());
                    srepro.setParameter("txTipoTransferencia", transferenciaEntity.getTipoTransferencia());
                    srepro.setParameter("tipo",trans.getTipo());
                    srepro.setParameter("unidadMedida",trans.getUnidadMedida());
                    srepro.setParameter("nuMetroCubico", trans.getDescontarMetroCubico());
                    srepro.setParameter("disponibilidadActa", trans.getDisponibilidadActa());

                    srepro.execute();
                    Integer idDetalleTransferenciaReturn = (Integer) srepro.getOutputParameterValue("nuIdDetalleTransferencia");
                }
                transEntity.setNuIdRecurso(idTransferenciaReturn);
                transEntityList.add(transEntity);
            }
            /*** Registrar Tranferencia***/

            result.setData(transEntityList);
            result.setSuccess(true);
            result.setMessage("Se registró la transferencia correctamente.");

            return result;
        } catch (Exception e) {
            log.error("Recurso - registrarTransferencia" + "Ocurrió un error :" + e.getMessage());
            result.setSuccess(false);
            result.setMessage("Ocurrió un error.");
            return result;
        }
    }

    @Override
    public ResultClassEntity RegistrarRetorno(List<ReporteEntity> lstReporte) {
        ResultClassEntity resultClassEntity = new ResultClassEntity();


        try {
            for(ReporteEntity reporteEntity: lstReporte){
                StoredProcedureQuery spr = em.createStoredProcedureQuery("almacen.[pa_Retorno_Registrar]");

                spr.registerStoredProcedureParameter("nuIdRecurso", Integer.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("idEspecie", Integer.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("descontar", BigDecimal.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("descontarMetroCubico", BigDecimal.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nuIdAlmacenOrigin", Integer.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nuIdAlmacenDestino", Integer.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nroActaTraslado", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("tipoTransferencia", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("tipoEspecie", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nombreComun", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nombreCientifico", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nuIdTransferencia", Integer.class, ParameterMode.IN);
                setStoreProcedureEnableNullParameters(spr);

                spr.setParameter("nuIdRecurso", reporteEntity.getNuIdRecurso());
                spr.setParameter("idEspecie", reporteEntity.getIdEspecie());
                spr.setParameter("descontar", reporteEntity.getDescontar());
                spr.setParameter("descontarMetroCubico", reporteEntity.getDescontarMetroCubico());
                spr.setParameter("nuIdAlmacenOrigin", reporteEntity.getNuIdAlmacenOrigin());
                spr.setParameter("nuIdAlmacenDestino", reporteEntity.getNuIdAlmacen());
                spr.setParameter("nroActaTraslado", reporteEntity.getNroActa());
                spr.setParameter("tipoTransferencia", reporteEntity.getTipoTransferenciaDetalle());
                spr.setParameter("tipoEspecie", reporteEntity.getTipoEspecie());
                spr.setParameter("nombreComun", reporteEntity.getNombreComun());
                spr.setParameter("nombreCientifico", reporteEntity.getNombreCientifico());
                spr.setParameter("nuIdTransferencia", reporteEntity.getNuIdTransferencia());

                spr.execute();
            }
            resultClassEntity.setData(lstReporte);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        }

        catch (Exception e) {
            log.error("Recurso - registrarTransferencia" + "Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

    @Override
    public Pageable<List<TransferenciaEntity>> ListarTransferencia( Integer nuIdAlmacen,String documento,
                                                                    String tipoTransferencia,Integer nuIdTransferencia, Page p) throws Exception {
        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Transferencia_Listar");
            sp.registerStoredProcedureParameter("nuIdAlmacen", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("NumeroDocumento", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("TipoTransferencia", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nuIdTransferencia", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("nuIdAlmacen", nuIdAlmacen);
            sp.setParameter("NumeroDocumento", documento);
            sp.setParameter("TipoTransferencia", tipoTransferencia);
            sp.setParameter("nuIdTransferencia", nuIdTransferencia);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarTransferencia(p, sp.getResultList());
        } catch (Exception e) {
            log.error("AlmacenRepositoryImpl - listar"+"Ocurrió un error :" + e.getMessage());
            return setResultDataListarTransferencia(p, null);
        }
    }

    private Pageable<List<TransferenciaEntity>> setResultDataListarTransferencia(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<TransferenciaEntity>> pageable=new Pageable<>(page);
        List<TransferenciaEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            TransferenciaEntity item = new TransferenciaEntity();
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
            item.setCantidadProducto((Integer) row[15]);
            item.setNombreComun((String) row[16]);
            item.setNombreCientifico((String) row[17]);
            item.setAlmacenOrigen((String) row[19]);
            item.setAlmacenDestino((String) row[20]);
            item.setMetroCubico((BigDecimal) row[21]);
            items.add(item);
            pageable.setTotalRecords(SpUtil.toLong(row[18]));

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
    public Pageable<List<TransferenciaEntity>> ListarReportesAvanzados( Integer nuIdAlmacen,
                                                                    String tipoTransferencia, Integer nuIdTransferencia,String tipoTransferenciaDetalle, Page p) throws Exception {
        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Reportes_Avanzados_Listar");
            sp.registerStoredProcedureParameter("nuIdAlmacen", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("TipoTransferencia", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nuIdTransferencia", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipoTransferenciaDetalle", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("nuIdAlmacen", nuIdAlmacen);
            sp.setParameter("TipoTransferencia", tipoTransferencia);
            sp.setParameter("nuIdTransferencia", nuIdTransferencia);
            sp.setParameter("tipoTransferenciaDetalle", tipoTransferenciaDetalle);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarReportesAvanzados(p, sp.getResultList());
        } catch (Exception e) {
            log.error("AlmacenRepositoryImpl - listar"+"Ocurrió un error :" + e.getMessage());
            return setResultDataListarReportesAvanzados(p, null);
        }
    }

    private Pageable<List<TransferenciaEntity>> setResultDataListarReportesAvanzados(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<TransferenciaEntity>> pageable=new Pageable<>(page);
        List<TransferenciaEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            TransferenciaEntity item = new TransferenciaEntity();
            item.setNuIdTransferencia((Integer) row[0]);
            item.setTipoTransferenciaDetalle((String) row[1]);
            item.setFeFechaRegistro((Date) row[2]);
            item.setAlmacenDestino((String) row[3]);
            item.setNombreComun((String) row[4]);
            item.setNombreCientifico((String) row[5]);
            item.setCantidadProducto((Integer) row[6]);
            item.setTipoEspecie((String) row[7]);
            items.add(item);
            pageable.setTotalRecords(SpUtil.toLong(row[8]));

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


}
