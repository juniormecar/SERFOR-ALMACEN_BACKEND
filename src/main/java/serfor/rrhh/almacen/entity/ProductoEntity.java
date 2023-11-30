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
public class ProductoEntity {
    private Integer nuIdProducto;
    private String txNumeroTrozas;
    private String txNumeroGtfOrigen;
    private String txNombreCientifico;
    private String txNombreComun;
    private String txTipo;
    private String txDescripcionPresentacion;
    private String txEstado;
    private Integer NuIdUsuarioRegistro;
    private Date feFechaRegistro;
    private Integer nuIdUsuarioModificacion;
    private Date feFechaModificacion;
    private Integer nuIdUsuarioElimina;
    private Date feFechaElimina;
    private String txObservaciones;

}
