package serfor.rrhh.almacen.service;

import org.springframework.http.ResponseEntity;
import serfor.rrhh.almacen.entity.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public interface ExternoService {
    ResponseLogin getLogin(RequestLogin param) throws Exception;
}

