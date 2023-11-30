package serfor.rrhh.almacen.entity;

public class Page {

    /**
     * Número de página
     */
    Long pageNumber;

    /**
     * Tamaño de página
     */
    Long pageSize;

    /**
     * Campo por el que se desea ordenar
     */
    String sortField;

    /**
     * Tipo de ordenamiento ASC o DESC
     */
    String sortType;

    /**
     * Campo de base de datos(solo pasarlo como parámetro)
     */
    Long offset;

    /**
     * Número total de registros
     */
    Long totalRecords;

    public Page() {
        this.pageNumber = null;
        this.pageSize = null;
        this.sortField = null;
        this.sortType = null;
        this.offset = null;
    }

    public Page(Long pageNumber, Long pageSize, String sortField, String sortType) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortField = sortField;
        this.sortType = sortType;
        this.offset = (this.pageSize == null || this.pageNumber == null) ? 0 : this.pageSize * (this.pageNumber - 1);
    }

    public Page(Long pageNumber, Long pageSize, String sortField, String sortType, Long totalRecords) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortField = sortField;
        this.sortType = sortType;
        this.totalRecords = totalRecords;
        this.offset = (this.pageSize == null || this.pageNumber == null) ? 0 : this.pageSize * (this.pageNumber - 1);
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

}
