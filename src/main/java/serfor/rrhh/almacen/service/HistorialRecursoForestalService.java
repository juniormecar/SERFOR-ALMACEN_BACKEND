package serfor.rrhh.almacen.service;

import serfor.rrhh.almacen.entity.*;

import java.util.List;

public interface HistorialRecursoForestalService {
    ResultClassEntity RegistroHistorialRecursoProducto(HistorialRecursoProductoEntity historialRecursoProducto) throws Exception;

    Pageable<List<HistorialRecursoProductoEntity>> ListarHistorialRecursoForestal(Integer nuIdAlmacen, String nombreEspecie,
                                                                                  String tipoProducto, String tipoIngreso,
                                                                                  String disponible,Page page) throws Exception;

}
