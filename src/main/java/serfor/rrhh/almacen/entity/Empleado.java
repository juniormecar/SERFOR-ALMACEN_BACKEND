package serfor.rrhh.almacen.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {

    private Integer id;
    private String fechaIngreso;
    private Compagnia compagnia;
    private Cargo cargo;
    private RegimenLaboral regimenLaboral;
    private Persona persona;
    private String ip;
}
