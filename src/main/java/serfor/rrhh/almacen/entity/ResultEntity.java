package serfor.rrhh.almacen.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ResultEntity<T> {

    Integer Codigo;
    Boolean IsSuccess;
    String Message;
    String MessageExeption;
    String StackTrace;
    String InnerException;
    String Informacion;
    List<T> data;
    T item;

    Integer totalrecord;

    List<Map<String, Object>> lista;

    String[] cabecera;
    String[] detalle;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public ResultEntity() {
        // TODO Auto-generated constructor stub
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

    public String getInformacion() {
        return Informacion;
    }

    public void setInformacion(String informacion) {
        Informacion = informacion;
    }

    public Integer getTotalrecord() {
        return totalrecord;
    }

    public void setTotalrecord(Integer totalrecord) {
        this.totalrecord = totalrecord;
    }

    public String[] getCabecera() {
        return cabecera;
    }

    public void setCabecera(String[] cabecera) {
        this.cabecera = cabecera;
    }

    public List<Map<String, Object>> getLista() {
        return lista;
    }

    public void setLista(List<Map<String, Object>> lista) {
        this.lista = lista;
    }

    public String[] getDetalle() {
        return detalle;
    }

    public void setDetalle(String[] detalle) {
        this.detalle = detalle;
    }
}
