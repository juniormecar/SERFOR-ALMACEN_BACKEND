package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.ActaRepository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ActaRepositoryImpl extends JdbcDaoSupport implements ActaRepository {

    @Autowired
    @Qualifier("dataSourceAlmacen")
    DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ActaRepositoryImpl.class);

    @Override
    public ResultClassEntity RegistroActa(ActaEntity acta) throws Exception {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        ActaEntity actaEntity = new ActaEntity();
        try {
            /*** Registrar Acta***/
            StoredProcedureQuery spa = em.createStoredProcedureQuery("almacen.pa_Acta_Registrar");
            spa.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("DomicilioIntervenido", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("NombresAutoridadInstructora", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("NombresAdministrado", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("NombresTestigos", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("NumeroDni", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("TipoEspecimen", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("FechaIntervencion", Date.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("Lugar", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("NombreIntervenido", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("Hora", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("DescripcionHechos", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("ConductaInfractora", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("TipoInfraccion", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("SustentoNormativoDecisora", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("AutoridadDecisora", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("MediosPrueba", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("PlazosPresentacion", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("TipoMedidaProvisional", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("Justificacion", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("Especie", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("Unidad", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("CantidadVolumen", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("Observaciones", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("AutoridadInstructora", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("SustentoNormativoInstructora", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("TipoSancion", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("MontoMulta", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("SustentoNormativoMulta", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("IdActa", Integer.class, ParameterMode.INOUT);
            SpUtil.enableNullParams(spa);
            spa.setParameter("IdRecurso", acta.getNuIdRecurso());
            spa.setParameter("DomicilioIntervenido", acta.getDomicilioIntervenido());
            spa.setParameter("NombresAutoridadInstructora", acta.getNombreAutoridadInstructora());
            spa.setParameter("NombresAdministrado", acta.getNombreAdministrado());
            spa.setParameter("NombresTestigos", acta.getNombreTestigo());
            spa.setParameter("NumeroDni", acta.getNumeroDNI());
            spa.setParameter("TipoEspecimen", acta.getTipoEspecimen());
            spa.setParameter("FechaIntervencion", acta.getFechaIntervencion());
            spa.setParameter("Lugar", acta.getLugar());
            spa.setParameter("NombreIntervenido", acta.getNombreIntervenido());
            spa.setParameter("Hora", acta.getHora());
            spa.setParameter("DescripcionHechos", acta.getDescripcionHechos());
            spa.setParameter("ConductaInfractora", acta.getConductaInfractora());
            spa.setParameter("TipoInfraccion", acta.getTipoInfraccion());
            spa.setParameter("SustentoNormativoDecisora", acta.getSustentoNormativoDecisora());
            spa.setParameter("AutoridadDecisora", acta.getAtutoridadDecisora());
            spa.setParameter("MediosPrueba", acta.getMediosPrueba());
            spa.setParameter("PlazosPresentacion", acta.getPlazoPresentacionEncargo());
            spa.setParameter("TipoMedidaProvisional", acta.getMedidaProvisional());
            spa.setParameter("Justificacion", acta.getJustificacion());
            spa.setParameter("Especie", acta.getEspecie());
            spa.setParameter("Unidad", acta.getUnidad());
            spa.setParameter("CantidadVolumen", acta.getCantidadVolumen());
            spa.setParameter("Observaciones", acta.getObservaciones());
            spa.setParameter("AutoridadInstructora", acta.getAutoridadInstructora());
            spa.setParameter("SustentoNormativoInstructora", acta.getSustentoNormativoInstructora());
            spa.setParameter("TipoSancion", acta.getSancion());
            spa.setParameter("MontoMulta", acta.getMontoMulta());
            spa.setParameter("SustentoNormativoMulta", acta.getSustentoNormativoMulta());
            spa.setParameter("IdActa", acta.getIdActa());
            spa.execute();
            Integer idAcataReturn =(Integer)spa.getOutputParameterValue("IdActa");
            actaEntity.setIdActa(idAcataReturn);
            resultClassEntity.setData(actaEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        }catch (Exception e){
            log.error("Almacen - registrarAlmacen"+"Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

    private ActaEntity setResultDataObtener(List<Object[]> dataDb) throws Exception {
        ActaEntity data = new ActaEntity();
        if (!dataDb.isEmpty()) {
            Object[] item = dataDb.get(0);
            data.setIdActa((Integer) item[0]);
            data.setNuIdRecurso((Integer) item[1]);
            data.setFechaIntervencion((Date) item[2]);
            data.setHora((String) item[3]);
            data.setLugar((String) item[4]);
            data.setNombreIntervenido((String) item[5]);
            data.setNumeroDNI((String) item[6]);
            data.setDomicilioIntervenido((String) item[7]);
            data.setDescripcionHechos((String) item[8]);
            data.setConductaInfractora((String) item[9]);
            data.setTipoInfraccion((String) item[10]);
            data.setSancion((String) item[11]);
            data.setMontoMulta((String) item[12]);
            data.setSustentoNormativoMulta((String) item[13]);
            data.setAutoridadInstructora((String) item[14]);
            data.setSustentoNormativoInstructora((String) item[15]);
            data.setAtutoridadDecisora((String) item[16]);
            data.setSustentoNormativoDecisora((String) item[17]);
            data.setMediosPrueba((String) item[18]);
            data.setPlazoPresentacionEncargo((String) item[19]);
            data.setMedidaProvisional((String) item[20]);
            data.setJustificacion((String) item[21]);
            data.setTipoEspecimen((String) item[22]);
            data.setEspecie((String) item[23]);
            data.setUnidad((String) item[24]);
            data.setCantidadVolumen((String) item[25]);
            data.setObservaciones((String) item[26]);
            data.setNombreAutoridadInstructora((String) item[27]);
            data.setNombreAdministrado((String) item[28]);
            data.setNombreTestigo((String) item[29]);
        }
        return data;
    }

    @Override
    public ResultClassEntity<ActaEntity> ListarActa(Integer nuIdRecurso) throws Exception {
        ResultClassEntity<ActaEntity> result = new ResultClassEntity<>();
        try {
            StoredProcedureQuery processStored = em.createStoredProcedureQuery("almacen.pa_Acta_Listar");
            processStored.registerStoredProcedureParameter("IdRecurso", Integer.class, ParameterMode.IN);
            SpUtil.enableNullParams(processStored);
            processStored.setParameter("IdRecurso", nuIdRecurso);
            processStored.execute();
            ActaEntity data = setResultDataObtener(processStored.getResultList());
            result.setData(data);
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            log.error("ActaRepositoryImpl - ListarActa"+"Ocurrió un error :" + e.getMessage());
            result.setSuccess(false);
            result.setMessage("Ocurrió un error.");
            return result;
        }
    }


    @Override
    public ResultClassEntity ActualizarFlag(ActaEntity acta) throws Exception {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        ActaEntity actaEntity = new ActaEntity();
        try {
            /*** Actualizar Flag***/
            StoredProcedureQuery spa = em.createStoredProcedureQuery("almacen.pa_Acta_Actualizar");
            spa.registerStoredProcedureParameter("IdActa", Integer.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("Flag", Boolean.class, ParameterMode.IN);
            SpUtil.enableNullParams(spa);
            spa.setParameter("IdActa", acta.getIdActa());
            spa.setParameter("Flag", acta.getFlag());
            spa.execute();
            actaEntity.setIdActa(acta.getIdActa());
            resultClassEntity.setData(actaEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        }catch (Exception e){
            log.error("ActualizarFlag"+ "Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }


}
