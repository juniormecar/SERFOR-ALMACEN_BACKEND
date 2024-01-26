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
public class HistorialRecursoProductoEntity {
    private Integer idHistorialRecursoForestal;
    private Integer nuIdRecursoProducto;
    private Integer nuIdRecurso;
    private String cantidadProducto;
    private String totalProducto;
    private String estado;
    private Integer idUsuarioRegistro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date fechaRegistro;
    private Integer idUsuarioModificacion;
    private Date fechaModificacion;
    private Integer idUsuarioElimina;
    private Date fechaElimina;
    private Integer idEspecie;
    private String tipoProducto;
    private String unidadMedida;
    private String acciones;
    private String nroGuiaTransporteForestal;
    private String nroActa;
    private String nombreComun;
    private String nombreCientifico;

    private String disponible;
    private String tipoIngreso;
    private BigDecimal cantidadIngreso;
    private BigDecimal saldoTotalIngreso;
    private String tipoSAlida;
    private BigDecimal cantidadSalida;
    private BigDecimal saldoTotalSalida;

    private String nomAlmacen;
    private String ATF;
    private String PuestoControl;

    private BigDecimal cantidadM3Ingreso;
    private BigDecimal saldoTotalM3Ingreso;
    private BigDecimal cantidadM3Salida;
    private BigDecimal saldoTotalM3Salida;

    private String codigoUnico;
}
