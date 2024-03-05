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
public class ReporteEntity {
    private Integer nuIdTransferencia;
    private String nombre;
    private String apellidos;
    private String tipoDocumento;
    private String documento;
    private String nroActa;
    private Integer nuIdRecurso;
    private String tipoTransferencia;
    private String tipoTransferenciaDetalle;
    private String observaciones;
    private Integer nuIdAlmacen;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date feFechaRegistro;
    private String txCodigoPuntoControl;
    private String txCodigoPuntoControlDetalle;
    private Integer idEspecie;
    private BigDecimal cantidadProducto;
    private String nombreComun;
    private String nombreCientifico;
    private String almacenOrigen;
    private String almacenDestino;
    private BigDecimal metroCubico;
    private String faunaSalida;

    private Integer nuIdAlmacenOrigin;
    private String tipoEspecie;
    private String periodo;

    private BigDecimal cantidad;
    private String tipoAccion;
    private Date fechaInicio;
    private Date fechaFin;
    private String numeroDocumento;
    private String nombreAlmacen;
    private Integer cantidadTotalIngresos;
    private Integer cantidadTotalSalidas;

    private String detalleReporte;
    private BigDecimal cantidadTotalMAD;
    private BigDecimal cantidadTotalNOMAD;
    private BigDecimal cantidadTotalFA;

    private String atf;
    private String puestoControl;
    private BigDecimal cantidadCapacidadMAD;
    private BigDecimal cantidadCapacidadNOMAD;
    private BigDecimal cantidadCapacidadFA;
    private String unidadMedida;

    private String tipoIngreso;

    private BigDecimal cantidadTotalXtipoYunidadMedida;

    private String codigoUnico;

    private BigDecimal descontar;
    private BigDecimal descontarMetroCubico;

    private BigDecimal cantidadProductoRet;
    private BigDecimal metroCubicoRet;

    private Integer nuIdArchivoTransferencia;

    public String numeroActaRetorno;

    private Integer nuIdTransferenciaDetalle;
    public String nroResolucion;

    private Date fechaTransferencia;
    public String horaTransferencia;
}
