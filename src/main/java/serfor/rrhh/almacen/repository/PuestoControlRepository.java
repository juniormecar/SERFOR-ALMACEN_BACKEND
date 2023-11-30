package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.PuestoControlEntity;

import java.util.List;

public interface PuestoControlRepository {

    List<PuestoControlEntity> ListarPuestoControl(Integer idAtf) throws Exception;
}
