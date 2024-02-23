package serfor.rrhh.almacen.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.RequestBody;
import serfor.rrhh.almacen.entity.ActaEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;
import serfor.rrhh.almacen.entity.TransferenciaEntity;

import java.util.List;

public interface ActaService {
    ResultClassEntity RegistroActa(ActaEntity acta) throws Exception;

    ResultClassEntity<ActaEntity> ListarActa(Integer nuIdRecurso) throws Exception;

    ResultClassEntity ActualizarFlag(ActaEntity acta) throws Exception;

    ByteArrayResource consolidadoActa_PDF(Integer idRecurso) throws Exception;
}
