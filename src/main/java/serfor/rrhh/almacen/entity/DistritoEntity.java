package serfor.rrhh.almacen.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistritoEntity {
    private Integer idDistrito;
    private String nombreDistrito;
    private String codigoDistrito;
    private String codigoProvincia;
    private String codigoDistritoINEI;
    private String codigoDistritoRENIEC;
    private String codigoDistritoSUNAT;
    private Integer idProvincia;
    private Integer idDepartamento;
    private Integer idUsuarioCreacion;
    private String estadoRegistro;
    private Date fechaCreacion;
}
