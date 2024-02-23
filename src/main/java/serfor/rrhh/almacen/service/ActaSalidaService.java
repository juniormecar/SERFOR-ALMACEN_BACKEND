package serfor.rrhh.almacen.service;

import org.springframework.core.io.ByteArrayResource;
import serfor.rrhh.almacen.entity.TransferenciaEntity;

import java.util.List;

public interface ActaSalidaService {
    ByteArrayResource consolidadoActaSalida_PDF(List<TransferenciaEntity> transferencia) throws Exception;
}
