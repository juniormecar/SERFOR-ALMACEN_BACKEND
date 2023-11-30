package serfor.rrhh.almacen.third.pas.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.hibernate.query.procedure.internal.ProcedureParameterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import serfor.rrhh.almacen.entity.Page;
import serfor.rrhh.almacen.entity.Pageable;
import serfor.rrhh.almacen.entity.SpUtil;
import serfor.rrhh.almacen.repository.impl.RecursoRepositoryImpl;
import serfor.rrhh.almacen.third.pas.entity.IntervencionDetalleEntity;
import serfor.rrhh.almacen.third.pas.entity.IntervencionEntity;
import serfor.rrhh.almacen.third.pas.entity.IntervenidoPas;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class IntervencionDao {


    @Autowired
    @Qualifier("JdbcDsPas")
    private JdbcTemplate jdbcTemplate;

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(RecursoRepositoryImpl.class);

    public void setStoreProcedureEnableNullParameters(StoredProcedureQuery storedProcedureQuery) {
        if (storedProcedureQuery == null || storedProcedureQuery.getParameters() == null)
            return;

        for (Parameter parameter : storedProcedureQuery.getParameters()) {
            ((ProcedureParameterImpl) parameter).enablePassingNulls(true);
        }
    }


    public Pageable<List<IntervencionEntity>> ListarIntervencion(String nuActa, Page p) throws Exception {
        try{

            String sql = "EXEC pas.pa_Intervencion_Listar ?,?,?,?,?";

            Connection conn = null;
            PreparedStatement stmt = null;
            conn = jdbcTemplate.getDataSource().getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nuActa);
            stmt.setLong(2, p.getPageNumber());
            stmt.setLong(3, p.getPageSize());
            stmt.setString(4, p.getSortField());
            stmt.setString(5, p.getSortType());
            Pageable<List<IntervencionEntity>> pageable=new Pageable<>(p);
            List<IntervencionEntity> items = new ArrayList<>();
            List<IntervencionDetalleEntity> itemsDetalle = new ArrayList<>();
            try (ResultSet rs1 = stmt.executeQuery()) {

                while (rs1.next()) {
                        IntervencionEntity item = new IntervencionEntity();
                        item.setNuIntervencionID((Integer) rs1.getInt("NU_IntervencionID"));
                        item.setTxUbigeoCodigo((String) rs1.getString("TX_UbigeoCodigo"));
                        item.setNuTipoInicioPASID((Integer) rs1.getInt("NU_TipoInicioPASID"));
                        item.setNuLugarAutoridadID((Integer) rs1.getInt("NU_LugarAutoridadID"));
                        item.setNuLugarAutoridad((Integer) rs1.getInt("NU_LugarAutoridad"));
                        item.setTxCUT((String) rs1.getString("TX_CUT"));
                        item.setTxArchivoInicial((String) rs1.getString("TX_ArchivoInicial"));
                        item.setTxLugarReferencia((String) rs1.getString("TX_LugarReferencia"));
                        item.setTxObservaciones((String) rs1.getString("TX_Observaciones"));
                        item.setFeFechaRegistro((Date) rs1.getDate("FE_FechaRegistro"));
                        item.setTxDescripcionHechos((String) rs1.getString("TX_DescripcionHechos"));
                        item.setTxJustificacionMedida((String) rs1.getString("TX_JustificacionMedida"));
                        item.setNuAutoridadSancionadoraID((Integer) rs1.getInt("NU_AutoridadSancionadoraID"));
                        item.setNuUnidadMedidaIDArea((Integer) rs1.getInt("NU_UnidadMedidaIDArea"));
                        item.setNuCantidadArea((BigDecimal) rs1.getBigDecimal("NU_CantidadArea"));
                        item.setTxActivo((String.valueOf(rs1.getString("TX_Activo"))));
                        item.setFeSysFecha((Date) rs1.getDate("FE_SysFecha"));
                        item.setNuSysUsuario((Integer) rs1.getInt("NU_SysUsuario"));
                        item.setTxNumActaResolInicio((String) rs1.getString("TX_NumActaResolInicio"));
                        item.setTxDireccionIntervencion((String) rs1.getString("TX_DireccionIntervencion"));
                        item.setNuCoordenadaX((BigDecimal) rs1.getBigDecimal("NU_CoordenadaX"));
                        item.setNuCoordenadaY((BigDecimal) rs1.getBigDecimal("NU_CoordenadaY"));
                        item.setNuZonaUTM((Integer) rs1.getInt("NU_ZonaUTM"));
                        item.setNuTipoSancion((Integer) rs1.getInt("NU_TipoSancion"));
                        item.setNuSancionComplementaria((Integer) rs1.getInt("NU_SancionComplementaria"));
                        item.setNuTipoEstablecimiento((Integer) rs1.getInt("NU_TipoEstablecimiento"));
                        item.setNuMetodoDeteccion((Integer) rs1.getInt("NU_MetodoDeteccion"));
                        item.setNuTipoProductoEspecie((Integer) rs1.getInt("NU_TipoProductoEspecie"));
                        item.setTxNumeroExpediente((String) rs1.getString("TX_NumeroExpediente"));
                        item.setTxDepositarioMedida((String) rs1.getString("TX_DepositarioMedida"));
                        item.setTxObservIntervenidos((String) rs1.getString("TX_ObservIntervenidos"));
                        item.setTxObservInfracciones((String) rs1.getString("TX_ObservInfracciones"));
                        item.setTxObservEspecies((String) rs1.getString("TX_ObservEspecies"));
                        item.setTxObservMedidaProvisoria((String) rs1.getString("TX_ObservMedidaProvisoria"));
                        item.setNuIdAutoridad((Integer) rs1.getInt("NU_IdAutoridad"));

                        /********************************* INTERVENIDO *********************************************************/
                        String sqlInterv = "EXEC pas.pa_Intervencion_Intervenido ?";
                        IntervenidoPas itemIntv = new IntervenidoPas();
                        Connection connIntv = null;
                        PreparedStatement stmtIntrv = null;
                        connIntv = jdbcTemplate.getDataSource().getConnection();
                        connIntv.setAutoCommit(false);
                        stmtIntrv = conn.prepareStatement(sqlInterv);
                        stmtIntrv.setInt(1, item.getNuIntervencionID());
                        try (ResultSet rsIntv = stmtIntrv.executeQuery()) {
                            while (rsIntv.next()) {
                                itemIntv.setNUIntervenidoID((Integer) rsIntv.getInt("NU_IntervenidoID"));
                                itemIntv.setNUIntervencionID((Integer) rsIntv.getInt("NU_IntervencionID"));
                                itemIntv.setNUPersonaID((Integer) rsIntv.getInt("NU_PersonaID"));
                                itemIntv.setNUIntervenidoRolID((Integer) rsIntv.getInt("NU_IntervenidoRolID"));
                                itemIntv.setTXNombreCompleto((String) rsIntv.getString("TX_NombreCompleto"));
                                itemIntv.setTXNombreCompletoActa((String) rsIntv.getString("TX_NombreCompletoActa"));
                                itemIntv.setNUTipoDocumentoID((Integer) rsIntv.getInt("NU_TipoDocumentoID"));
                                itemIntv.setTXTipoDocumento((String) rsIntv.getString("TX_TipoDocumento"));
                                itemIntv.setTXNumeroDocumento((String) rsIntv.getString("TX_NumeroDocumento"));
                                itemIntv.setTXNumeroDocumentoActa((String) rsIntv.getString("TX_NumeroDocumentoActa"));
                                itemIntv.setTXDireccion((String) rsIntv.getString("TX_Direccion"));
                                itemIntv.setTXDireccionNotificacion((String) rsIntv.getString("TX_DireccionNotificacion"));
                                itemIntv.setNUInfracciones((Integer) rsIntv.getInt("NU_Infracciones"));
                                itemIntv.setNUEstadoPASID((Integer) rsIntv.getInt("NU_EstadoPASID"));
                                itemIntv.setTXArchivado((String) rsIntv.getString("TX_Archivado"));
                                itemIntv.setTXUbigeoCodigo((String) rsIntv.getString("TX_UbigeoCodigo"));
                                itemIntv.setTXDepartamento((String) rsIntv.getString("TX_Departamento"));
                                itemIntv.setTXProvincia((String) rsIntv.getString("TX_Provincia"));
                                itemIntv.setTXDistrito((String) rsIntv.getString("TX_Distrito"));
                                itemIntv.setTXActivo((String) rsIntv.getString("TX_Activo"));
                                itemIntv.setFESysFecha((Date) rsIntv.getDate("FE_SysFecha"));
                                itemIntv.setNUSysUsuario((Integer) rsIntv.getInt("NU_SysUsuario"));
                                itemIntv.setTXObservaciones((String) rsIntv.getString("TX_Observaciones"));
                                itemIntv.setTXUbigeoDirNotificacion((String) rsIntv.getString("TX_UbigeoDirNotificacion"));
                                itemIntv.setTXEstadoActual((String) rsIntv.getString("TX_EstadoActual"));
                                itemIntv.setTXNombres((String) rsIntv.getString("TX_Nombres"));
                                itemIntv.setTXApellidoPaterno((String) rsIntv.getString("TX_ApellidoPaterno"));
                                itemIntv.setTXApellidoMaterno((String) rsIntv.getString("TX_ApellidoMaterno"));

                            }

                        }
                        connIntv.close();
                        item.setIntervenidoPas(itemIntv);
                        /********************************* ESPECIES *********************************************************/
                            String sqlEspecie = "EXEC pas.pa_Intervencion_Detalle_Listar ?,?,?";

                            Connection connEsp = null;
                            PreparedStatement stmtEsp = null;
                            connEsp = jdbcTemplate.getDataSource().getConnection();
                            connEsp.setAutoCommit(false);
                            stmtEsp = conn.prepareStatement(sqlEspecie);
                            stmtEsp.setInt(1, item.getNuIntervencionID());
                            stmtEsp.setString(2, p.getSortField());
                            stmtEsp.setString(3,  p.getSortType());
                            try (ResultSet rsEs = stmtEsp.executeQuery()) {
                                while (rsEs.next()) {
                                    IntervencionDetalleEntity itemEsp = new IntervencionDetalleEntity();
                                    itemEsp.setNuIntervencionEspecieID((Integer) rsEs.getInt("NU_IntervencionEspecieID"));
                                    itemEsp.setNuEspecieID((Integer) rsEs.getInt("NU_EspecieID"));
                                    itemEsp.setNuIntervencionID((Integer) rsEs.getInt("NU_IntervencionID"));
                                    itemEsp.setNuTipoRecursoID((Integer) rsEs.getInt("NU_TipoRecursoID"));
                                    itemEsp.setNuProductoUnidadMedidaID((Integer) rsEs.getInt("NU_ProductoUnidadMedidaID"));
                                    itemEsp.setTxEspecie((String) rsEs.getString("TX_Especie"));
                                    itemEsp.setTxNombreComun((String) rsEs.getString("TX_NombreComun"));
                                    itemEsp.setTxDescripcion((String) rsEs.getString("TX_Descripcion"));
                                    itemEsp.setNuCantidad((BigDecimal) rsEs.getBigDecimal("NU_Cantidad"));
                                    itemEsp.setNuEstadoID((Integer) rsEs.getInt("NU_EstadoID"));
                                    itemEsp.setTxActivo(String.valueOf(rsEs.getString("TX_Activo")));
                                    itemEsp.setNuCantidadActual((BigDecimal) rsEs.getBigDecimal("NU_CantidadActual"));
                                    itemEsp.setFeSysFecha((Date) rsEs.getDate("FE_SysFecha"));
                                    itemEsp.setNuSysUsuario((Integer) rsEs.getInt("NU_SysUsuario"));
                                    itemEsp.setTxPresuntoOrigen((String) rsEs.getString("TX_PresuntoOrigen"));
                                    itemEsp.setTxPresuntoDestino((String) rsEs.getString("TX_PresuntoDestino"));
                                    itemEsp.setNuCantidadCrias((Integer) rsEs.getInt("NU_CantidadCrias"));
                                    itemEsp.setNuCantidadJuveniles((Integer) rsEs.getInt("NU_CantidadJuveniles"));
                                    itemEsp.setNuCantidadAdultos((Integer) rsEs.getInt("NU_CantidadAdultos"));
                                    itemEsp.setTxObservaciones((String) rsEs.getString("TX_Observaciones"));
                                    itemEsp.setNuNoEncontradoLista((Integer) rsEs.getInt("NU_NoEncontradoLista"));
                                    itemsDetalle.add(itemEsp);
                                }
                            }
                        connEsp.close();
                        /*****************************************************************************************/

                        item.setLstIntervencionDetalle(itemsDetalle);
                        items.add(item);
                        pageable.setTotalRecords(SpUtil.toLong(rs1.getInt("total")));
                    }
            }
            conn.close();
            pageable.setData(items);
            pageable.setSuccess(true);
            if(items.size()>0){
                pageable.setMessage("Se obtuvo data.");
            }else{
                pageable.setMessage("No se encontró data.");
            }
            return pageable;
        } catch (Exception e) {
            log.error("PasRepositoryImpl - listar"+"Ocurrió un error :" + e.getMessage());
            return new Pageable<>(p);
        }
    }

}
