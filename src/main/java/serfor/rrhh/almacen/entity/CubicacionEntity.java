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
public class CubicacionEntity {

    private Integer idRecurProCubicacion;
    private BigDecimal cantidad;
    private BigDecimal espesor;
    private BigDecimal ancho;
    private BigDecimal largo;
    private BigDecimal diametroPromedio;
    private BigDecimal volumenPT;
    private BigDecimal volumenM3;
    private String estado;
    private Integer idUsuarioRegistro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date fechaRegistro;
    private Integer idUsuarioModificacion;
    private Date fechaModificacion;
    private Integer idUsuarioElimina;
    private Date fechaElimina;
    private Integer total;
    private Integer nuIdRecursoProducto;
    private BigDecimal longitud;
    private BigDecimal totalVolumenM3;
}
