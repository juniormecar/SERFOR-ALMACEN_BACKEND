package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.AtfEntity;
import serfor.rrhh.almacen.entity.DepartamentoEntity;

import java.util.List;

public interface AtfRepository {
    List<AtfEntity> ListarATF() throws Exception;
}
