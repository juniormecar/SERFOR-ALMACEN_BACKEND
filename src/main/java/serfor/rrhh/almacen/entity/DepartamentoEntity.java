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
public class DepartamentoEntity {
    private Integer idDepartamento;
    private String nombreDepartamento;
    private String codigoDepartamento;
    private String codigoDepartamentoINEI;
    private String codigoDepartamentoRENIEC;
    private String codigoDepartamentoSUNAT;
    private String estadoRegistro;
    private Integer idUsuarioCreacion;
    private Date fechaCreacion;
}
