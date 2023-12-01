package serfor.rrhh.almacen.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PuestoControlEntity {
    private Integer idPuestoControl;
    private String nombrePuestoControl;
    private String controlObligatorio;
    private String departamento;
    private String provincia;
    private String distrito;
    private String coordenadasNorte;
    private String coordenadasEste;
    private String zonaUTM;
    private Integer idAtf;
    private String estado;
    private String direccion;

    private String nombreATF;
}
