package serfor.rrhh.almacen.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLogin {

    private Empleado empleado;
    private String ip;
    private Usuario usuario;
}
