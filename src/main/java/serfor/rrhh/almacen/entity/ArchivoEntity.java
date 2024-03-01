package serfor.rrhh.almacen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArchivoEntity {
    private Integer idArchivo;
    private Integer idArchivoDetalle;
    private String ruta;
    private String nombre;
    private String nombreGenerado;
    private String descripcionArchivo;
    private String extension;
    private String tipoDocumento;
    private byte[] file;
    private String contenType;
    private Integer idRecursoProducto;
    private Integer idRecurso;
    private String estado;
    private String type;
    private String codigo;
    private MultipartFile files;


    // @Column(name = "NU_ID_USUARIO_REGISTRO",  nullable = false)
    private Integer idUsuarioRegistro;
    // @Column(name = "FE_FECHA_REGISTRO",  nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date fechaRegistro;
    // @Column(name = "NU_ID_USUARIO_MODIFICACION",  nullable = false)
    private Integer idUsuarioModificacion;
    // @Column(name = "FE_FECHA_MODIFICACION",  nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date fechaModificacion;
    // @Column(name = "NU_ID_USUARIO_ELIMINA",  nullable = false)
    private Integer idUsuarioElimina;
    // @Column(name = "FE_FECHA_MODIFICACION",  nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date fechaElimina;
    private String token;
    private Integer nuAlerta;
    private byte[] bitFile;
}
