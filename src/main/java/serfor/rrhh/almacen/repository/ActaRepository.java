package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.*;

import java.util.List;

public interface ActaRepository {
    ResultClassEntity RegistroActa(ActaEntity acta) throws Exception;

    ResultClassEntity<ActaEntity> ListarActa(Integer nuIdRecurso) throws Exception;

    ResultClassEntity ActualizarFlag(ActaEntity acta) throws Exception;
}
