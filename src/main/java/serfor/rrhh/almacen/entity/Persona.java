package serfor.rrhh.almacen.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Persona{
    private Integer id;
    private String nombreAbreviado;
    private String paterno;
    private String materno;
    private String nombres;
    private String numeroDocumento;
    private String celular;
    private String correo;
    private String correoEmpresa;
    private Sistema docIdentidad;
}
