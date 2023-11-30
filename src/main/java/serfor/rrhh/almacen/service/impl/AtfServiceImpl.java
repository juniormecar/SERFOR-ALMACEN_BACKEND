package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.AtfEntity;
import serfor.rrhh.almacen.repository.AtfRepository;
import serfor.rrhh.almacen.service.AtfService;

import java.util.List;


@Service
public class AtfServiceImpl implements AtfService {

    @Autowired
    private AtfRepository atfRepository;

    @Override
    public List<AtfEntity> ListarATF() throws Exception {
        return atfRepository.ListarATF();
    }
}
