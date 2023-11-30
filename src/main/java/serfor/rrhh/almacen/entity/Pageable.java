package serfor.rrhh.almacen.entity;

public class Pageable<T> extends Page{

    T data;
    Boolean success;
    String message;

    public Pageable() {
    }

    public Pageable(Long pageNumber, Long pageSize, String sortField, String sortType, T data) {
        super(pageNumber, pageSize, sortField, sortType);
        this.data = data;
    }

    public Pageable(Long pageNumber, Long pageSize, String sortField, String sortType, Long totalRecords, T data) {
        super(pageNumber, pageSize, sortField, sortType, totalRecords);
        this.data = data;
    }

    public Pageable(Page p) {
        super(p.getPageNumber(), p.getPageSize(), p.getSortField(), p.getSortType(), p.getTotalRecords());
        this.success = true;
    }

    public Pageable(Boolean success, String message, Page p) {
        super(p.getPageNumber(), p.getPageSize(), p.getSortField(), p.getSortType(), p.getTotalRecords());
        this.success = success;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
