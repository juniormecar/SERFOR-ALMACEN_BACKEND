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
public class RecursoProductoEntity {

    private Integer nuIdRecursoProducto;
    private RecursoEntity recurso;
    private ProductoEntity producto;
    private BigDecimal txCantidadProducto;
    private String txTotalProducto;
    private String txEstado;
    private Integer idUsuarioRegistro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date feFechaRegistro;
    private Integer nuIdUsuarioModificacion;
    private Date feFechaModificacion;
    private Integer nuIdUsuarioElimina;
    private Date feFechaElimina;
    /*private Integer nuIdProducto;*/
    private Integer nuIdRecurso;
    private Integer idEspecie;

    //
    private String familia;
    private String nombreCientifico;
    private String nombreComun;
    private String nombreComercial;

    private String tipoProducto;
    private String unidadMedida;

    private String nroGuiaTransporteForestal;
    private Integer nuIdAlmacen;
    private String numeroActa;
    private String nombres;
    private String type;
    private String nombreAlmacen;
    private String tipoIngreso;

    private String tipoAlmacenamiento;
    private String capacidadUnidad;
    private String tipoSubProducto;

    private String numeroDocumento;
    private String desctipoProducto;
    private String tipo;

    private String txPuestoControl;
    private String txNumeroATF;

    private BigDecimal volumen;

    private BigDecimal metroCubico;

    private String disponibilidadActa;

    private String descTipoProducto;
    private String descUnidadMedida;
    private String descSubProducto;
    private String descTipoAlmacenamiento;

    private BigDecimal cantidadTotal;
    private Integer nuIdArchivo;

    ;

}
