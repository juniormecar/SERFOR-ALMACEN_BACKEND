package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.AtfEntity;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.ResultClassEntity;

import java.util.List;

public interface AtfRepository {
    Pageable<List<AtfEntity>> ListarBandejaATF(Page page) throws Exception;

    List<AtfEntity> ListarATF() throws Exception;

    ResultClassEntity RegistrarATF(AtfEntity atf) throws Exception;

    ResultClassEntity EliminarATF(Integer idAtf) throws Exception;
}
