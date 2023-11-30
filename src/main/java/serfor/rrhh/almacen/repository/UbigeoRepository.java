package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.DistritoEntity;
import serfor.rrhh.almacen.entity.ParametroEntity;
import serfor.rrhh.almacen.entity.ProvinciaEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;
import serfor.rrhh.almacen.entity.DepartamentoEntity;

import java.util.List;

public interface UbigeoRepository {
    List<DepartamentoEntity> ListarDepartamento() throws Exception;
    List<ProvinciaEntity> ListarProvincia(String codigoDepartamento) throws Exception;
   List<DistritoEntity> ListarDistrito(String codigoProvincia) throws Exception;
}
