package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.PuestoControlEntity;
import serfor.rrhh.almacen.repository.PuestoControlRepository;
import serfor.rrhh.almacen.service.PuestoControlService;

import java.util.List;

@Service
public class PuestoControlServiceImpl implements PuestoControlService {

    @Autowired
    private PuestoControlRepository puestocontrolRepository;
    @Override
    public List<PuestoControlEntity> ListarPuestoControl(Integer idAtf) throws Exception {
        return puestocontrolRepository.ListarPuestoControl(idAtf);
    }
}
