package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.AtfEntity;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.ResultClassEntity;
import serfor.rrhh.almacen.repository.AtfRepository;
import serfor.rrhh.almacen.service.AtfService;

import java.util.List;


@Service
public class AtfServiceImpl implements AtfService {

    @Autowired
    private AtfRepository atfRepository;

    @Override
    public Pageable<List<AtfEntity>> ListarATF(Page page) throws Exception {
        return atfRepository.ListarATF(page);
    }

    @Override
    public ResultClassEntity RegistrarATF(AtfEntity atf) throws Exception {
        return atfRepository.RegistrarATF(atf);
    }

    @Override
    public ResultClassEntity EliminarATF(Integer idAtf) throws Exception {
        return atfRepository.EliminarATF(idAtf);
    }
}
