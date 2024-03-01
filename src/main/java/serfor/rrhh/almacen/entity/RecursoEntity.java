package serfor.rrhh.almacen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecursoEntity {
    private Integer nuIdRecurso;
    private String txTipoRecurso;
    private String txNombreRecurso;
    private String txNombreComun;
    private String txEstado;
    private Integer nuIdUsuarioRegistro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date feFechaRegistro;
    private Integer nuIdUsuarioModificacion;
    private Date feFechaModificacion;
    private Integer nuIdUsuarioElimina;
    private Date feFechaElimina;
    private String txNroGuiaTransporteForestal;
    private String txNombreAutoridadRegional;
    private Date feFechaExpedicion;
    private String txOrigenRecurso;
    private Date feFechaVencimiento;
    private String txObservaciones;
    private String txTipoRegistro;
    private AlmacenEntity almacen;
    private List<RecursoProductoEntity> lstEspecie;
    private Character txEstadoRecurso;
    private Integer nuIdAlmacen;
    private String numeroActa;
    private String tipoDocumento;
    private String numeroDocumento;

    private String txDescEstadoRecurso;

    private String tipoIngreso;

    private String tipoIngresoDesc;

    private String nombres;
    private String direccion;
    private String foto;

    private String tipoInfraccion;
    private String tipoSancion;
    private String txNombreAlmacen;

    private String disponibilidadActa;

    private String tipoDocumentoConductor;
    private String numeroDocumentoConductor;
    private String nombresConductor;
    private String placa;

    private Date fechaIngreso;
    private String horaIngreso;

    private RecursoPersonaEntity intervenido;
    private RecursoPersonaEntity conductor;

    private String documentoSesion;

    private Integer nuIdArchivoRecurso;
    private String typeAccion;
    private Integer nuIdRecursoProducto;

}
