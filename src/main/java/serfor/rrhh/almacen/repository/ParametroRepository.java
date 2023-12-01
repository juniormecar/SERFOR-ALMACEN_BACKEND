package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.*;

import java.util.List;

public interface ParametroRepository {
    List<ParametroEntity> listaParametro(String prefijo) throws Exception;

    ResultClassEntity saveParametros(List<TipoparametroEntity> lstParametros) throws Exception;

    ResultClassEntity EliminarParametro(Integer idParametro,Integer idUsuarioElimina) throws Exception;

    ResultClassEntity registrarParametro(ParametroEntity parametro) throws Exception;

    ResultClassEntity registrarTipoParametro(TipoparametroEntity tipoparametro) throws Exception;

    ResultClassEntity EliminarTipoParametro(Integer idTipoParametro,Integer idUsuarioElimina) throws Exception;

    Pageable<List<TipoparametroEntity>> listarTipoParametro(Page page) throws Exception;

}
