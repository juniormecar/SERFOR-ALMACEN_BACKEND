package serfor.rrhh.almacen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaEntity {
    private Integer nuIdTransferencia;
    private String nombre;
    private String apellidos;
    private String tipoDocumento;
    private String documento;
    private String nroActa;
    private Integer nuIdRecurso;
    private String tipoTransferencia;
    private String estado;
    private Integer nuIdUsuarioRegistro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date feFechaRegistro;
    private Integer nuIdUsuarioModificacion;
    private Date feFechaModificacion;
    private Integer nuIdUsuarioElimina;
    private Date feFechaElimina;
    private String observaciones;
    private String txCodigoPuntoControl;
    private Integer nuIdAlmacen;
    private Integer nuIdAlmacenOrigin;
    private List<TransferenciaDetalleEntity> lstTransferenciaDetalle;
    private Integer idEspecie;
    private String tipoTransferenciaDetalle;
    private String txCodigoPuntoControlDetalle;
    private Integer cantidadProducto;
    private String nombreCientifico;
    private String nombreComun;
    private String almacenOrigen;
    private String almacenDestino;
    private String nroActaTransferencia;
    private String nroResolucion;
    private String nroActaTraslado;

    private BigDecimal metroCubico;

    private String faunaSalida;

    private String tipoEspecie;

    private Integer nuIdArchivo;
    private String typeAccion;
    private Integer nuIdArchivoRetorno;
    private String nroActaRetorno;

    private Date fechaTransferencia;
    private String horaTransferencia;
}
