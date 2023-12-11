package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.PuestoControlRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.sql.DataSource;
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
    public Pageable<List<PuestoControlEntity>> ListarBandejaPuestoControl(Integer idAtf, Page p) throws Exception {
        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Puesto_Control_Bandeja_Listar");
            sp.registerStoredProcedureParameter("idAtf", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("idAtf", idAtf);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarPuestoControl(p, sp.getResultList());
        } catch (Exception e) {
            log.error("ListarATF"+"Ocurrió un error :" + e.getMessage());
            return setResultDataListarPuestoControl(p, null);
        }
    }

    private Pageable<List<PuestoControlEntity>> setResultDataListarPuestoControl(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<PuestoControlEntity>> pageable=new Pageable<>(page);
        List<PuestoControlEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
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
            pc.setNombreATF((String) row[10]);
            pc.setDireccion((String) row[11]);
            items.add(pc);
            pageable.setTotalRecords(SpUtil.toLong(row[12]));

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
                    pc.setNombreATF((String) row[10]);
                    pc.setDireccion((String) row[11]);
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

    @Override
    public ResultClassEntity registrarPuestoControl(PuestoControlEntity puestoControl) {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        PuestoControlEntity puestoControlEntity = new PuestoControlEntity();
        try {
            StoredProcedureQuery spa = em.createStoredProcedureQuery("almacen.pa_Puesto_Control_Registrar");
            spa.registerStoredProcedureParameter("nombrePuestoControl", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("controlObligatorio", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("departamento", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("provincia", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("distrito", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("coordenadasNorte", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("coordenadasEste", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("zonaUTM", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("idATF", Integer.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("direccion", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("idPuestoControl", Integer.class, ParameterMode.INOUT);
            SpUtil.enableNullParams(spa);
            spa.setParameter("nombrePuestoControl", puestoControl.getNombrePuestoControl());
            spa.setParameter("controlObligatorio", puestoControl.getControlObligatorio());
            spa.setParameter("departamento", puestoControl.getDepartamento());
            spa.setParameter("provincia", puestoControl.getProvincia());
            spa.setParameter("distrito", puestoControl.getDistrito());
            spa.setParameter("coordenadasNorte", puestoControl.getCoordenadasNorte());
            spa.setParameter("coordenadasEste", puestoControl.getCoordenadasEste());
            spa.setParameter("zonaUTM", puestoControl.getZonaUTM());
            spa.setParameter("idATF", puestoControl.getIdAtf());
            spa.setParameter("direccion", puestoControl.getDireccion());
            spa.setParameter("idPuestoControl", puestoControl.getIdPuestoControl());
            spa.execute();
            Integer PuestoControlReturn = (Integer) spa.getOutputParameterValue("idPuestoControl");
            puestoControlEntity.setIdPuestoControl(PuestoControlReturn);
            resultClassEntity.setData(puestoControlEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        } catch (Exception e) {
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

    @Override
    public ResultClassEntity EliminarPuestoControl(Integer idPuestoControl) throws Exception {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        PuestoControlEntity puestoControlEntity = new PuestoControlEntity();

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Puesto_Control_Eliminar");
            sp.registerStoredProcedureParameter("idPuestoControl", Integer.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("idPuestoControl", idPuestoControl);
            sp.execute();
            puestoControlEntity.setIdPuestoControl(puestoControlEntity.getIdPuestoControl());
            resultClassEntity.setData(puestoControlEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        } catch (Exception e) {
            log.error("Almacen - listarRecurso" + "Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

}
