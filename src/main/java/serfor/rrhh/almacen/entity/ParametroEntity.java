package serfor.rrhh.almacen.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParametroEntity {
    private Integer idParametro;
    private String codigo;
    private String valorPrimario;
    private String valorSecundario;
    private Integer idTipoParametro;
    private Integer idParametroPadre;
    private String valorTerciario;
    private String prefijo;
    private String nombre;
    private String descripcion;
    private String estado;
    private Integer idUsuarioElimina;
}
