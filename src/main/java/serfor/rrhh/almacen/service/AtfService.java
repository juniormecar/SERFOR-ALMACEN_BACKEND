package serfor.rrhh.almacen.service;

import serfor.rrhh.almacen.entity.AtfEntity;
import serfor.rrhh.almacen.entity.DepartamentoEntity;

import java.util.List;

public interface AtfService {
    List<AtfEntity> ListarATF() throws Exception;
}
