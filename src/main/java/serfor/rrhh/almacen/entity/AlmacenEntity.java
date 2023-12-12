package serfor.rrhh.almacen.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlmacenEntity {
    private Integer nuIdAlmacen;
    private String txUbigeo;
    private String txNombreAlmacen;
    private String txTipoAlmacen;
    private String txTipoDocumento;
    private String txNumeroDocumento;
    private String txNombresEncargado;
    private BigDecimal nuCapacidadAlmacen;
    private Integer nuIdUsuarioModificacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date feFechaModificacion;
    private String txEstado;
    private Integer nuIdUsuarioElimina;
    private Date feFechaElimina;
    private Integer nuIdUsuarioRegistro;
    private Date feFechaRegistro;
    private String txPuestoControl;
    private String txNumeroATF;
    private String descrATF;
    private String descrPuestoControl;
    private String descrTipoAlmacen;
    private String foto;
    private Integer cantidadResponsables;
    private String direccionAlmacen;
    private List<AlmacenResponsableEntity> lstAlmacenResponsable;

    private BigDecimal capacidadNoMaderable;
    private BigDecimal capacidadFauna;
    private BigDecimal capacidadMaderable;
}
