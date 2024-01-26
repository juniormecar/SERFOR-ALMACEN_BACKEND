package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.poi.hpsf.Decimal;
import org.hibernate.query.procedure.internal.ProcedureParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.RecursoRepository;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class RecursoRepositoryImpl extends JdbcDaoSupport implements RecursoRepository {

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
    public ResultClassEntity RegistrarRecurso(RecursoEntity recursoEntity) {
        ResultClassEntity result = new ResultClassEntity();
        RecursoEntity reEntity = new RecursoEntity();

        try {


            /*** Registrar Recurso***/
            StoredProcedureQuery spr = em.createStoredProcedureQuery("almacen.pa_Recurso_Registrar");
            spr.registerStoredProcedureParameter("NombreRecurso", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("TipoRecurso", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("IdUsuarioRegistro", Integer.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("NroGuiaTransporteForestal", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("NombreAutoridadRegional", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("OrigenRecurso", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("FechaExpedicion", Date.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("FechaVencimiento", Date.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("Observaciones", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("IdAlmacen", Integer.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("EstadoRecurso", Character.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("TipoRegistro", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("NroActa", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("TipoDocumento", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("NroDocumento", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("tipoIngreso", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("nombres", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("direccion", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("foto", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("tipoInfraccion", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("tipoSancion", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("TipoDocConductor", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("NroDocConductor", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("nombresConductor", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("placa", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("fechaIngreso", Date.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("horaIngreso", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.INOUT);
            setStoreProcedureEnableNullParameters(spr);
            spr.setParameter("NombreRecurso", recursoEntity.getTxNombreRecurso());
            spr.setParameter("TipoRecurso", recursoEntity.getTxTipoRecurso());
            spr.setParameter("IdUsuarioRegistro", recursoEntity.getNuIdUsuarioRegistro());
            spr.setParameter("NroGuiaTransporteForestal", recursoEntity.getTxNroGuiaTransporteForestal());
            spr.setParameter("NombreAutoridadRegional", recursoEntity.getTxNombreAutoridadRegional());
            spr.setParameter("OrigenRecurso", recursoEntity.getTxOrigenRecurso());
            spr.setParameter("FechaExpedicion", recursoEntity.getFeFechaExpedicion());
            spr.setParameter("FechaVencimiento", recursoEntity.getFeFechaVencimiento());
            spr.setParameter("Observaciones", recursoEntity.getTxObservaciones());
            spr.setParameter("IdAlmacen", recursoEntity.getNuIdAlmacen());
            spr.setParameter("EstadoRecurso", recursoEntity.getTxEstadoRecurso());
            spr.setParameter("TipoRegistro", recursoEntity.getTxTipoRegistro());
            spr.setParameter("NroActa", recursoEntity.getNumeroActa());
            spr.setParameter("TipoDocumento", recursoEntity.getTipoDocumento());
            spr.setParameter("NroDocumento", recursoEntity.getNumeroDocumento());
            spr.setParameter("tipoIngreso", recursoEntity.getTipoIngreso());
            spr.setParameter("nombres", recursoEntity.getNombres());
            spr.setParameter("direccion", recursoEntity.getDireccion());
            spr.setParameter("foto", recursoEntity.getFoto());
            spr.setParameter("tipoInfraccion", recursoEntity.getTipoInfraccion());
            spr.setParameter("tipoSancion", recursoEntity.getTipoSancion());
            spr.setParameter("TipoDocConductor", recursoEntity.getTipoDocumentoConductor());
            spr.setParameter("NroDocConductor", recursoEntity.getNumeroDocumentoConductor());
            spr.setParameter("nombresConductor", recursoEntity.getNombresConductor());
            spr.setParameter("placa", recursoEntity.getPlaca());
            spr.setParameter("fechaIngreso", recursoEntity.getFechaIngreso());
            spr.setParameter("horaIngreso", recursoEntity.getHoraIngreso());
            spr.setParameter("IdRecurso", recursoEntity.getNuIdRecurso());
            spr.execute();
            Integer idRecursoReturn = (Integer) spr.getOutputParameterValue("IdRecurso");
            reEntity.setNuIdRecurso(idRecursoReturn);
            if( recursoEntity.getIntervenido() !=null) {
                /*** Registrar Recurso Persona(intervenido)***/
                StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Recurso_Persona_Registrar");
                sp.registerStoredProcedureParameter("tipoDocumento", String.class, ParameterMode.IN);
                sp.registerStoredProcedureParameter("nroDocumento", String.class, ParameterMode.IN);
                sp.registerStoredProcedureParameter("nombres", String.class, ParameterMode.IN);
                sp.registerStoredProcedureParameter("foto", String.class, ParameterMode.IN);
                sp.registerStoredProcedureParameter("placa", String.class, ParameterMode.IN);
                sp.registerStoredProcedureParameter("tipoPersona", String.class, ParameterMode.IN);
                sp.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.IN);
                sp.registerStoredProcedureParameter("IdUsuarioRegistro", Integer.class, ParameterMode.IN);
                sp.registerStoredProcedureParameter("IdRecursoPersona", Integer.class, ParameterMode.INOUT);
                setStoreProcedureEnableNullParameters(sp);
                sp.setParameter("tipoDocumento", recursoEntity.getIntervenido().getTipoDocumento());
                sp.setParameter("nroDocumento", recursoEntity.getIntervenido().getNumeroDocumento());
                sp.setParameter("nombres", recursoEntity.getIntervenido().getNombresPersona());
                sp.setParameter("foto", recursoEntity.getIntervenido().getFoto());
                sp.setParameter("placa", recursoEntity.getIntervenido().getPlaca());
                sp.setParameter("tipoPersona", recursoEntity.getIntervenido().getTipoPersona());
                sp.setParameter("IdRecurso", reEntity.getNuIdRecurso());
                sp.setParameter("IdUsuarioRegistro", recursoEntity.getIntervenido().getIdUsuarioRegistro());
                sp.setParameter("IdRecursoPersona", recursoEntity.getIntervenido().getIdRecursoPersona());
                sp.execute();
                Integer idRecursoPersonaReturn = (Integer) sp.getOutputParameterValue("IdRecursoPersona");
                recursoEntity.getIntervenido().setIdRecursoPersona(idRecursoPersonaReturn);
            }
            if( recursoEntity.getConductor() !=null) {
                /*** Registrar Recurso Persona(conductor)***/
                StoredProcedureQuery stpro = em.createStoredProcedureQuery("almacen.pa_Recurso_Persona_Registrar");
                stpro.registerStoredProcedureParameter("tipoDocumento", String.class, ParameterMode.IN);
                stpro.registerStoredProcedureParameter("nroDocumento", String.class, ParameterMode.IN);
                stpro.registerStoredProcedureParameter("nombres", String.class, ParameterMode.IN);
                stpro.registerStoredProcedureParameter("foto", String.class, ParameterMode.IN);
                stpro.registerStoredProcedureParameter("placa", String.class, ParameterMode.IN);
                stpro.registerStoredProcedureParameter("tipoPersona", String.class, ParameterMode.IN);
                stpro.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.IN);
                stpro.registerStoredProcedureParameter("IdUsuarioRegistro", Integer.class, ParameterMode.IN);
                stpro.registerStoredProcedureParameter("IdRecursoPersona", Integer.class, ParameterMode.INOUT);
                setStoreProcedureEnableNullParameters(stpro);
                stpro.setParameter("tipoDocumento", recursoEntity.getConductor().getTipoDocumento());
                stpro.setParameter("nroDocumento", recursoEntity.getConductor().getNumeroDocumento());
                stpro.setParameter("nombres", recursoEntity.getConductor().getNombresPersona());
                stpro.setParameter("foto", recursoEntity.getConductor().getFoto());
                stpro.setParameter("placa", recursoEntity.getConductor().getPlaca());
                stpro.setParameter("tipoPersona", recursoEntity.getConductor().getTipoPersona());
                stpro.setParameter("IdRecurso", reEntity.getNuIdRecurso());
                stpro.setParameter("IdUsuarioRegistro", recursoEntity.getConductor().getIdUsuarioRegistro());
                stpro.setParameter("IdRecursoPersona", recursoEntity.getConductor().getIdRecursoPersona());
                stpro.execute();
                Integer idRecursoPersonaConductorReturn = (Integer) stpro.getOutputParameterValue("IdRecursoPersona");
                recursoEntity.getConductor().setIdRecursoPersona(idRecursoPersonaConductorReturn);
            }

            /*** listar especieForestal***/
            for (RecursoProductoEntity pro : recursoEntity.getLstEspecie()) {

                /*** Registrar Recurso-especieForestal***/
                StoredProcedureQuery srepro = em.createStoredProcedureQuery("almacen.pa_Recurso_Producto_Registrar");
                srepro.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("IdEspecie", Integer.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("CantidadProducto", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("TotalProducto", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("IdUsuarioRegistro", Integer.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("tipoProducto", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("unidadMedida", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("tipo", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("nombreComun", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("nombreCientifico", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("tipoAlmacenamiento", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("capacidadUnidad", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("tipoSubProducto", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("metroCubico", BigDecimal.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("disponibilidadActa", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("cantidadTotal", BigDecimal.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("IdRecursoProducto", Integer.class, ParameterMode.INOUT);
                setStoreProcedureEnableNullParameters(srepro);
                srepro.setParameter("IdRecurso", reEntity.getNuIdRecurso());
                srepro.setParameter("IdEspecie", pro.getIdEspecie());
                srepro.setParameter("CantidadProducto", pro.getTxCantidadProducto());
                srepro.setParameter("TotalProducto", pro.getTxTotalProducto());
                srepro.setParameter("IdUsuarioRegistro", pro.getIdUsuarioRegistro());
                srepro.setParameter("tipoProducto", pro.getTipoProducto());
                srepro.setParameter("unidadMedida", pro.getUnidadMedida());
                srepro.setParameter("IdRecursoProducto", pro.getNuIdRecursoProducto());
                srepro.setParameter("tipo", pro.getType());
                srepro.setParameter("nombreComun", pro.getNombreComun());
                srepro.setParameter("nombreCientifico", pro.getNombreCientifico());
                srepro.setParameter("tipoAlmacenamiento", pro.getTipoAlmacenamiento());
                srepro.setParameter("capacidadUnidad", pro.getCapacidadUnidad());
                srepro.setParameter("tipoSubProducto", pro.getTipoSubProducto());
                srepro.setParameter("metroCubico", pro.getMetroCubico());
                srepro.setParameter("disponibilidadActa", pro.getDisponibilidadActa());
                srepro.setParameter("cantidadTotal", pro.getCantidadTotal());
                srepro.execute();
                //Integer idProductosReturn =(Integer)sppr.getOutputParameterValue("@IdRecursoProducto");
                Integer idProductoRecursoReturn = (Integer) srepro.getOutputParameterValue("IdRecursoProducto");
                pro.setNuIdRecursoProducto(idProductoRecursoReturn);
            }
            reEntity.setNuIdRecurso(idRecursoReturn);
            result.setData(reEntity);
            result.setSuccess(true);
            result.setMessage("Se registró el recurso correctamente.");

            return result;
        } catch (Exception e) {
            log.error("Recurso - registrarRecursos" + "Ocurrió un error :" + e.getMessage());
            result.setSuccess(false);
            result.setMessage("Ocurrió un error.");
            return result;
        }
    }

    @Override
    public ResultClassEntity ActualizarRecurso(RecursoEntity recursoEntity) {
        ResultClassEntity result = new ResultClassEntity();
        RecursoEntity reEntity = new RecursoEntity();

        try {

            /*** Actualizar Recurso***/
            StoredProcedureQuery spr = em.createStoredProcedureQuery("almacen.pa_Recurso_Actualizar");
            spr.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("NombreRecurso", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("TipoRecurso", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("idUsuarioModifica", Integer.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("NroGuiaTransporteForestal", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("NombreAutoridadRegional", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("OrigenRecurso", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("FechaExpedicion", Date.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("FechaVencimiento", Date.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("Observaciones", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("IdAlmacen", Integer.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("TipoRegistro", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("EstadoRecurso", Character.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("NroActa", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("TipoDocumento", String.class, ParameterMode.IN);
            spr.registerStoredProcedureParameter("NroDocumento", String.class, ParameterMode.IN);
            setStoreProcedureEnableNullParameters(spr);
            spr.setParameter("IdRecurso", recursoEntity.getNuIdRecurso());
            spr.setParameter("NombreRecurso", recursoEntity.getTxNombreRecurso());
            spr.setParameter("TipoRecurso", recursoEntity.getTxTipoRecurso());
            spr.setParameter("idUsuarioModifica", recursoEntity.getNuIdUsuarioModificacion());
            spr.setParameter("NroGuiaTransporteForestal", recursoEntity.getTxNroGuiaTransporteForestal());
            spr.setParameter("NombreAutoridadRegional", recursoEntity.getTxNombreAutoridadRegional());
            spr.setParameter("OrigenRecurso", recursoEntity.getTxOrigenRecurso());
            spr.setParameter("FechaExpedicion", recursoEntity.getFeFechaExpedicion());
            spr.setParameter("FechaVencimiento", recursoEntity.getFeFechaVencimiento());
            spr.setParameter("Observaciones", recursoEntity.getTxObservaciones());
            //spr.setParameter("IdAlmacen", recursoEntity.getAlmacen().getNuIdAlmacen());
            spr.setParameter("IdAlmacen", recursoEntity.getNuIdAlmacen());
            spr.setParameter("TipoRegistro", recursoEntity.getTxTipoRegistro());
            spr.setParameter("EstadoRecurso", recursoEntity.getTxEstadoRecurso());
            spr.setParameter("NroActa", recursoEntity.getNumeroActa());
            spr.setParameter("TipoDocumento", recursoEntity.getTipoDocumento());
            spr.setParameter("NroDocumento", recursoEntity.getNumeroDocumento());
            spr.execute();

            if(!recursoEntity.getLstEspecie().isEmpty()){
                for(RecursoProductoEntity especie :recursoEntity.getLstEspecie()){
                    /*** Actualizar Recurso***/
                    StoredProcedureQuery srepro = em.createStoredProcedureQuery("almacen.pa_Recurso_Producto_Actualizar");
                    srepro.registerStoredProcedureParameter("IdRecursoProducto", Integer.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("CantidadProducto", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("TotalProducto", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("IdEspecie", Integer.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("tipoProducto", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("unidadMedida", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("IdUsuarioModificacion", Integer.class, ParameterMode.IN);
                    setStoreProcedureEnableNullParameters(srepro);
                    srepro.setParameter("IdRecursoProducto", especie.getNuIdRecursoProducto());
                    srepro.setParameter("IdRecurso", especie.getNuIdRecurso());
                    srepro.setParameter("CantidadProducto", especie.getTxCantidadProducto());
                    srepro.setParameter("TotalProducto", especie.getTxTotalProducto());
                    srepro.setParameter("IdEspecie", especie.getIdEspecie());
                    srepro.setParameter("tipoProducto", especie.getTipoProducto());
                    srepro.setParameter("unidadMedida", especie.getUnidadMedida());
                    srepro.setParameter("IdUsuarioModificacion", especie.getNuIdUsuarioModificacion());
                    srepro.execute();
                }
            }


            result.setSuccess(true);
            result.setMessage("Se actualizó el recurso correctamente.");

            return result;
        } catch (Exception e) {
            log.error("Recurso - actualizarRecursos" + "Ocurrió un error :" + e.getMessage());
            result.setSuccess(false);
            result.setMessage("Ocurrió un error.");
            return result;
        }
    }

    @Override
    public Pageable<List<RecursoEntity>> ListarRecurso(String numeroDocumento, String numeroActa,
                                                       String txNroGuiaTransporteForestal, String tipoIngreso,Integer nuIdAlmacen, String documentoSesion, Page p) throws Exception {

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Recurso_Listar");
            sp.registerStoredProcedureParameter("NumeroDocumento", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("Acta", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("GuiaForestal", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipoIngreso", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("idAlmacen", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("documentoSesion", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);

            SpUtil.enableNullParams(sp);
            sp.setParameter("NumeroDocumento", numeroDocumento);
            sp.setParameter("Acta", numeroActa);
            sp.setParameter("GuiaForestal", txNroGuiaTransporteForestal);
            sp.setParameter("tipoIngreso", tipoIngreso);
            sp.setParameter("idAlmacen", nuIdAlmacen);
            sp.setParameter("documentoSesion", documentoSesion);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());

            sp.execute();
            return setResultDataListarRecurso(p, sp.getResultList());
        } catch (Exception e) {
            log.error("RecursoRepositoryImpl - listar" + "Ocurrió un error :" + e.getMessage());
            return setResultDataListarRecurso(p, null);
        }
    }

    private Pageable<List<RecursoEntity>> setResultDataListarRecurso(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<RecursoEntity>> pageable = new Pageable<>(page);
        List<RecursoEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            RecursoEntity reEntity = new RecursoEntity();
            reEntity.setNuIdRecurso((Integer) row[0]);
            reEntity.setTxNombreRecurso((String) row[1]);
            reEntity.setTxTipoRecurso((String) row[2]);
            reEntity.setNuIdUsuarioModificacion((Integer) row[3]);
            reEntity.setFeFechaModificacion((Date) row[4]);
            reEntity.setTxEstado((String) row[5]);
            reEntity.setNuIdUsuarioElimina((Integer) row[6]);
            reEntity.setFeFechaElimina((Date) row[7]);
            reEntity.setNuIdUsuarioRegistro((Integer) row[8]);
            reEntity.setFeFechaRegistro((Date) row[9]);
            reEntity.setTxNroGuiaTransporteForestal((String) row[10]);
            reEntity.setTxNombreAutoridadRegional((String) row[11]);
            reEntity.setFeFechaExpedicion((Date) row[12]);
            reEntity.setTxOrigenRecurso((String) row[13]);
            reEntity.setFeFechaVencimiento((Date) row[14]);
            reEntity.setTxObservaciones((String) row[15]);
            reEntity.setTxEstadoRecurso((Character) row[16]);
            reEntity.setTxTipoRegistro((String) row[17]);
            reEntity.setNumeroActa((String) row[18]);
            //reEntity.setTipoDocumento((String) row[19]);
            //reEntity.setNumeroDocumento((String) row[20]);
            reEntity.setTxDescEstadoRecurso((String) row[19]);
            reEntity.setNuIdAlmacen((Integer) row[20]);
            reEntity.setTipoIngreso((String) row[21]);
            reEntity.setTipoIngresoDesc((String) row[22]);
            reEntity.setNombres((String) row[23]);
            reEntity.setDireccion((String) row[24]);
            reEntity.setFoto((String) row[25]);
            reEntity.setTipoInfraccion((String) row[26]);
            reEntity.setTipoSancion((String) row[27]);
            reEntity.setTxNombreAlmacen((String) row[28]);
            reEntity.setTipoDocumentoConductor((String) row[29]);
            reEntity.setNumeroDocumentoConductor((String) row[30]);
            reEntity.setNombresConductor((String) row[31]);
            reEntity.setPlaca((String) row[32]);
            reEntity.setFechaIngreso((Date) row[33]);
            reEntity.setHoraIngreso((String) row[34]);
            RecursoPersonaEntity recursoPersonaEntity = new RecursoPersonaEntity();
            recursoPersonaEntity.setIdRecursoPersona((Integer) row[35] );
            recursoPersonaEntity.setTipoDocumento((String) row[36] );
            recursoPersonaEntity.setNumeroDocumento((String) row[37] );
            recursoPersonaEntity.setNombresPersona((String) row[38]);
            recursoPersonaEntity.setFoto((String) row[39] );
            recursoPersonaEntity.setTipoPersona((String) row[40] );
            reEntity.setIntervenido(recursoPersonaEntity);
            RecursoPersonaEntity recursoPersonaEntity2 = new RecursoPersonaEntity();
            recursoPersonaEntity2.setIdRecursoPersona((Integer) row[41] );
            recursoPersonaEntity2.setTipoDocumento((String) row[42] );
            recursoPersonaEntity2.setNumeroDocumento((String) row[43] );
            recursoPersonaEntity2.setNombresPersona((String) row[44] );
            recursoPersonaEntity2.setFoto((String) row[45] );
            recursoPersonaEntity2.setPlaca((String) row[46] );
            recursoPersonaEntity2.setTipoPersona((String) row[47] );
            reEntity.setConductor(recursoPersonaEntity2);
            items.add(reEntity);
            pageable.setTotalRecords(SpUtil.toLong(row[48]));

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

    @Override
    public ResultClassEntity EliminarRecurso(Integer nuIdRecurso,Integer nuIdUsuarioElimina) throws Exception {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        RecursoEntity recursoEntity = new RecursoEntity();

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Recurso_Eliminar");
            sp.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("IdUsuarioElimina", Integer.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("IdRecurso", nuIdRecurso);
            sp.setParameter("IdUsuarioElimina", nuIdUsuarioElimina);
            sp.execute();
            recursoEntity.setNuIdRecurso(recursoEntity.getNuIdRecurso());
            recursoEntity.setNuIdUsuarioElimina(recursoEntity.getNuIdUsuarioElimina());
            resultClassEntity.setData(recursoEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        } catch (Exception e) {
            log.error("Recurso - listarRecurso" + "Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

    @Override
    public Pageable<List<RecursoProductoEntity>> ListarRecursoEspecie(Integer idEspecie, Integer idRecurso,
                                                                      Page p) throws Exception {

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Recurso_Producto_Listar");
            sp.registerStoredProcedureParameter("IdEspecie", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("offset", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("IdEspecie", idEspecie);
            sp.setParameter("IdRecurso", idRecurso);
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.setParameter("offset", p.getOffset());
            sp.setParameter("pageSize", p.getPageSize());
            sp.execute();
            return setResultDataListarRecursoEspecie(p, sp.getResultList());
        } catch (Exception e) {
            log.error("RecursoEspecieRepositoryImpl - listar" + "Ocurrió un error :" + e.getMessage());
            return setResultDataListarRecursoEspecie(p, null);
        }
    }

    private Pageable<List<RecursoProductoEntity>> setResultDataListarRecursoEspecie(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<RecursoProductoEntity>> pageable = new Pageable<>(page);
        List<RecursoProductoEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            RecursoProductoEntity reEntity = new RecursoProductoEntity();
            reEntity.setFeFechaRegistro((Date) row[0]);
            reEntity.setNuIdRecursoProducto((Integer) row[1]);
            reEntity.setNuIdRecurso((Integer) row[2]);
            reEntity.setTxCantidadProducto((String) row[3]);
            reEntity.setTxTotalProducto((String) row[4]);
            reEntity.setTxEstado((String) row[5]);
            reEntity.setIdUsuarioRegistro((Integer) row[6]);
            reEntity.setIdEspecie((Integer)  row[7]);
            reEntity.setNombreCientifico((String) row[8]);
            reEntity.setNombreComercial((String) row[9]);
            reEntity.setNombreComun((String) row[10]);
            reEntity.setFamilia((String) row[11]);
            reEntity.setTipoProducto((String) row[13]);
            reEntity.setUnidadMedida((String) row[14]);
            reEntity.setType((String) row[15]);
            reEntity.setTipoAlmacenamiento((String) row[16]);
            reEntity.setCapacidadUnidad((String) row[17]);
            reEntity.setTipoSubProducto((String) row[18]);
            reEntity.setDesctipoProducto((String) row[19]);
            reEntity.setMetroCubico((BigDecimal) row[20]);
            reEntity.setDisponibilidadActa((String) row[21]);
            reEntity.setCantidadTotal((BigDecimal) row[22]);
            items.add(reEntity);
            pageable.setTotalRecords(SpUtil.toLong(row[12]));
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

    @Override
    public List<RecursoProductoEntity> ListarRecursoEspeciePDF(Integer idEspecie, Integer idRecurso,
                                                                      Page p) throws Exception {

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Recurso_Producto_Listar");
            sp.registerStoredProcedureParameter("IdEspecie", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("offset", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("IdEspecie", idEspecie);
            sp.setParameter("IdRecurso", idRecurso);
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.setParameter("offset", p.getOffset());
            sp.setParameter("pageSize", p.getPageSize());
            sp.execute();
            return setResultDataListarRecursoEspeciePDF(p, sp.getResultList());
        } catch (Exception e) {
            log.error("RecursoEspecieRepositoryImpl - listar" + "Ocurrió un error :" + e.getMessage());
            return setResultDataListarRecursoEspeciePDF(p, null);
        }
    }

    private List<RecursoProductoEntity> setResultDataListarRecursoEspeciePDF(Page page, List<Object[]> dataDb) throws Exception {

        List<RecursoProductoEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            RecursoProductoEntity reEntity = new RecursoProductoEntity();
            reEntity.setFeFechaRegistro((Date) row[0]);
            reEntity.setNuIdRecursoProducto((Integer) row[1]);
            reEntity.setNuIdRecurso((Integer) row[2]);
            reEntity.setTxCantidadProducto((String) row[3]);
            reEntity.setTxTotalProducto((String) row[4]);
            reEntity.setTxEstado((String) row[5]);
            reEntity.setIdUsuarioRegistro((Integer) row[6]);
            reEntity.setIdEspecie((Integer) row[7]);
            reEntity.setNombreCientifico((String) row[8]);
            reEntity.setNombreComercial((String) row[9]);
            reEntity.setNombreComun((String) row[10]);
            reEntity.setFamilia((String) row[11]);
            reEntity.setTipoProducto((String) row[13]);
            reEntity.setUnidadMedida((String) row[14]);
            reEntity.setType((String) row[15]);
            reEntity.setTipoAlmacenamiento((String) row[16]);
            reEntity.setCapacidadUnidad((String) row[17]);
            reEntity.setTipoSubProducto((String) row[18]);
            reEntity.setDesctipoProducto((String) row[19]);
            reEntity.setMetroCubico((BigDecimal) row[20]);
            items.add(reEntity);
        }
        return items;
    }

    @Override
    public Pageable<List<RecursoProductoEntity>> ListarRecursoVerProductos(Integer nuIdAlmacen, String guiaForestal,
                                                                           String numeroActa, String nombres,
                                                                           String nombreProducto, Integer idEspecie,
                                                                           String numeroDocumento, String tipoDetalle,
                                                                           String nombreCientifico, String nombreComun,
                                                                           String txNumeroATF,String txPuestoControl,String nombreAlmacen,
                                                                           String tipo,String unidadMedida,String tipoIngreso,String disponibilidadActa,Page p) throws Exception {

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Recurso_VerProductos_Listar");
            sp.registerStoredProcedureParameter("nuIdAlmacen", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("guiaForestal", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("numeroActa", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nombres", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nombreProducto", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("idEspecie", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("NumeroDocumento", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipoDetalle", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nombreCientifico", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nombreComun", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("Atf", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("PuestoControl", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("nombreAlmacen", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipo", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("unidadMedida", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipoIngreso", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipoDisponibilidad", String.class, ParameterMode.IN);

            SpUtil.enableNullParams(sp);
            sp.setParameter("nuIdAlmacen", nuIdAlmacen);
            sp.setParameter("guiaForestal", guiaForestal);
            sp.setParameter("numeroActa", numeroActa);
            sp.setParameter("nombres", nombres);
            sp.setParameter("nombreProducto", nombreProducto);
            sp.setParameter("idEspecie", idEspecie);
            sp.setParameter("NumeroDocumento", numeroDocumento);
            sp.setParameter("tipoDetalle", tipoDetalle);
            sp.setParameter("nombreCientifico", nombreCientifico);
            sp.setParameter("nombreComun", nombreComun);
            sp.setParameter("Atf", txNumeroATF);
            sp.setParameter("PuestoControl", txPuestoControl);
            sp.setParameter("nombreAlmacen", nombreAlmacen);
            sp.setParameter("tipo", tipo);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.setParameter("unidadMedida", unidadMedida);
            sp.setParameter("tipoIngreso", tipoIngreso);
            sp.setParameter("tipoDisponibilidad", disponibilidadActa);


            sp.execute();
            return setResultDataListarRecursoVerProductos(p, sp.getResultList());
        } catch (Exception e) {
            log.error("RecursoRepositoryImpl - listar" + "Ocurrió un error :" + e.getMessage());
            return setResultDataListarRecursoVerProductos(p, null);
        }
    }
    private Pageable<List<RecursoProductoEntity>> setResultDataListarRecursoVerProductos(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<RecursoProductoEntity>> pageable = new Pageable<>(page);
        List<RecursoProductoEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            RecursoProductoEntity rpeEntity = new RecursoProductoEntity();
            RecursoEntity rEntity = new RecursoEntity();
            rpeEntity.setNroGuiaTransporteForestal((String)row[0]);
            rpeEntity.setNumeroActa((String)row[1]);
            rpeEntity.setNombreCientifico((String) row[2]);
            rpeEntity.setNombreComun((String) row[3]);
            rpeEntity.setTxCantidadProducto(String.valueOf((BigDecimal) row[4]));
            rpeEntity.setTipoProducto((String) row[5]);
            rpeEntity.setUnidadMedida((String) row[6]);
            rpeEntity.setNuIdRecurso((Integer) row[7]);
            rpeEntity.setNuIdAlmacen((Integer) row[8]);
            rpeEntity.setIdEspecie((Integer)  row[9]);
            rpeEntity.setNuIdRecursoProducto((Integer) row[10]);
            rpeEntity.setFeFechaRegistro((Date) row[12]);
            rpeEntity.setNombreAlmacen((String) row[13]);
            rpeEntity.setTipoIngreso((String) row[14]);
            rpeEntity.setTipo((String) row[15]);
            rpeEntity.setMetroCubico((BigDecimal) row[16]);
            rpeEntity.setDisponibilidadActa((String) row[17]);
            rpeEntity.setCapacidadUnidad((String) row[18]);
            items.add(rpeEntity);
            pageable.setTotalRecords(SpUtil.toLong(row[11]));
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

    @Override
    public ResultClassEntity EliminarRecursoEspecieForestal(RecursoProductoEntity recursoespecieforestal) throws Exception {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        RecursoProductoEntity recursoProductoEntity = new RecursoProductoEntity();

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Recurso_Especie_Forestal_Eliminar");
            sp.registerStoredProcedureParameter("IdRecursoEspecieForestal", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("IdUsuarioElimina", Integer.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("IdRecursoEspecieForestal", recursoespecieforestal.getNuIdRecursoProducto());
            sp.setParameter("IdUsuarioElimina", recursoespecieforestal.getNuIdUsuarioElimina());
            sp.execute();
            recursoProductoEntity.setNuIdRecursoProducto(recursoespecieforestal.getNuIdRecursoProducto());
            recursoProductoEntity.setNuIdUsuarioElimina(recursoespecieforestal.getNuIdUsuarioElimina());
            resultClassEntity.setData(recursoProductoEntity);
            resultClassEntity.setSuccess(true);
            resultClassEntity.setMessage("Se elimino el Recurso-EspecieForestal correctamente.");
            return resultClassEntity;

        } catch (Exception e) {
            log.error("RecursoEspecieForestal - eliminar " + "Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

    @Override
    public Pageable<List<RecursoEntity>> ListarRecursoValidacion(String numeroActa, Page p) throws Exception {

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Recurso_Listar_Actas");
            sp.registerStoredProcedureParameter("Acta", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);

            SpUtil.enableNullParams(sp);
            sp.setParameter("Acta", numeroActa);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());

            sp.execute();
            return setResultDataListarRecurso(p, sp.getResultList());
        } catch (Exception e) {
            log.error("RecursoRepositoryImpl - listarActa" + "Ocurrió un error :" + e.getMessage());
            return setResultDataListarRecurso(p, null);
        }
    }

    @Override
    public ResultClassEntity ActualizarDisponibilidadActa(RecursoEntity recurso) throws Exception {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        RecursoEntity recursoEntity = new RecursoEntity();
        try {
            /*** Actualizar***/
            StoredProcedureQuery spa = em.createStoredProcedureQuery("almacen.pa_Acta_Disponibilidad");
            spa.registerStoredProcedureParameter("numeroActa", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("disponibilidadActa", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(spa);
            spa.setParameter("numeroActa", recurso.getNumeroActa());
            spa.setParameter("disponibilidadActa", recurso.getDisponibilidadActa());
            spa.execute();
            recursoEntity.setNumeroActa(recurso.getNumeroActa());
            recursoEntity.setDisponibilidadActa(recurso.getDisponibilidadActa());
            resultClassEntity.setData(recursoEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        }catch (Exception e){
            log.error("ActualizarDisponibilidadActa"+ "Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

    @Override
    public Pageable<List<RecursoProductoEntity>> BuscarActaProductos(String numeroActa,String tipo,Page p) throws Exception {

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Buscar_Acta_Productos");
            sp.registerStoredProcedureParameter("numeroActa", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipo", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);

            SpUtil.enableNullParams(sp);
            sp.setParameter("numeroActa", numeroActa);
            sp.setParameter("tipo", tipo);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());

            sp.execute();
            return setResultDataBuscarActaProductos(p, sp.getResultList());
        } catch (Exception e) {
            log.error("RecursoRepositoryImpl - listar" + "Ocurrió un error :" + e.getMessage());
            return setResultDataBuscarActaProductos(p, null);
        }
    }

    private Pageable<List<RecursoProductoEntity>> setResultDataBuscarActaProductos(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<RecursoProductoEntity>> pageable = new Pageable<>(page);
        List<RecursoProductoEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            RecursoProductoEntity reEntity = new RecursoProductoEntity();
            reEntity.setNuIdRecurso((Integer) row[0]);
            reEntity.setNroGuiaTransporteForestal((String) row[1]);
            reEntity.setNuIdAlmacen((Integer) row[2]);
            reEntity.setNumeroActa((String) row[3]);
            reEntity.setTipoIngreso((String) row[4]);
            reEntity.setDisponibilidadActa((String) row[5]);
            reEntity.setNombreAlmacen((String) row[6]);
            reEntity.setTxCantidadProducto((String) row[7]);
            reEntity.setIdEspecie((Integer) row[8]);
            reEntity.setTipoProducto((String) row[9]);
            reEntity.setUnidadMedida((String) row[10]);
            reEntity.setNombreComun((String) row[11]);
            reEntity.setNombreCientifico((String) row[12]);
            reEntity.setMetroCubico((BigDecimal) row[13]);
            reEntity.setTipoSubProducto((String) row[14]);
            reEntity.setTipo((String) row[15]);
            reEntity.setNuIdRecursoProducto((Integer) row[16]);
            reEntity.setCapacidadUnidad((String) row[17]);
            reEntity.setTipoAlmacenamiento((String) row[18]);
            reEntity.setDesctipoProducto((String) row[19]);
            reEntity.setDescUnidadMedida((String) row[20]);
            reEntity.setDescSubProducto((String) row[21]);
            reEntity.setDescTipoAlmacenamiento((String) row[22]);
            items.add(reEntity);
            pageable.setTotalRecords(SpUtil.toLong(row[23]));

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

    @Override
    public ResultClassEntity actualizarRecursoEspecie(List<RecursoProductoEntity> list) {
        ResultClassEntity result = new ResultClassEntity();
        RecursoEntity reEntity = new RecursoEntity();
        try {
            /*** listar especieForestal***/
            for (RecursoProductoEntity pro : list) {

                /*** Registrar Recurso-especieForestal***/
                StoredProcedureQuery srepro = em.createStoredProcedureQuery("almacen.pa_Recurso_Producto_Registrar");
                srepro.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("IdEspecie", Integer.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("CantidadProducto", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("TotalProducto", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("IdUsuarioRegistro", Integer.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("tipoProducto", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("unidadMedida", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("tipo", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("nombreComun", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("nombreCientifico", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("tipoAlmacenamiento", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("capacidadUnidad", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("tipoSubProducto", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("metroCubico", BigDecimal.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("disponibilidadActa", String.class, ParameterMode.IN);
                srepro.registerStoredProcedureParameter("IdRecursoProducto", Integer.class, ParameterMode.INOUT);
                setStoreProcedureEnableNullParameters(srepro);
                srepro.setParameter("IdRecurso", pro.getNuIdRecurso());
                srepro.setParameter("IdEspecie", pro.getIdEspecie());
                srepro.setParameter("CantidadProducto", pro.getTxCantidadProducto());
                srepro.setParameter("TotalProducto", pro.getTxTotalProducto());
                srepro.setParameter("IdUsuarioRegistro", pro.getIdUsuarioRegistro());
                srepro.setParameter("tipoProducto", pro.getTipoProducto());
                srepro.setParameter("unidadMedida", pro.getUnidadMedida());
                srepro.setParameter("IdRecursoProducto", pro.getNuIdRecursoProducto());
                srepro.setParameter("tipo", pro.getType());
                srepro.setParameter("nombreComun", pro.getNombreComun());
                srepro.setParameter("nombreCientifico", pro.getNombreCientifico());
                srepro.setParameter("tipoAlmacenamiento", pro.getTipoAlmacenamiento());
                srepro.setParameter("capacidadUnidad", pro.getCapacidadUnidad());
                srepro.setParameter("tipoSubProducto", pro.getTipoSubProducto());
                srepro.setParameter("metroCubico", pro.getMetroCubico());
                srepro.setParameter("disponibilidadActa", pro.getDisponibilidadActa());

                srepro.execute();
                //Integer idProductosReturn =(Integer)sppr.getOutputParameterValue("@IdRecursoProducto");
                Integer idProductoRecursoReturn = (Integer) srepro.getOutputParameterValue("IdRecursoProducto");
                pro.setNuIdRecursoProducto(idProductoRecursoReturn);
            }

            result.setSuccess(true);
            result.setMessage("Se actualizo el recurso especie correctamente.");

            return result;
        } catch (Exception e) {
            log.error("Recurso - actualizarRecursoEspecie" + "Ocurrió un error :" + e.getMessage());
            result.setSuccess(false);
            result.setMessage("Ocurrió un error.");
            return result;
        }
    }

    @Override
    public Pageable<List<RecursoPersonaEntity>> ListarRecursoPersona(Integer nuIdRecurso,String tipoPersona,
                                                                     Page p) throws Exception {

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Recurso_Persona_Listar");
            sp.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("tipoPersona", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("IdRecurso", nuIdRecurso);
            sp.setParameter("tipoPersona", tipoPersona);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarRecursoPersona(p, sp.getResultList());
        } catch (Exception e) {
            log.error("RecursoEspecieRepositoryImpl - listar" + "Ocurrió un error :" + e.getMessage());
            return setResultDataListarRecursoPersona(p, null);
        }
    }

    private Pageable<List<RecursoPersonaEntity>> setResultDataListarRecursoPersona(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<RecursoPersonaEntity>> pageable = new Pageable<>(page);
        List<RecursoPersonaEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            RecursoPersonaEntity personaEntity = new RecursoPersonaEntity();
            personaEntity.setIdRecursoPersona((Integer) row[0]);
            personaEntity.setNuIdRecurso((Integer) row[1]);
            personaEntity.setTipoDocumento((String) row[2]);
            personaEntity.setNumeroDocumento((String) row[3]);
            personaEntity.setNombresPersona((String) row[4]);
            personaEntity.setFoto((String) row[5]);
            personaEntity.setPlaca((String) row[6]);
            personaEntity.setTipoPersona((String) row[7]);
            personaEntity.setFechaRegistro((Date) row[8]);
            items.add(personaEntity);
            pageable.setTotalRecords(SpUtil.toLong(row[9]));
        }
        pageable.setData(items);
        pageable.setSuccess(true);
        if (items.size() > 0) {
            pageable.setMessage("Se obtuvo data.");
        } else {
            pageable.setMessage("No se encontró data.");
        }
        return pageable;
    }

}
