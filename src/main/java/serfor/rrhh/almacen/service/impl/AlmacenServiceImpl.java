package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.AlmacenRepository;
import serfor.rrhh.almacen.service.AlmacenService;

import java.util.List;

@Service
public class AlmacenServiceImpl implements AlmacenService {

    @Autowired
    private AlmacenRepository almacenRepository;

    @Override
    public Pageable<List<AlmacenEntity>> ListarAlmacen(String txUbigeo, String txNombreAlmacen, String txTipoAlmacen,
                                                       String txPuestoControl, String txNumeroATF, String txNumeroDocumento, Page page)
    throws Exception {
                 return almacenRepository.ListarAlmacen( txUbigeo, txNombreAlmacen, txTipoAlmacen,
                                                         txPuestoControl, txNumeroATF, txNumeroDocumento, page);
    }

    @Override
    public ResultClassEntity EliminarAlmacen(Integer nuIdAlmacen,Integer nuIdUsuarioElimina) throws Exception {
        return almacenRepository.EliminarAlmacen(nuIdAlmacen, nuIdUsuarioElimina);
    }

    @Override
    public ResultClassEntity RegistroAlmacen(AlmacenEntity almacen) throws Exception {
        return almacenRepository.RegistroAlmacen(almacen);
    }

    @Override
    public Pageable<List<AlmacenResponsableEntity>> ListarAlmacenResponsable(Integer nuIdAlmacen,String numeroDocumento, Page page)
    throws Exception {
        return almacenRepository.ListarAlmacenResponsable( nuIdAlmacen, numeroDocumento, page);
    }

    @Override
    public ResultClassEntity EliminarAlmacenResponsable(Integer idAlmacenResponsable,Integer idUsuarioElimina) throws Exception {
        return almacenRepository.EliminarAlmacenResponsable(idAlmacenResponsable, idUsuarioElimina);
    }

    @Override
    public AlmacenEntity getAlmacen(Integer idAlmacen)throws Exception {
        return almacenRepository.getAlmacen( idAlmacen);
    }

}
