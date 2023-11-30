package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.ParametroEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;
import serfor.rrhh.almacen.entity.TipoparametroEntity;
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
}
