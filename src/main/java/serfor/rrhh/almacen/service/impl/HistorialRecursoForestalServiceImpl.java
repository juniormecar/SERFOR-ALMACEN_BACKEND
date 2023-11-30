package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.HistorialRecursoProductoRepository;
import serfor.rrhh.almacen.service.HistorialRecursoForestalService;

import java.util.List;

@Service
public class HistorialRecursoForestalServiceImpl implements HistorialRecursoForestalService {

    @Autowired
    private HistorialRecursoProductoRepository historialRecursoProductoRepository;

    @Override
    public ResultClassEntity RegistroHistorialRecursoProducto(HistorialRecursoProductoEntity historialRecursoProducto) throws Exception {
        return historialRecursoProductoRepository.RegistroHistorialRecursoProducto(historialRecursoProducto);
    }

    @Override
    public Pageable<List<HistorialRecursoProductoEntity>> ListarHistorialRecursoForestal(Integer nuIdAlmacen, String nombreEspecie,
                                                                                         String tipoProducto, String tipoIngreso,
                                                                                         String disponible, Page page)
            throws Exception {
        return historialRecursoProductoRepository.ListarHistorialRecursoForestal(nuIdAlmacen,nombreEspecie,
                                                                                tipoProducto,tipoIngreso,disponible,page);
    }

}
