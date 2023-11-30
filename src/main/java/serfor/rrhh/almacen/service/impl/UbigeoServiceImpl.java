package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.UbigeoRepository;
import serfor.rrhh.almacen.service.UbigeoService;

import java.util.List;

@Service
public class UbigeoServiceImpl implements UbigeoService {
    @Autowired
    private UbigeoRepository ubigeoRepository;


    @Override
    public List<DepartamentoEntity> ListarDepartamento() throws Exception {
        return ubigeoRepository.ListarDepartamento();
    }

    @Override
    public List<ProvinciaEntity> ListarProvincia(String codigoDepartamento) throws Exception {
        return ubigeoRepository.ListarProvincia(codigoDepartamento);
    }

    @Override
    public List<DistritoEntity> ListarDistrito(String codigoProvincia) throws Exception {
        return ubigeoRepository.ListarDistrito(codigoProvincia);
    }

}
