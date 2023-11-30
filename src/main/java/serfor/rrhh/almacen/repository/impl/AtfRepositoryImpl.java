package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.hibernate.query.procedure.internal.ProcedureParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.AtfRepository;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AtfRepositoryImpl  extends JdbcDaoSupport implements AtfRepository {
    @Autowired
    @Qualifier("dataSourceAlmacen")
    DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(AtfRepositoryImpl.class);


    public void setStoreProcedureEnableNullParameters(StoredProcedureQuery storedProcedureQuery) {
        if (storedProcedureQuery == null || storedProcedureQuery.getParameters() == null)
            return;

        for (Parameter parameter : storedProcedureQuery.getParameters()) {
            ((ProcedureParameterImpl) parameter).enablePassingNulls(true);
        }
    }

    @Override
    public Pageable<List<AtfEntity>> ListarATF(Page p) throws Exception {
        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_ATF_Listar");
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarATF(p, sp.getResultList());
        } catch (Exception e) {
            log.error("ListarATF"+"Ocurrió un error :" + e.getMessage());
            return setResultDataListarATF(p, null);
        }
    }
    private Pageable<List<AtfEntity>> setResultDataListarATF(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<AtfEntity>> pageable=new Pageable<>(page);
        List<AtfEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            AtfEntity atf = new AtfEntity();
            atf.setIdAtf((Integer) row[0]);
            atf.setNombreAtf((String) row[1]);
            atf.setCodigoAtf((String) row[2]);
            items.add(atf);
            pageable.setTotalRecords(SpUtil.toLong(row[3]));

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
    public ResultClassEntity RegistrarATF(AtfEntity atf) {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        AtfEntity atfEntity = new AtfEntity();
        try {
            StoredProcedureQuery spa = em.createStoredProcedureQuery("almacen.pa_ATF_Registrar");
            spa.registerStoredProcedureParameter("nombreATF", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("codigo", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("idATF", Integer.class, ParameterMode.INOUT);
            SpUtil.enableNullParams(spa);
            spa.setParameter("nombreATF", atf.getNombreAtf());
            spa.setParameter("codigo", atf.getCodigoAtf());
            spa.setParameter("idATF", atf.getIdAtf());
            spa.execute();
            Integer AtfReturn = (Integer) spa.getOutputParameterValue("idATF");
            atfEntity.setIdAtf(AtfReturn);
            resultClassEntity.setData(atfEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        } catch (Exception e) {
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

    @Override
    public ResultClassEntity EliminarATF(Integer idAtf) throws Exception {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        AtfEntity atfEntity = new AtfEntity();

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_ATF_Eliminar");
            sp.registerStoredProcedureParameter("idATF", Integer.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("idATF", idAtf);
            sp.execute();
            atfEntity.setIdAtf(atfEntity.getIdAtf());
            resultClassEntity.setData(atfEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        } catch (Exception e) {
            log.error("EliminarATF" + "Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

}
