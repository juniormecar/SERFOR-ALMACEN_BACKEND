package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.hibernate.query.procedure.internal.ProcedureParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.AlmacenRepository;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class AlmacenRepositoryImpl extends JdbcDaoSupport implements AlmacenRepository {

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

    @Override
    public Pageable<List<AlmacenEntity>> ListarAlmacen( String txUbigeo,String txNombreAlmacen,
                                                        String txTipoAlmacen,String txPuestoControl, String txNumeroATF,
                                                        String txNumeroDocumento, Page p) throws Exception {
        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Almacen_Listar");
            sp.registerStoredProcedureParameter("ubigeo", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("NombreAlmacen", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("TipoAlmacen", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("PuestoControl", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("Atf", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("NumeroDocumento", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("ubigeo", txUbigeo);
            sp.setParameter("NombreAlmacen", txNombreAlmacen);
            sp.setParameter("TipoAlmacen", txTipoAlmacen);
            sp.setParameter("PuestoControl", txPuestoControl);
            sp.setParameter("Atf", txNumeroATF);
            sp.setParameter("NumeroDocumento", txNumeroDocumento);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarAlmacen(p, sp.getResultList());
        } catch (Exception e) {
            log.error("AlmacenRepositoryImpl - listar"+"Ocurrió un error :" + e.getMessage());
            return setResultDataListarAlmacen(p, null);
        }
    }

    @Override
    public AlmacenEntity getAlmacen(Integer idAlmacen) throws Exception {
        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Almacen_obtener");
            sp.registerStoredProcedureParameter("nuIdAlmacen", Integer.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("nuIdAlmacen", idAlmacen);
            sp.execute();
            return setResultDataListarAlmacen(sp.getResultList());
        } catch (Exception e) {
            log.error("AlmacenRepositoryImpl - listar"+"Ocurrió un error :" + e.getMessage());
            return setResultDataListarAlmacen(null);
        }
    }

    private Pageable<List<AlmacenEntity>> setResultDataListarAlmacen(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<AlmacenEntity>> pageable=new Pageable<>(page);
        List<AlmacenEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            AlmacenEntity item = new AlmacenEntity();
            item.setNuIdAlmacen((Integer) row[0]);
            item.setTxUbigeo((String) row[1]);
            item.setTxNombreAlmacen((String) row[2]);
            item.setTxTipoAlmacen((String) row[3]);
            item.setTxTipoDocumento((String) row[4]);
            item.setTxNumeroDocumento((String) row[5]);
            item.setTxNombresEncargado((String) row[6]);
            item.setNuCapacidadAlmacen((BigDecimal) row[7]);
            item.setNuIdUsuarioModificacion((Integer) row[8]);
            item.setFeFechaModificacion((Date) row[9]);
            item.setTxEstado((String) row[10]);
            item.setNuIdUsuarioElimina((Integer) row[11]);
            item.setFeFechaElimina((Date) row[12]);
            item.setNuIdUsuarioRegistro((Integer) row[13]);
            item.setFeFechaRegistro((Date) row[14]);
            item.setTxPuestoControl((String) row[15]);
            item.setTxNumeroATF((String) row[16]);
            item.setDescrATF((String) row[18]);
            item.setDescrPuestoControl((String) row[19]);
            item.setDescrTipoAlmacen((String) row[20]);
            item.setFoto((String) row[21]);
            item.setCantidadResponsables((Integer) row[22]);
            item.setDireccionAlmacen((String) row[23]);
            item.setCapacidadNoMaderable((BigDecimal) row[24]);
            item.setCapacidadFauna((BigDecimal) row[25]);
            item.setCapacidadMaderable((BigDecimal) row[26]);
            item.setDepartamento((String) row[27]);
            item.setProvincia((String) row[28]);
            item.setDistrito((String) row[29]);
            items.add(item);
            pageable.setTotalRecords(SpUtil.toLong(row[17]));

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

    private AlmacenEntity setResultDataListarAlmacen(List<Object[]> dataDb) throws Exception {
        AlmacenEntity item = new AlmacenEntity();
        for (Object[] row : dataDb) {
            item.setNuIdAlmacen((Integer) row[0]);
            item.setTxUbigeo((String) row[1]);
            item.setTxNombreAlmacen((String) row[2]);
            item.setTxTipoAlmacen((String) row[3]);
            item.setTxTipoDocumento((String) row[4]);
            item.setTxNumeroDocumento((String) row[5]);
            item.setTxNombresEncargado((String) row[6]);
            item.setNuCapacidadAlmacen((BigDecimal) row[7]);
            item.setNuIdUsuarioModificacion((Integer) row[8]);
            item.setFeFechaModificacion((Date) row[9]);
            item.setTxEstado((String) row[10]);
            item.setNuIdUsuarioElimina((Integer) row[11]);
            item.setFeFechaElimina((Date) row[12]);
            item.setNuIdUsuarioRegistro((Integer) row[13]);
            item.setFeFechaRegistro((Date) row[14]);
            item.setTxPuestoControl((String) row[15]);
            item.setTxNumeroATF((String) row[16]);
            item.setDescrATF((String) row[17]);
            item.setDescrPuestoControl((String) row[18]);
            item.setDescrTipoAlmacen((String) row[19]);
            item.setFoto((String) row[20]);
            item.setCantidadResponsables((Integer) row[21]);
            item.setDireccionAlmacen((String) row[22]);
            item.setCapacidadNoMaderable((BigDecimal) row[23]);
            item.setCapacidadFauna((BigDecimal) row[24]);
            item.setCapacidadMaderable((BigDecimal) row[25]);
            item.setDepartamento((String) row[26]);
            item.setProvincia((String) row[27]);
            item.setDistrito((String) row[28]);
        }

        return item;
    }

    @Override
    public ResultClassEntity EliminarAlmacen(Integer nuIdAlmacen,Integer nuIdUsuarioElimina) throws Exception {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        AlmacenEntity almacenEntity = new AlmacenEntity();

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Almacen_Eliminar");
            sp.registerStoredProcedureParameter("IdAlmacen", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("IdUsuarioElimina", Integer.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("IdAlmacen", nuIdAlmacen);
            sp.setParameter("IdUsuarioElimina", nuIdUsuarioElimina);
            sp.execute();
            almacenEntity.setNuIdAlmacen(almacenEntity.getNuIdAlmacen());
            almacenEntity.setNuIdUsuarioElimina(almacenEntity.getNuIdUsuarioElimina());
            resultClassEntity.setData(almacenEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        } catch (Exception e) {
            log.error("Almacen - listarRecurso" + "Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

    @Override
    public ResultClassEntity RegistroAlmacen(AlmacenEntity almacen){
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        AlmacenEntity almacenEntity = new AlmacenEntity();
        try {
            /*** Registrar Almacen***/
            StoredProcedureQuery spa = em.createStoredProcedureQuery("almacen.pa_Almacen_Registrar");
            spa.registerStoredProcedureParameter("ubigeo", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("NombreAlmacen", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("TipoAlmacen", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("TipoDocumento", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("NumeroDocumento", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("NombresEncargado", String.class, ParameterMode.IN);
            //spa.registerStoredProcedureParameter("NuCapacidadAlmacen", BigDecimal.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("IdUsuarioRegistro", Integer.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("PuestoControl", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("Atf", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("foto", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("direccionAlmacen", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("capacidadNoMaderable", BigDecimal.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("capacidadFauna", BigDecimal.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("capacidadMaderable", BigDecimal.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("idAlmacen", Integer.class, ParameterMode.INOUT);
            SpUtil.enableNullParams(spa);
            spa.setParameter("ubigeo", almacen.getTxUbigeo());
            spa.setParameter("NombreAlmacen", almacen.getTxNombreAlmacen());
            spa.setParameter("TipoAlmacen", almacen.getTxTipoAlmacen());
            spa.setParameter("TipoDocumento", almacen.getTxTipoDocumento());
            spa.setParameter("NumeroDocumento", almacen.getTxNumeroDocumento());
            spa.setParameter("NombresEncargado", almacen.getTxNombresEncargado());
            //spa.setParameter("NuCapacidadAlmacen", almacen.getNuCapacidadAlmacen());
            spa.setParameter("IdUsuarioRegistro", almacen.getNuIdUsuarioRegistro());
            spa.setParameter("PuestoControl", almacen.getTxPuestoControl());
            spa.setParameter("Atf", almacen.getTxNumeroATF());
            spa.setParameter("foto", almacen.getFoto());
            spa.setParameter("direccionAlmacen", almacen.getDireccionAlmacen());
            spa.setParameter("capacidadNoMaderable", almacen.getCapacidadNoMaderable());
            spa.setParameter("capacidadFauna", almacen.getCapacidadFauna());
            spa.setParameter("capacidadMaderable", almacen.getCapacidadMaderable());
            spa.setParameter("idAlmacen", almacen.getNuIdAlmacen());
            spa.execute();
            Integer idAlmacenReturn =(Integer)spa.getOutputParameterValue("idAlmacen");
            almacenEntity.setNuIdAlmacen(idAlmacenReturn);
            /*** Listar Almacen Responsable***/
            for (AlmacenResponsableEntity res : almacen.getLstAlmacenResponsable()) {
                /*** Registrar Almacen Responsable***/
                StoredProcedureQuery almares = em.createStoredProcedureQuery("almacen.pa_Almacen_Responsable_Registrar");
                almares.registerStoredProcedureParameter("TipoDocumento", String.class, ParameterMode.IN);
                almares.registerStoredProcedureParameter("NumeroDocumento", String.class, ParameterMode.IN);
                almares.registerStoredProcedureParameter("NombresResponsable", String.class, ParameterMode.IN);
                almares.registerStoredProcedureParameter("idAlmacen", Integer.class, ParameterMode.IN);
                almares.registerStoredProcedureParameter("IdUsuarioRegistro", Integer.class, ParameterMode.IN);
                almares.registerStoredProcedureParameter("idAlmacenResponsable", Integer.class, ParameterMode.INOUT);
                SpUtil.enableNullParams(almares);
                almares.setParameter("TipoDocumento", res.getTipoDocumento());
                almares.setParameter("NumeroDocumento", res.getNumeroDocumento());
                almares.setParameter("NombresResponsable", res.getNombresResponsable());
                almares.setParameter("idAlmacen", almacenEntity.getNuIdAlmacen());
                almares.setParameter("IdUsuarioRegistro", res.getIdUsuarioRegistro());
                almares.setParameter("idAlmacenResponsable", res.getIdAlmacenResponsable());
                almares.execute();
                Integer idAlmacenResponsableReturn = (Integer) almares.getOutputParameterValue("idAlmacenResponsable");
                res.setIdAlmacenResponsable(idAlmacenResponsableReturn);
            }
            almacenEntity.setNuIdAlmacen(idAlmacenReturn);
            resultClassEntity.setData(almacenEntity);
            resultClassEntity.setSuccess(true);
            resultClassEntity.setMessage("Se registró el almacen correctamente.");
            return resultClassEntity;

        }catch (Exception e){
            log.error("Almacen - registrarAlmacen"+"Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

    @Override
    public Pageable<List<AlmacenResponsableEntity>> ListarAlmacenResponsable(Integer nuIdAlmacen,String numeroDocumento
                                                                             ,Page p) throws Exception {
        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Almacen_Responsable_Listar");
            sp.registerStoredProcedureParameter("idAlmacen", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("NumeroDocumento", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("idAlmacen", nuIdAlmacen);
            sp.setParameter("NumeroDocumento", numeroDocumento);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarAlmacenResponsable(p, sp.getResultList());
        } catch (Exception e) {
            log.error("AlmacenRepositoryImpl - listarAlmacenResponsable"+"Ocurrió un error :" + e.getMessage());
            return setResultDataListarAlmacenResponsable(p, null);
        }
    }

    private Pageable<List<AlmacenResponsableEntity>> setResultDataListarAlmacenResponsable(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<AlmacenResponsableEntity>> pageable=new Pageable<>(page);
        List<AlmacenResponsableEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            AlmacenResponsableEntity item = new AlmacenResponsableEntity();
            item.setIdAlmacenResponsable((Integer) row[0]);
            item.setNuIdAlmacen((Integer) row[1]);
            item.setNombreAlmacen((String) row[2]);
            item.setTipoAlmacen((String) row[3]);
            item.setTipoDocumento((String) row[4]);
            item.setNumeroDocumento((String) row[5]);
            item.setNombresResponsable((String) row[6]);
            item.setIdUsuarioRegistro((Integer) row[7]);
            item.setFechaRegistro((Date) row[8]);
            item.setIdUsuarioModificacion((Integer) row[9]);
            item.setFechaModificacion((Date) row[10]);
            item.setIdUsuarioElimina((Integer) row[11]);
            item.setFechaElimina((Date) row[12]);
            item.setEstado((String) row[13]);
            items.add(item);
            pageable.setTotalRecords(SpUtil.toLong(row[14]));

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
    public ResultClassEntity EliminarAlmacenResponsable(Integer idAlmacenResponsable,Integer idUsuarioElimina) throws Exception {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        AlmacenResponsableEntity almacenResponsableEntity = new AlmacenResponsableEntity();

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Almacen_Responsable_Eliminar");
            sp.registerStoredProcedureParameter("idAlmacenResponsable", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("IdUsuarioElimina", Integer.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("idAlmacenResponsable", idAlmacenResponsable);
            sp.setParameter("IdUsuarioElimina", idUsuarioElimina);
            sp.execute();
            almacenResponsableEntity.setIdAlmacenResponsable(almacenResponsableEntity.getIdAlmacenResponsable());
            almacenResponsableEntity.setIdUsuarioElimina(almacenResponsableEntity.getIdUsuarioElimina());
            resultClassEntity.setData(almacenResponsableEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        } catch (Exception e) {
            log.error("Almacen - EliminarAlmacenResponsable " + "Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }


}
