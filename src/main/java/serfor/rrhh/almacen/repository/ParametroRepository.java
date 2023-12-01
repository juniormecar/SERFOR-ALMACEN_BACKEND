package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.ParametroEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;
import serfor.rrhh.almacen.entity.TipoparametroEntity;

import java.util.List;

public interface ParametroRepository {
    List<ParametroEntity> listaParametro(String prefijo) throws Exception;

    ResultClassEntity saveParametros(List<TipoparametroEntity> lstParametros) throws Exception;

    ResultClassEntity registrarTipoParametro(TipoparametroEntity tipoparametro) throws Exception;

    ResultClassEntity EliminarTipoParametro(Integer idTipoParametro,Integer idUsuarioElimina) throws Exception;

}
