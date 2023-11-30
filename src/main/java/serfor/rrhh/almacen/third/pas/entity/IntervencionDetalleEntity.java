package serfor.rrhh.almacen.third.pas.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntervencionDetalleEntity {

    private Integer nuIntervencionEspecieID;
    private Integer nuEspecieID;
    private Integer nuIntervencionID;
    private Integer nuTipoRecursoID;
    private Integer nuProductoUnidadMedidaID;
    private String txEspecie;
    private String txNombreComun;
    private String txDescripcion;
    private BigDecimal nuCantidad;
    private Integer nuEstadoID;
    private String txActivo;
    private BigDecimal nuCantidadActual;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date feSysFecha;
    private Integer nuSysUsuario;
    private String txPresuntoOrigen;
    private String txPresuntoDestino;
    private Integer nuCantidadCrias;
    private Integer nuCantidadJuveniles;
    private Integer nuCantidadAdultos;
    private String txObservaciones;
    private Integer nuNoEncontradoLista;
}
