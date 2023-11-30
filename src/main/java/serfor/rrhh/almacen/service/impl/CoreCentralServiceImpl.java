package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.EspecieFaunaEntity;
import serfor.rrhh.almacen.entity.EspecieForestalEntity;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.repository.CoreCentralRepository;
import serfor.rrhh.almacen.service.CoreCentralService;
import serfor.rrhh.almacen.entity.*;
import java.util.List;

@Service
public class CoreCentralServiceImpl implements CoreCentralService {

    @Autowired
    private CoreCentralRepository coreCentralRepository;
    @Override
    public  Pageable<List<EspecieFaunaEntity>> listaPorFiltroEspecieFauna(String nombreEspecie, Page page) throws Exception {
        // TODO Auto-generated method stub
        return coreCentralRepository.listaPorFiltroEspecieFauna(nombreEspecie, page);
    }

    @Override
    public Pageable<List<EspecieForestalEntity>> listaPorFiltroEspecieForestal(Integer idEspecie, String nombreComun,
                                                                     String nombreCientifico , String autor,
                                                                     String familia, Page page) throws Exception {
        // TODO Auto-generated method stub
        return coreCentralRepository.listaPorFiltroEspecieForestal(idEspecie, nombreComun, nombreCientifico , autor, familia,page);
    }
}
