package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.ParametroRepository;
import serfor.rrhh.almacen.service.ParametroService;

import java.util.List;

@Service
public class ParametroServiceImpl implements ParametroService {

    @Autowired
    private ParametroRepository parametroValorRepository;
    @Override
    public List<ParametroEntity> listaParametro(String prefijo) throws Exception {
        return parametroValorRepository.listaParametro(prefijo);
    }
    @Override
    public ResultClassEntity saveParametros(List<TipoparametroEntity> lstParametros) throws Exception {
        return parametroValorRepository.saveParametros(lstParametros);
    }

    @Override
    public ResultClassEntity EliminarParametro(Integer idParametro,Integer idUsuarioElimina) throws Exception {
        return parametroValorRepository.EliminarParametro(idParametro, idUsuarioElimina);
    }

    @Override
    public ResultClassEntity registrarTipoParametro(TipoparametroEntity tipoparametro) throws Exception {
        return parametroValorRepository.registrarTipoParametro(tipoparametro);
    }

    @Override
    public ResultClassEntity EliminarTipoParametro(Integer idTipoParametro,Integer idUsuarioElimina) throws Exception {
        return parametroValorRepository.EliminarTipoParametro(idTipoParametro, idUsuarioElimina);
    }

    @Override
    public Pageable<List<TipoparametroEntity>> listarTipoParametro(Page page) throws Exception {
        return parametroValorRepository.listarTipoParametro(page);
    }
}
