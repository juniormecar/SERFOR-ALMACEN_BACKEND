package serfor.rrhh.almacen.service;

import serfor.rrhh.almacen.entity.*;

import java.util.List;

public interface UbigeoService {
    List<DepartamentoEntity> ListarDepartamento() throws Exception;
    List<ProvinciaEntity> ListarProvincia(String codigoDepartamento) throws Exception;
    List<DistritoEntity> ListarDistrito(String codigoProvincia) throws Exception;
}

