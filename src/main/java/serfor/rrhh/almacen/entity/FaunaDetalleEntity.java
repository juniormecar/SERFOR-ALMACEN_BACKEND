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
public class FaunaDetalleEntity {

    private Integer nuIdRecursoProducto;
    private Integer nuIdFauna;
    private Integer txCantidadProducto;
    private String edadEspecie;
    private String estadoEspecie;
    private String observaciones;
    private Integer idUsuarioRegistro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date fechaRegistro;
    private Integer idUsuarioModificacion;
    private Date fechaModificacion;
    private Integer idUsuarioElimina;
    private Date fechaElimina;
    private String txEstado;
    private Integer total;

}
