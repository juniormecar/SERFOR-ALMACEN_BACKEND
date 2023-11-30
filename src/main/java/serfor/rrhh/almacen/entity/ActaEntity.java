package serfor.rrhh.almacen.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActaEntity {
    private Integer idActa;
    private Integer nuIdRecurso;
    private String domicilioIntervenido;
    private String nombreAutoridadInstructora;
    private String nombreAdministrado;
    private String nombreTestigo;
    private String numeroDNI;
    private String tipoEspecimen;
    private String estado;
   /* @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima"*/
    /*@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "America/Lima")*/
    private Date fechaIntervencion;
    private Date fechaRegistro;
    private Boolean flag;
    private String lugar;
    private String nombreIntervenido;
    private String hora;
    private String descripcionHechos;
    private String conductaInfractora;
    private String tipoInfraccion;
    private String atutoridadDecisora;
    private String sustentoNormativoDecisora;
    private String mediosPrueba;
    private String plazoPresentacionEncargo;
    private String medidaProvisional;
    private String justificacion;
    private String especie;
    private String unidad;
    private String cantidadVolumen;
    private String observaciones;
    private String autoridadInstructora;
    private String sustentoNormativoInstructora;
    private String sancion;
    private String montoMulta;
    private String sustentoNormativoMulta;

}
