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
public class ProvinciaEntity {
    private Integer idProvincia;
    private String nombreProvincia;
    private String codigoProvincia;
    private String codigoDepartamento;
    private String codigoProvinciaINEI;
    private String codigoProvinciaRENIEC;
    private String codigoProvinciaSUNAT;
    private Integer idDepartamento;
    private Integer idUsuarioCreacion;
    private String estadoRegistro;
    private Date fechaCreacion;

}
