package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.FaunaDetalleRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FaunaDetalleRepositoryImpl extends JdbcDaoSupport implements FaunaDetalleRepository {

    @Autowired
    @Qualifier("dataSourceAlmacen")
    DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(FaunaDetalleRepositoryImpl.class);


    @Override
    public ResultClassEntity RegistroFaunaDetalle(List<FaunaDetalleEntity> lstFaunaDetalle)  {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        FaunaDetalleEntity faunaDetalleEntity = new FaunaDetalleEntity();
        try {

            for(FaunaDetalleEntity faunaDetalle:lstFaunaDetalle ){
                /*** Registrar Fauna Detalle ***/
                StoredProcedureQuery spa = em.createStoredProcedureQuery("almacen.pa_Fauna_Detalle_Registrar");
                spa.registerStoredProcedureParameter("IdRecursoProducto", Integer.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("cantidad", Integer.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("edadEspecie", String.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("estadoEspecie", String.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("observaciones", String.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("IdusuarioRegistro", Integer.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("IdFaunaDetalle", Integer.class, ParameterMode.INOUT);
                SpUtil.enableNullParams(spa);
                spa.setParameter("IdRecursoProducto", faunaDetalle.getNuIdRecursoProducto());
                spa.setParameter("cantidad", faunaDetalle.getTxCantidadProducto());
                spa.setParameter("edadEspecie", faunaDetalle.getEdadEspecie());
                spa.setParameter("estadoEspecie", faunaDetalle.getEstadoEspecie());
                spa.setParameter("observaciones", faunaDetalle.getObservaciones());
                spa.setParameter("IdusuarioRegistro", faunaDetalle.getIdUsuarioRegistro());
                spa.setParameter("IdFaunaDetalle", faunaDetalle.getNuIdFauna());
                spa.execute();
                Integer idFaunaDetalleReturn =(Integer)spa.getOutputParameterValue("IdFaunaDetalle");
                faunaDetalle.setNuIdFauna(idFaunaDetalleReturn);
            }
            resultClassEntity.setData(faunaDetalleEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        }catch (Exception e){
            log.error("FaunaDetalle - registroFaunaDetalle" + "Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }
    @Override
    public Pageable<List<FaunaDetalleEntity>> ListarFaunaDetalle(Integer nuIdRecursoProducto, Page p) throws Exception {
        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Fauna_Detalle_Listar");
            sp.registerStoredProcedureParameter("nuIdRecursoProducto", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("nuIdRecursoProducto", nuIdRecursoProducto);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarFaunDetalle(p, sp.getResultList());
        } catch (Exception e) {
            log.error("FaunaDetalleRepositoryImpl - listar"+"Ocurrió un error :" + e.getMessage());
            return setResultDataListarFaunDetalle(p, null);
        }
    }
    private Pageable<List<FaunaDetalleEntity>> setResultDataListarFaunDetalle(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<FaunaDetalleEntity>> pageable=new Pageable<>(page);
        List<FaunaDetalleEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            FaunaDetalleEntity item = new FaunaDetalleEntity();
            item.setNuIdFauna((Integer) row[0]);
            item.setNuIdRecursoProducto((Integer) row[1]);
            item.setTxCantidadProducto((Integer) row[2]);
            item.setEdadEspecie((String) row[3]);
            item.setEstadoEspecie((String) row[4]);
            item.setObservaciones((String) row[5]);
            items.add(item);
            pageable.setTotalRecords(SpUtil.toLong(row[6]));

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
    public ResultClassEntity EliminarFaunaDetalle (Integer nuIdFaunaDetalle,Integer idUsuarioElimina) throws Exception {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        FaunaDetalleEntity faunaDetalleEntity = new FaunaDetalleEntity();

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Fauna_Detalle_Eliminar");
            sp.registerStoredProcedureParameter("nuIdFaunaDetalle", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("idUsuarioElimina", Integer.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("nuIdFaunaDetalle", nuIdFaunaDetalle);
            sp.setParameter("idUsuarioElimina", idUsuarioElimina);
            sp.execute();
            faunaDetalleEntity.setNuIdFauna(faunaDetalleEntity.getNuIdFauna());
            faunaDetalleEntity.setIdUsuarioElimina(faunaDetalleEntity.getIdUsuarioElimina());
            resultClassEntity.setData(faunaDetalleEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        } catch (Exception e) {
            log.error("Fauna - EliminarDetalle " + "Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }
}
