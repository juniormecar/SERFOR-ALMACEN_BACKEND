package serfor.rrhh.almacen.repository.impl;


import org.apache.logging.log4j.LogManager;
import org.hibernate.query.procedure.internal.ProcedureParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.UbigeoRepository;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class UbigeoRepositoryImpl extends JdbcDaoSupport implements UbigeoRepository {
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
    public List<DepartamentoEntity> ListarDepartamento() throws Exception {
        List<DepartamentoEntity> result = new ArrayList<DepartamentoEntity>();
        try {
            StoredProcedureQuery processStored = em.createStoredProcedureQuery("almacen.pa_Departamento_Listar");
            //processStored.registerStoredProcedureParameter("prefijo", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(processStored);
            //processStored.setParameter("prefijo", prefijo);
            processStored.execute();

            List<Object[]> spResult = processStored.getResultList();
            if (spResult.size() >= 1) {
                for (Object[] row : spResult) {
                    DepartamentoEntity dep = new DepartamentoEntity();
                    dep.setNombreDepartamento((String) row[0]);
                    dep.setCodigoDepartamento((String) row[1]);
                    dep.setCodigoDepartamentoINEI((String) row[2]);
                    dep.setCodigoDepartamentoRENIEC((String) row[3]);
                    dep.setCodigoDepartamentoSUNAT((String) row[4]);
                    dep.setIdDepartamento((Integer) row[5]);
                    result.add(dep);
                }
            } else {
                return null;
            }
            return result;
        } catch (Exception e) {
            log.error("UbigeoRepositoryImpl - ListarDepartamento",
                    "Ocurrió un error :" + e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
    }


    @Override
    public List<ProvinciaEntity> ListarProvincia(String codigoDepartamento) throws Exception {
        List<ProvinciaEntity> result = new ArrayList<ProvinciaEntity>();
        try {
            StoredProcedureQuery processStored = em.createStoredProcedureQuery("almacen.pa_Provincia_Listar");
            processStored.registerStoredProcedureParameter("codigoDepartamento", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(processStored);
            processStored.setParameter("codigoDepartamento", codigoDepartamento);
            processStored.execute();

            List<Object[]> spResult = processStored.getResultList();
            if (spResult.size() >= 1) {
                for (Object[] row : spResult) {
                    ProvinciaEntity prov = new ProvinciaEntity();
                    prov.setIdProvincia((Integer) row[0]);
                    prov.setNombreProvincia((String) row[1]);
                    prov.setCodigoProvincia((String) row[2]);
                    prov.setCodigoDepartamento((String) row[3]);
                    prov.setCodigoProvinciaINEI((String) row[4]);
                    result.add(prov);
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

 @Override
    public List<DistritoEntity> ListarDistrito(String codigoProvincia) throws Exception {
        List<DistritoEntity> result = new ArrayList<DistritoEntity>();
        try {
            StoredProcedureQuery processStored = em.createStoredProcedureQuery("almacen.pa_Distrito_Listar");
            processStored.registerStoredProcedureParameter("codigoProvincia", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(processStored);
            processStored.setParameter("codigoProvincia", codigoProvincia);
            processStored.execute();

            List<Object[]> spResult = processStored.getResultList();
            if (spResult.size() >= 1) {
                for (Object[] row : spResult) {
                    DistritoEntity dis = new DistritoEntity();
                    dis.setIdDistrito((Integer) row[0]);
                    dis.setNombreDistrito((String) row[1]);
                    dis.setCodigoDistrito((String) row[2]);
                   dis.setCodigoProvincia((String) row[3]);
                    dis.setCodigoDistritoINEI((String) row[4]);

                    result.add(dis);
                }
            } else {
                return null;
            }
            return result;
        } catch (Exception e) {
            log.error("UbigeoRepositoryImpl - ListarDistrito",
                    "Ocurrió un error :" + e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
    }

}
