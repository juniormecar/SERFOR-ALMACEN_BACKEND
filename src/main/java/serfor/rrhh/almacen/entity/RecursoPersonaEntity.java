package serfor.rrhh.almacen.entity;

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
public class RecursoPersonaEntity {
    private Integer idRecursoPersona;
    private Integer nuIdRecurso;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombresPersona;
    private String foto;
    private String placa;
    private String tipoPersona;
    private String estado;
    private Integer idUsuarioRegistro;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date fechaRegistro;
    private Integer idUsuarioModificacion;
    private Date fechaModificacion;
    private Integer idUsuarioElimina;
    private Date fechaElimina;

}
