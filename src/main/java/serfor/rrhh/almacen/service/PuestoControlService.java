package serfor.rrhh.almacen.service;

import serfor.rrhh.almacen.entity.PuestoControlEntity;

import java.util.List;

public interface PuestoControlService {
    List<PuestoControlEntity> ListarPuestoControl( Integer idAtf ) throws Exception;
}
