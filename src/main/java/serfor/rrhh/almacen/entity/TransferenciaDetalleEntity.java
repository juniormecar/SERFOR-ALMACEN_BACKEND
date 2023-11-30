package serfor.rrhh.almacen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaDetalleEntity {
    private Integer nuIdDetTransferencia;
    private Integer nuIdTransferencia;
    private Integer nuIdEspecie;
    private Integer nuCantidadProducto;
    private String estado;
    private Integer nuIdUsuarioRegistro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date feFechaRegistro;
    private Integer nuIdUsuarioModificacion;
    private Date feFechaModificacion;
    private Integer nuIdUsuarioElimina;
    private Date feFechaElimina;

    //
    private Integer nuIdRecursoProducto;
    private Integer idEspecie;
    private Integer descontar;

    private String nombreComun;
    private String nombreCientifico;
    private String tipo;
    private String unidadMedida;
    private BigDecimal descontarMetroCubico;

    private String disponibilidadActa;

}
