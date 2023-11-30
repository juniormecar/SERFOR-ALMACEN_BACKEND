package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.EspecieFaunaEntity;
import serfor.rrhh.almacen.entity.EspecieForestalEntity;
import serfor.rrhh.almacen.entity.SpUtil;
import serfor.rrhh.almacen.repository.CoreCentralRepository;
import serfor.rrhh.almacen.entity.*;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CoreCentralRepositoryImpl extends JdbcDaoSupport implements CoreCentralRepository {

    @Autowired
    @Qualifier("dataSourceAlmacen")
    DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(CoreCentralRepositoryImpl.class);

    @Override
    public Pageable<List<EspecieFaunaEntity>> listaPorFiltroEspecieFauna(String nombreEspecie, Page p) throws Exception {
        try {
            StoredProcedureQuery processStored = em.createStoredProcedureQuery("almacen.pa_EspecieFauna_ListarPorFiltro");
            processStored.registerStoredProcedureParameter("nombreEspecie", String.class, ParameterMode.IN);
            processStored.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            processStored.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            SpUtil.enableNullParams(processStored);
            processStored.setParameter("nombreEspecie", nombreEspecie);
            processStored.setParameter("pageNumber", p.getPageNumber());
            processStored.setParameter("pageSize", p.getPageSize());
            processStored.execute();
            return setResultDataListarEspecieFauna(p, processStored.getResultList());
        } catch (Exception e) {
            log.error("listaPorFiltroEspecieFauna" + "Ocurrió un error :" + e.getMessage());
            return setResultDataListarEspecieFauna (p, null);
        }
    }

    private Pageable<List<EspecieFaunaEntity>> setResultDataListarEspecieFauna(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<EspecieFaunaEntity>> pageable = new Pageable<>(page);
        List<EspecieFaunaEntity> result = new ArrayList<>();

        for (Object[] row : dataDb) {
            EspecieFaunaEntity per = new EspecieFaunaEntity();
            per.setIdEspecie((short) row[0]);
            per.setNombreCientifico((String) row[1]);
            per.setNombreComun((String) row[2]);
            per.setFamilia((String) row[3]);
            result.add(per);
            pageable.setTotalRecords(SpUtil.toLong(row[4]));
        }
        pageable.setData(result);
        pageable.setSuccess(true);
        //pageable.setTotalRecords(Long.valueOf(items.size()));
        if (result.size() > 0) {
            pageable.setMessage("Se obtuvo data.");
        } else {
            pageable.setMessage("No se encontró data.");
        }
        return pageable;
    }

    @Override
    public Pageable<List<EspecieForestalEntity>> listaPorFiltroEspecieForestal(Integer idEspecie,String nombreComun,
                                                                     String nombreCientifico ,String autor,
                                                                     String familia,Page p) throws Exception {
        Pageable<List<EspecieForestalEntity>> pageable=new Pageable<>(p);
        List<EspecieForestalEntity> result = new ArrayList<EspecieForestalEntity>();
        try {
            StoredProcedureQuery processStored = em.createStoredProcedureQuery("almacen.pa_EspecieForestal_ListarPorFiltro");
            processStored.registerStoredProcedureParameter("P_NU_IDESPECIE", Integer.class, ParameterMode.IN);
            processStored.registerStoredProcedureParameter("P_DESC_COMUN", String.class, ParameterMode.IN);
            processStored.registerStoredProcedureParameter("P_DESC_CIENTIFICO", String.class, ParameterMode.IN);
            processStored.registerStoredProcedureParameter("p_AUTOR", String.class, ParameterMode.IN);
            processStored.registerStoredProcedureParameter("p_FAMILIA", String.class, ParameterMode.IN);
            processStored.registerStoredProcedureParameter("P_PAGENUM", Long.class, ParameterMode.IN);
            processStored.registerStoredProcedureParameter("P_PAGESIZE", Long.class, ParameterMode.IN);
            SpUtil.enableNullParams(processStored);
            processStored.setParameter("P_NU_IDESPECIE", idEspecie);
            processStored.setParameter("P_DESC_COMUN", nombreComun);
            processStored.setParameter("P_DESC_CIENTIFICO", nombreCientifico);
            processStored.setParameter("p_AUTOR", autor);
            processStored.setParameter("p_FAMILIA", familia);
            processStored.setParameter("P_PAGENUM", p.getPageNumber());
            processStored.setParameter("P_PAGESIZE", p.getPageSize());
            processStored.execute();

            List<Object[]> spResult = processStored.getResultList();
            if (spResult.size() >= 1) {
                for (Object[] row : spResult) {
                    EspecieForestalEntity per = new EspecieForestalEntity();
                    per.setIdEspecie((short) row[0]);
                    per.setNombreComun((String) row[1]);
                    per.setNombreCientifico((String) row[2]);
                    per.setAutor((String) row[3]);
                    per.setFamilia((String) row[4]);
                    result.add(per);
                    pageable.setTotalRecords(SpUtil.toLong(row[5]));
                }
            }
            pageable.setData(result);
            pageable.setSuccess(true);
            //pageable.setTotalRecords(Long.valueOf(items.size()));
            if(result.size()>0){
                pageable.setMessage("Se obtuvo data.");
            }else{
                pageable.setMessage("No se encontró data.");
            }
            return pageable;
        } catch (Exception e) {
            log.error("CoreCentralRepositoryImpl - listaPorFiltroEspecieForestal",
                    "Ocurrió un error :" + e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
    }

}
