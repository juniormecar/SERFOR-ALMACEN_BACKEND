package serfor.rrhh.almacen.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class ResultArchivoEntity implements Serializable {
    private Integer Codigo;
    private Boolean IsSuccess;
    private Boolean Success;
    private String Message;
    private String MessageExeption;
    private String StackTrace;
    private String InnerException;
    private String NombeArchivo;
    private String NombeArchivoGenerado;
    private String TipoDocumento;
    private String ContenTypeArchivo;
    private byte[] Archivo;
    private String Informacion;
    private String typeFile;
    private String txExtension;

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    public String getTxExtension() {
        return txExtension;
    }

    public void setTxExtension(String txExtension) {
        this.txExtension = txExtension;
    }
    public Integer getCodigo() {
        return Codigo;
    }
    public void setCodigo(Integer codigo) {
        Codigo = codigo;
    }
    public Boolean getIsSuccess() {
        return IsSuccess;
    }
    public void setIsSuccess(Boolean isSuccess) {
        IsSuccess = isSuccess;
    }
    public Boolean getSuccess() {
        return Success;
    }
    public void setSuccess(Boolean success) {
        Success = success;
    }
    public String getMessage() {
        return Message;
    }
    public void setMessage(String message) {
        Message = message;
    }
    public String getMessageExeption() {
        return MessageExeption;
    }
    public void setMessageExeption(String messageExeption) {
        MessageExeption = messageExeption;
    }
    public String getStackTrace() {
        return StackTrace;
    }
    public void setStackTrace(String stackTrace) {
        StackTrace = stackTrace;
    }
    public String getInnerException() {
        return InnerException;
    }
    public void setInnerException(String innerException) {
        InnerException = innerException;
    }
    public String getNombeArchivo() {
        return NombeArchivo;
    }
    public void setNombeArchivo(String nombeArchivo) {
        NombeArchivo = nombeArchivo;
    }
    public String getNombeArchivoGenerado() {
        return NombeArchivoGenerado;
    }
    public void setNombeArchivoGenerado(String nombeArchivoGenerado) {
        NombeArchivoGenerado = nombeArchivoGenerado;
    }
    public String getTipoDocumento() {
        return TipoDocumento;
    }
    public void setTipoDocumento(String tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }
    public String getContenTypeArchivo() {
        return ContenTypeArchivo;
    }
    public void setContenTypeArchivo(String contenTypeArchivo) {
        ContenTypeArchivo = contenTypeArchivo;
    }
    public byte[] getArchivo() {
        return Archivo;
    }
    public void setArchivo(byte[] archivo) {
        Archivo = archivo;
    }
    public String getInformacion() {
        return Informacion;
    }
    public void setInformacion(String informacion) {
        Informacion = informacion;
    }



}
