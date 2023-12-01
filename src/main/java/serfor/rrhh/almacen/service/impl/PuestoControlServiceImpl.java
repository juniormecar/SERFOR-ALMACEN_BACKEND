package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.PuestoControlEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;
import serfor.rrhh.almacen.repository.PuestoControlRepository;
import serfor.rrhh.almacen.service.PuestoControlService;

import java.util.List;

@Service
public class PuestoControlServiceImpl implements PuestoControlService {

    @Autowired
    private PuestoControlRepository puestocontrolRepository;
    @Override
    public Pageable<List<PuestoControlEntity>> ListarPuestoControl(Integer idAtf, Page page) throws Exception {
        return puestocontrolRepository.ListarPuestoControl(idAtf, page);
    }

    @Override
    public ResultClassEntity registrarPuestoControl(PuestoControlEntity puestoControl) throws Exception {
        return puestocontrolRepository.registrarPuestoControl(puestoControl);
    }

    @Override
    public ResultClassEntity EliminarPuestoControl(Integer idPuestoControl) throws Exception {
        return puestocontrolRepository.EliminarPuestoControl(idPuestoControl);
    }
}
