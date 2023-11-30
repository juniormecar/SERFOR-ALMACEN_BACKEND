package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.ProvinciaEntity;
import serfor.rrhh.almacen.entity.PuestoControlEntity;
import serfor.rrhh.almacen.entity.SpUtil;
import serfor.rrhh.almacen.repository.PuestoControlRepository;

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
public class PuestoControlRepositoryImpl extends JdbcDaoSupport implements PuestoControlRepository {

    @Autowired
    @Qualifier("dataSourceAlmacen")
    DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(PuestoControlRepositoryImpl.class);


    @Override
    public List<PuestoControlEntity> ListarPuestoControl(Integer idAtf) throws Exception {
        List<PuestoControlEntity> result = new ArrayList<PuestoControlEntity>();
        try {
            StoredProcedureQuery processStored = em.createStoredProcedureQuery("almacen.pa_Puesto_Control_Listar");
            processStored.registerStoredProcedureParameter("idAtf", Integer.class, ParameterMode.IN);
            SpUtil.enableNullParams(processStored);
            processStored.setParameter("idAtf", idAtf);
            processStored.execute();

            List<Object[]> spResult = processStored.getResultList();
            if (spResult.size() >= 1) {
                for (Object[] row : spResult) {
                    PuestoControlEntity pc = new PuestoControlEntity();
                    pc.setIdPuestoControl((Integer) row[0]);
                    pc.setNombrePuestoControl((String) row[1]);
                    pc.setControlObligatorio((String) row[2]);
                    pc.setDepartamento((String) row[3]);
                    pc.setProvincia((String) row[4]);
                    pc.setDistrito((String) row[5]);
                    pc.setCoordenadasNorte((String) row[6]);
                    pc.setCoordenadasEste((String) row[7]);
                    pc.setZonaUTM((String) row[8]);
                    pc.setIdAtf((Integer) row[9]);
                    result.add(pc);
                }
            } else {
                return null;
            }
            return result;
        } catch (Exception e) {
            log.error("UbigeoRepositoryImpl - ListarProvincia",
                    "Ocurrió un error :" + e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
    }
}
