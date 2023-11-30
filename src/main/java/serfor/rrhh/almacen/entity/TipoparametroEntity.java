package serfor.rrhh.almacen.entity;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoparametroEntity {
    private Integer idTipoParametro;
    private String prefijo;
    private String nombre;
    private String descripcion;
    private String estado;
    private Integer idUsuarioRegistro;
    private Date fechaRegistro;
    private Integer idUsuarioModificacion;
    private Date fechaModificacion;
    private Integer idUsuarioElimina;
    private Date fechaElimina;
    private Byte editable;

    private List<ParametroEntity> lstParametro;

}
