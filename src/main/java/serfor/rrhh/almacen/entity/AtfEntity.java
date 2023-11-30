package serfor.rrhh.almacen.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtfEntity {
    private Integer idAtf;
    private String nombreAtf;
    private String codigoAtf;
    private String estado;
}
