package serfor.rrhh.almacen.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlmacenResponsableEntity {
    private Integer idAlmacenResponsable;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombresResponsable;
    private Integer nuIdAlmacen;
    private Integer idUsuarioRegistro;
    private Date fechaRegistro;
    private Integer idUsuarioModificacion;
    private Date fechaModificacion;
    private Integer idUsuarioElimina;
    private Date fechaElimina;
    private String estado;

    private String nombreAlmacen;
    private String tipoAlmacen;
}
