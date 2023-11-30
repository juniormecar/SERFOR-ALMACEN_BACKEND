package serfor.rrhh.almacen.third.pas.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntervenidoPas {
    private Integer NUIntervenidoID;
    private Integer NUIntervencionID;
    private Integer NUPersonaID;
    private Integer NUIntervenidoRolID;
    private String TXNombreCompleto;
    private String TXNombreCompletoActa;
    private Integer NUTipoDocumentoID;
    private String TXTipoDocumento;
    private String TXNumeroDocumento;
    private String TXNumeroDocumentoActa;
    private String TXDireccion;
    private String TXDireccionNotificacion;
    private Integer NUInfracciones;
    private Integer NUEstadoPASID;
    private String TXArchivado;
    private String TXUbigeoCodigo;
    private String TXDepartamento;
    private String TXProvincia;
    private String TXDistrito;
    private String TXActivo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date FESysFecha;
    private Integer NUSysUsuario;
    private String TXObservaciones;
    private String TXUbigeoDirNotificacion;
    private String TXEstadoActual;
    private String TXNombres;
    private String TXApellidoPaterno;
    private String TXApellidoMaterno;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date FEFechaCaducidad;
    private String TXColorSemaforo;
}
