package serfor.rrhh.almacen.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.xpath.operations.Bool;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario{

    private Integer id;
    private String password;
    private Boolean estado;
    private String nombre;
    private Integer userRegistro;
    private String fechaCaduca;
    private String fechaIngreso;
    private String labelEstado;
    private String classEstado;
    private String fechaRegistro;
    private Sistema sistema;
    private TipoUsuario tipoUsuario;
    private Compagnia compagnia;
    private Dependencia dependencia;
    private Cargo cargo;
    private Persona persona;

}
