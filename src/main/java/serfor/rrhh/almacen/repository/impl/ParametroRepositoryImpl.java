package serfor.rrhh.almacen.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.hibernate.query.procedure.internal.ProcedureParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.ParametroRepository;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ParametroRepositoryImpl extends JdbcDaoSupport implements ParametroRepository {
    @Autowired
    @Qualifier("dataSourceAlmacen")
    DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ParametroRepositoryImpl.class);

    public void setStoreProcedureEnableNullParameters(StoredProcedureQuery storedProcedureQuery) {
        if (storedProcedureQuery == null || storedProcedureQuery.getParameters() == null)
            return;

        for (Parameter parameter : storedProcedureQuery.getParameters()) {
            ((ProcedureParameterImpl) parameter).enablePassingNulls(true);
        }
    }
    @Override
    public Pageable<List<ParametroEntity>> listaBandejaParametro(String prefijo,Page p) throws Exception {
        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Parametro_ListarPorPrefijo_Bandeja");
            sp.registerStoredProcedureParameter("prefijo", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageNumber", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("pageSize", Long.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortField", String.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("sortType", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("prefijo", prefijo);
            sp.setParameter("pageNumber", p.getPageNumber());
            sp.setParameter("pageSize", p.getPageSize());
            sp.setParameter("sortField", p.getSortField());
            sp.setParameter("sortType", p.getSortType());
            sp.execute();
            return setResultDataListarParametro(p, sp.getResultList());
        } catch (Exception e) {
            log.error("listarTipoParametro" + "Ocurrió un error :" + e.getMessage());
            return setResultDataListarParametro(p, null);
        }
    }
    private Pageable<List<ParametroEntity>> setResultDataListarParametro(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<ParametroEntity>> pageable=new Pageable<>(page);
        List<ParametroEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            ParametroEntity per = new ParametroEntity();
            per.setIdParametro((Integer) row[0]);
            per.setCodigo((String) row[1]);
            per.setValorPrimario((String) row[2]);
            per.setValorSecundario((String) row[3]);
            per.setValorTerciario((String) row[4]);
            per.setIdTipoParametro((Integer) row[5]);
            per.setPrefijo((String) row[6]);
            items.add(per);
            pageable.setTotalRecords(SpUtil.toLong(row[7]));

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
    public List<ParametroEntity> listaParametro(String prefijo) throws Exception {
        List<ParametroEntity> result = new ArrayList<ParametroEntity>();
        try {
            StoredProcedureQuery processStored = em.createStoredProcedureQuery("almacen.pa_Parametro_ListarPorPrefijo");
            processStored.registerStoredProcedureParameter("prefijo", String.class, ParameterMode.IN);
            SpUtil.enableNullParams(processStored);
            processStored.setParameter("prefijo", prefijo);
            processStored.execute();

            List<Object[]> spResult = processStored.getResultList();
            if (spResult.size() >= 1) {
                for (Object[] row : spResult) {
                    ParametroEntity per = new ParametroEntity();
                    per.setIdParametro((Integer) row[0]);
                    per.setCodigo((String) row[1]);
                    per.setValorPrimario((String) row[2]);
                    per.setValorSecundario((String) row[3]);
                    per.setValorTerciario((String) row[4]);
                    per.setIdTipoParametro((Integer) row[5]);
                    per.setPrefijo((String) row[6]);
                    result.add(per);
                }
            } else {
                return null;
            }
            return result;
        } catch (Exception e) {
            log.error("ParametroRepositoryImpl - listaParametro",
                    "Ocurrió un error :" + e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
    }

    @Override
    public ResultClassEntity saveParametros(List<TipoparametroEntity> tipoparametroEntityList) {
        ResultClassEntity result = new ResultClassEntity();
        List<TipoparametroEntity> parametrosList = new ArrayList<>();
        TipoparametroEntity parametros = new TipoparametroEntity();

        try {
            for(TipoparametroEntity tipoparametroEntity: tipoparametroEntityList){
                StoredProcedureQuery spr = em.createStoredProcedureQuery("almacen.[pa_TipoParametro_Registrar_Actualizar]");
                spr.registerStoredProcedureParameter("idTipoParametro", Integer.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("prefijo", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nombre", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("descripcion", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("estado", String.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("idUsuarioRegistro", Integer.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("idUsuarioModificacion", Integer.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("editable", Byte.class, ParameterMode.IN);
                spr.registerStoredProcedureParameter("nuIdTipoParametro", Integer.class, ParameterMode.OUT);
                setStoreProcedureEnableNullParameters(spr);
                spr.setParameter("idTipoParametro", tipoparametroEntity.getIdTipoParametro());
                spr.setParameter("prefijo", tipoparametroEntity.getPrefijo());
                spr.setParameter("nombre", tipoparametroEntity.getNombre());
                spr.setParameter("descripcion", tipoparametroEntity.getDescripcion());
                spr.setParameter("estado", tipoparametroEntity.getEstado());
                spr.setParameter("idUsuarioRegistro", tipoparametroEntity.getIdUsuarioRegistro());
                spr.setParameter("idUsuarioModificacion", tipoparametroEntity.getIdUsuarioModificacion());
                spr.setParameter("editable", tipoparametroEntity.getEditable());
                spr.execute();
                Integer idTipoParametro = (Integer) spr.getOutputParameterValue("nuIdTipoParametro");

                /*** registrar detalle Parametros***/
                for (ParametroEntity param : tipoparametroEntity.getLstParametro()) {

                    StoredProcedureQuery srepro = em.createStoredProcedureQuery("almacen.pa_Parametro_Registrar_Actualizar");
                    srepro.registerStoredProcedureParameter("idParametro", Integer.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("codigo", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("valorPrimario", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("valorSecundario", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("valorTerciario", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("estado", String.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("idTipoParametro", Integer.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("idParametroPadre", Integer.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("idUsuarioRegistro", Integer.class, ParameterMode.IN);
                    srepro.registerStoredProcedureParameter("idUsuarioModificacion", Integer.class, ParameterMode.IN);
                    setStoreProcedureEnableNullParameters(srepro);
                    srepro.setParameter("idParametro", param.getIdParametro());
                    srepro.setParameter("codigo", param.getCodigo());
                    srepro.setParameter("valorPrimario", param.getValorPrimario());
                    srepro.setParameter("valorSecundario", param.getValorSecundario());
                    srepro.setParameter("valorTerciario", param.getValorTerciario());
                    srepro.setParameter("estado", param.getEstado());
                    srepro.setParameter("idTipoParametro", idTipoParametro);
                    srepro.setParameter("idParametroPadre", tipoparametroEntity.getIdTipoParametro());
                    srepro.setParameter("idUsuarioRegistro", tipoparametroEntity.getIdUsuarioRegistro());
                    srepro.setParameter("idUsuarioModificacion", tipoparametroEntity.getIdUsuarioModificacion());

                    srepro.execute();
                }
                parametros.setIdTipoParametro(idTipoParametro);
                parametrosList.add(parametros);
            }
            /*** Registrar Tranferencia***/

            result.setData(parametrosList);
            result.setSuccess(true);
            result.setMessage("Se registró la parametros correctamente.");

            return result;
        } catch (Exception e) {
            log.error("Recurso - registrarParametros" + "Ocurrió un error :" + e.getMessage());
            result.setSuccess(false);
            result.setMessage("Ocurrió un error.");
            return result;
        }
    }

    @Override
    public ResultClassEntity EliminarParametro(Integer idParametro,Integer idUsuarioElimina) throws Exception {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        ParametroEntity parametroEntity = new ParametroEntity();

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_Parametro_Eliminar");
            sp.registerStoredProcedureParameter("IdParametro", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("IdUsuarioElimina", Integer.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("IdParametro", idParametro);
            sp.setParameter("IdUsuarioElimina", idUsuarioElimina);
            sp.execute();
            parametroEntity.setIdParametro(parametroEntity.getIdParametro());
            parametroEntity.setIdUsuarioElimina(parametroEntity.getIdUsuarioElimina());
            resultClassEntity.setData(parametroEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        } catch (Exception e) {
            log.error("EliminarParametro" + "Ocurrió un error :" + e.getMessage());
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

    @Override
    public ResultClassEntity registrarParametro(ParametroEntity parametro) {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        ParametroEntity parametroEntity = new ParametroEntity();
        try {
            StoredProcedureQuery spa = em.createStoredProcedureQuery("almacen.pa_Parametro_Registrar");
            spa.registerStoredProcedureParameter("codigo", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("valorPrimario", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("valorSecundario", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("valorTerciario", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("idTipoParametro", Integer.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("IdUsuarioRegistro", Integer.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("idParametro", Integer.class, ParameterMode.INOUT);
            SpUtil.enableNullParams(spa);
            spa.setParameter("codigo", parametro.getCodigo());
            spa.setParameter("valorPrimario", parametro.getValorPrimario());
            spa.setParameter("valorSecundario", parametro.getValorSecundario());
            spa.setParameter("valorTerciario", parametro.getValorTerciario());
            spa.setParameter("idTipoParametro", parametro.getIdTipoParametro());
            spa.setParameter("IdUsuarioRegistro", parametro.getIdUsuarioRegistro());
            spa.setParameter("idParametro", parametro.getIdParametro());
            spa.execute();
            Integer ParametroReturn = (Integer) spa.getOutputParameterValue("idParametro");
            parametroEntity.setIdParametro(ParametroReturn);
            resultClassEntity.setData(parametroEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        } catch (Exception e) {
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

    @Override
    public ResultClassEntity registrarTipoParametro(TipoparametroEntity tipoparametro) {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        TipoparametroEntity tipoparametroEntity = new TipoparametroEntity();
        try {
            StoredProcedureQuery spa = em.createStoredProcedureQuery("almacen.pa_TipoParametro_Registrar");
            spa.registerStoredProcedureParameter("prefijo", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("nombre", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("descripcion", String.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("IdUsuarioRegistro", Integer.class, ParameterMode.IN);
            spa.registerStoredProcedureParameter("IdTipoParametro", Integer.class, ParameterMode.INOUT);
            SpUtil.enableNullParams(spa);
            spa.setParameter("prefijo", tipoparametro.getPrefijo());
            spa.setParameter("nombre", tipoparametro.getNombre());
            spa.setParameter("descripcion", tipoparametro.getDescripcion());
            spa.setParameter("IdUsuarioRegistro", tipoparametro.getIdUsuarioRegistro());
            spa.setParameter("IdTipoParametro", tipoparametro.getIdTipoParametro());
            spa.execute();
            Integer TipoParametroReturn = (Integer) spa.getOutputParameterValue("IdTipoParametro");
            tipoparametroEntity.setIdTipoParametro(TipoParametroReturn);
            resultClassEntity.setData(tipoparametroEntity);
            resultClassEntity.setSuccess(true);
            return resultClassEntity;

        } catch (Exception e) {
            resultClassEntity.setSuccess(false);
            resultClassEntity.setMessage("Ocurrió un error.");
            return resultClassEntity;
        }
    }

    @Override
    public ResultClassEntity EliminarTipoParametro(Integer idTipoParametro,Integer idUsuarioElimina) throws Exception {
        ResultClassEntity resultClassEntity = new ResultClassEntity();
        TipoparametroEntity tipoparametroEntity = new TipoparametroEntity();

        try {
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_TipoParametro_Eliminar");
            sp.registerStoredProcedureParameter("IdTipoParametro", Integer.class, ParameterMode.IN);
            sp.registerStoredProcedureParameter("IdUsuarioElimina", Integer.class, ParameterMode.IN);
            SpUtil.enableNullParams(sp);
            sp.setParameter("IdTipoParametro", idTipoParametro);
            sp.setParameter("IdUsuarioElimina", idUsuarioElimina);
            sp.execute();
            tipoparametroEntity.setIdTipoParametro(tipoparametroEntity.getIdTipoParametro());
            tipoparametroEntity.setIdUsuarioElimina(tipoparametroEntity.getIdUsuarioElimina());
            resultClassEntity.setData(tipoparametroEntity);
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
    public Pageable<List<TipoparametroEntity>> listarBandejaTipoParametro(Page p) throws Exception {
        try{
            StoredProcedureQuery sp = em.createStoredProcedureQuery("almacen.pa_TipoParametro_Bandeja_Listar");
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
            return setResultDataListarTipoParametro(p, sp.getResultList());
        } catch (Exception e) {
            log.error("listarTipoParametro" + "Ocurrió un error :" + e.getMessage());
            return setResultDataListarTipoParametro(p, null);
        }
    }
    private Pageable<List<TipoparametroEntity>> setResultDataListarTipoParametro(Page page, List<Object[]> dataDb) throws Exception {
        Pageable<List<TipoparametroEntity>> pageable=new Pageable<>(page);
        List<TipoparametroEntity> items = new ArrayList<>();
        for (Object[] row : dataDb) {
            TipoparametroEntity tipopara = new TipoparametroEntity();
            tipopara.setIdTipoParametro((Integer) row[0]);
            tipopara.setPrefijo((String) row[1]);
            tipopara.setNombre((String) row[2]);
            tipopara.setDescripcion((String) row[3]);
            items.add(tipopara);
            pageable.setTotalRecords(SpUtil.toLong(row[4]));

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
    public List<TipoparametroEntity> listarTipoParametro() throws Exception {
        List<TipoparametroEntity> result = new ArrayList<TipoparametroEntity>();
        try {
            StoredProcedureQuery processStored = em.createStoredProcedureQuery("almacen.pa_TipoParametro_Listar");
            SpUtil.enableNullParams(processStored);
            processStored.execute();

            List<Object[]> spResult = processStored.getResultList();
            if (spResult.size() >= 1) {
                for (Object[] row : spResult) {
                    TipoparametroEntity tipopara = new TipoparametroEntity();
                    tipopara.setIdTipoParametro((Integer) row[0]);
                    tipopara.setPrefijo((String) row[1]);
                    tipopara.setNombre((String) row[2]);
                    tipopara.setDescripcion((String) row[3]);
                    result.add(tipopara);
                }
            } else {
                return null;
            }
            return result;
        } catch (Exception e) {
            log.error("listarTipoParametro",
                    "Ocurrió un error :" + e.getMessage());
            throw new Exception(e.getMessage(), e);
        }
    }

}
