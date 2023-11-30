package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.AlmacenRepository;
import serfor.rrhh.almacen.repository.CubicacionRepository;

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
public class CubicacionRepositoryImpl extends JdbcDaoSupport implements CubicacionRepository {

    @Autowired
    @Qualifier("dataSourceAlmacen")
    DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(CubicacionRepositoryImpl.class);


    @Override
    public ResultClassEntity RegistroCubicacion(List<CubicacionEntity> lstCubicacion) {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        CubicacionEntity cubicacionEntity = new CubicacionEntity();
        try {

            for(CubicacionEntity cubicacion:lstCubicacion ){
                /*** Registrar Cubicacion***/
                StoredProcedureQuery spa = em.createStoredProcedureQuery("almacen.pa_Recurso_Producto_Cubicacion_Registrar");
                spa.registerStoredProcedureParameter("Cantidad", Integer.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("Espesor", BigDecimal.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("Ancho", BigDecimal.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("Largo", BigDecimal.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("DiametroPromedio", BigDecimal.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("VolumenPT", BigDecimal.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("VolumenM3", BigDecimal.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("IdUsuarioRegistro", Integer.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("IdRecursoProducto", Integer.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("TotalRecursoProducto", Integer.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("longitud", BigDecimal.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("totalVolumenM3", BigDecimal.class, ParameterMode.IN);
                spa.registerStoredProcedureParameter("IdRecursoProductoCubicacion", Integer.class, ParameterMode.INOUT);
                SpUtil.enableNullParams(spa);
                spa.setParameter("Cantidad", cubicacion.getCantidad());
                spa.setParameter("Espesor", cubicacion.getEspesor());
                spa.setParameter("Ancho", cubicacion.getAncho());
                spa.setParameter("Largo", cubicacion.getLargo());
                spa.setParameter("DiametroPromedio", cubicacion.getDiametroPromedio());
                spa.setParameter("VolumenPT", cubicacion.getVolumenPT());
                spa.setParameter("VolumenM3", cubicacion.getVolumenM3());
                spa.setParameter("IdUsuarioRegistro", cubicacion.getIdUsuarioRegistro());
                spa.setParameter("IdRecursoProducto", cubicacion.getNuIdRecursoProducto());
                spa.setParameter("TotalRecursoProducto", cubicacion.getTotal());
                spa.setParameter("longitud", cubicacion.getLongitud());
                spa.setParameter("totalVolumenM3", cubicacion.getTotalVolumenM3());
                spa.setParameter("IdRecursoProductoCubicacion", cubicacion.getIdRecurProCubicacion());
                spa.execute();
                Integer idRecursoProductoCubicacionReturn =(Integer)spa.getOutputParameterValue("IdRecursoProductoCubicacion");
                cubicacion.setIdRecurProCubicacion(idRecursoProductoCubicacionReturn);
            }
            resultClassEntity.setData(cubicacionEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        }catch (Exception e){
            log.error("Cubicación - registrarCubicación" + "Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }


    @Override
    public Pageable<List<CubicacionEntity>> ListarCubicacion(Integer nuIdRecursoProducto, Page p) throws Exception {
        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Recurso_Producto_Cubicacion_Listar");
            sp.registerStoredProcedureParameter("IdRecursoProducto", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("IdRecursoProducto", nuIdRecursoProducto);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarCubicacion(p, sp.getResultList());
        } catch (Exception e) {
            log.error("CubicacionRepositoryImpl - listar"+"Ocurrió un error :" + e.getMessage());
            return setResultDataListarCubicacion(p, null);
        }
    }


    @Override
    public List<CubicacionEntity> ListarCubicacionPDF(Integer nuIdRecursoProducto, Page p) throws Exception {
        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Recurso_Producto_Cubicacion_Listar");
            sp.registerStoredProcedureParameter("IdRecursoProducto", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("IdRecursoProducto", nuIdRecursoProducto);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarCubicacionPDF(p, sp.getResultList());
        } catch (Exception e) {
            log.error("CubicacionRepositoryImpl - listar"+"Ocurrió un error :" + e.getMessage());
            return setResultDataListarCubicacionPDF(p, null);
        }
    }
    private List<CubicacionEntity> setResultDataListarCubicacionPDF(Page page, List<Object[]> dataDb) throws Exception {

        List<CubicacionEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            CubicacionEntity item = new CubicacionEntity();
            item.setCantidad((Integer) row[0]);
            item.setEspesor((BigDecimal) row[1]);
            item.setAncho((BigDecimal) row[2]);
            item.setLargo((BigDecimal) row[3]);
            item.setDiametroPromedio((BigDecimal) row[4]);
            item.setVolumenPT((BigDecimal) row[5]);
            item.setVolumenM3((BigDecimal) row[6]);
            item.setEstado((String) row[7]);
            item.setIdRecurProCubicacion((Integer) row[8]);
            item.setNuIdRecursoProducto((Integer) row[9]);
            item.setLongitud((BigDecimal) row[10]);
            items.add(item);

        }
        return items;
    }


    private Pageable<List<CubicacionEntity>> setResultDataListarCubicacion(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<CubicacionEntity>> pageable=new Pageable<>(page);
        List<CubicacionEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            CubicacionEntity item = new CubicacionEntity();
            item.setCantidad((Integer) row[0]);
            item.setEspesor((BigDecimal) row[1]);
            item.setAncho((BigDecimal) row[2]);
            item.setLargo((BigDecimal) row[3]);
            item.setDiametroPromedio((BigDecimal) row[4]);
            item.setVolumenPT((BigDecimal) row[5]);
            item.setVolumenM3((BigDecimal) row[6]);
            item.setEstado((String) row[7]);
            item.setIdRecurProCubicacion((Integer) row[8]);
            item.setNuIdRecursoProducto((Integer) row[9]);
            item.setLongitud((BigDecimal) row[10]);
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
    public ResultClassEntity EliminarCubicacion (Integer idRecurProCubicacion,Integer idUsuarioElimina) throws Exception {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        CubicacionEntity cubicacionEntity = new CubicacionEntity();

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Recurso_Producto_Cubicacion_Eliminar");
            sp.registerStoredProcedureParameter("IdRecursoProductoCubicacion", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("idUsuarioElimina", Integer.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("IdRecursoProductoCubicacion", idRecurProCubicacion);
            sp.setParameter("idUsuarioElimina", idUsuarioElimina);
            sp.execute();
            cubicacionEntity.setIdRecurProCubicacion(cubicacionEntity.getIdRecurProCubicacion());
            cubicacionEntity.setIdUsuarioElimina(cubicacionEntity.getIdUsuarioElimina());
            resultClassEntity.setData(cubicacionEntity);
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
