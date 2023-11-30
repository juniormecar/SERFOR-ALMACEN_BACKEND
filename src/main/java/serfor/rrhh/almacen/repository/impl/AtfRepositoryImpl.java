package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.hibernate.query.procedure.internal.ProcedureParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.AtfEntity;
import serfor.rrhh.almacen.entity.DepartamentoEntity;
import serfor.rrhh.almacen.entity.SpUtil;
import serfor.rrhh.almacen.repository.AtfRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
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
    public List<AtfEntity> ListarATF() throws Exception {
        List<AtfEntity> result = new ArrayList<AtfEntity>();
        try {
            StoredProcedureQuery processStored = em.createStoredProcedureQuery("almacen.pa_ATF_Listar");
            SpUtil.enableNullParams(processStored);
            processStored.execute();

            List<Object[]> spResult = processStored.getResultList();
            if (spResult.size() >= 1) {
                for (Object[] row : spResult) {
                    AtfEntity atf = new AtfEntity();
                    atf.setIdAtf((Integer) row[0]);
                    atf.setNombreAtf((String) row[1]);
                    atf.setCodigoAtf((String) row[2]);
                    result.add(atf);
                }
            } else {
                return null;
            }
            return result;
        } catch (Exception e) {
            log.error("AtfRepositoryImpl - ListarATF",
                    "Ocurrió un error :" + e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
    }
}
