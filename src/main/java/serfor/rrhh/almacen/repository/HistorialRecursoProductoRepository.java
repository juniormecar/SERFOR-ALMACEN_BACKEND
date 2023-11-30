package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.*;

import java.util.List;

public interface HistorialRecursoProductoRepository {

    ResultClassEntity RegistroHistorialRecursoProducto(HistorialRecursoProductoEntity historialRecursoProducto) throws Exception;

    Pageable<List<HistorialRecursoProductoEntity>> ListarHistorialRecursoForestal(Integer nuIdAlmacen, String nombreEspecie,
                                                                                  String tipoProducto, String tipoIngreso,
                                                                                  String disponible, Page page) throws Exception;

}
