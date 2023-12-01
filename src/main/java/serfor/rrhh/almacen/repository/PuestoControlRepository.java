package serfor.rrhh.almacen.repository;

import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.PuestoControlEntity;
import serfor.rrhh.almacen.entity.ResultClassEntity;

import java.util.List;

public interface PuestoControlRepository {

    Pageable<List<PuestoControlEntity>> ListarPuestoControl(Integer idAtf, Page page) throws Exception;

    ResultClassEntity registrarPuestoControl(PuestoControlEntity puestoControl) throws Exception;

    ResultClassEntity EliminarPuestoControl(Integer idPuestoControl) throws Exception;
}
