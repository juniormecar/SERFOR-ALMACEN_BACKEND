package serfor.rrhh.almacen.third.pas.entity;

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
public class IntervencionEntity {
    private Integer nuIntervencionID;
    private String txUbigeoCodigo;
    private Integer nuTipoInicioPASID;
    private Integer nuLugarAutoridadID;
    private Integer nuLugarAutoridad;
    private String txCUT;
    private String txArchivoInicial;
    private String txLugarReferencia;
    private String txObservaciones;
    private Date feFechaRegistro;
    private String txDescripcionHechos;
    private String txJustificacionMedida;
    private Integer nuAutoridadInstructoraID;
    private Integer nuAutoridadSancionadoraID;
    private Integer nuUnidadMedidaIDArea;
    private BigDecimal nuCantidadArea;
    private String txActivo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date feSysFecha;
    private Integer nuSysUsuario;
    private String txNumActaResolInicio;
    private String txDireccionIntervencion;
    private BigDecimal nuCoordenadaX;
    private BigDecimal nuCoordenadaY;
    private Integer nuZonaUTM;
    private Integer nuTipoSancion;
    private Integer nuSancionComplementaria;
    private Integer nuTipoEstablecimiento;
    private Integer nuMetodoDeteccion;
    private Integer nuTipoProductoEspecie;
    private String txNumeroExpediente;
    private String txDepositarioMedida;
    private String txObservIntervenidos;
    private String txObservInfracciones;
    private String txObservEspecies;
    private String txObservMedidaProvisoria;
    private Integer nuIdAutoridad;
    private String txDepositarioUbigeo;
    private String txDepositarioDepartamento;
    private String txDepositarioProvincia;
    private String txDepositarioDistrito;
    private String txDepositarioDireccion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "America/Lima")
    private Date feFechaCaducidad;

    private IntervenidoPas intervenidoPas;

    private List<IntervencionDetalleEntity> lstIntervencionDetalle;
}
