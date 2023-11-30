package serfor.rrhh.almacen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EspecieFaunaEntity {
    short idEspecie ;
    String codfor ;
    String nombreCientifico ;
    String nombreComun ;
    String autor ;
    String sinonimia ;
    String familia ;
    Integer numero_lista ;
    String fuente;
    String categoria ;
    String oficial ;
    String habitoCrecimiento ;
    String tipouso ;
    String cites;
    String nombrecomercial;
    Integer dmc ;
    Integer idFuenteOficial;
    String nombreNativo;
    String categoriaAmenaza;
    public short getIdEspecie() {
        return idEspecie;
    }
    public void setIdEspecie(short idEspecie) {
        this.idEspecie = idEspecie;
    }
    public String getCodfor() {
        return codfor;
    }
    public void setCodfor(String codfor) {
        this.codfor = codfor;
    }
    public String getNombreCientifico() {
        return nombreCientifico;
    }
    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }
    public String getNombreComun() {
        return nombreComun;
    }
    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getSinonimia() {
        return sinonimia;
    }
    public void setSinonimia(String sinonimia) {
        this.sinonimia = sinonimia;
    }
    public String getFamilia() {
        return familia;
    }
    public void setFamilia(String familia) {
        this.familia = familia;
    }
    public Integer getNumero_lista() {
        return numero_lista;
    }
    public void setNumero_lista(Integer numero_lista) {
        this.numero_lista = numero_lista;
    }
    public String getFuente() {
        return fuente;
    }
    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getOficial() {
        return oficial;
    }
    public void setOficial(String oficial) {
        this.oficial = oficial;
    }
    public String getHabitoCrecimiento() {
        return habitoCrecimiento;
    }
    public void setHabitoCrecimiento(String habitoCrecimiento) {
        this.habitoCrecimiento = habitoCrecimiento;
    }
    public String getTipouso() {
        return tipouso;
    }
    public void setTipouso(String tipouso) {
        this.tipouso = tipouso;
    }
    public String getCites() {
        return cites;
    }
    public void setCites(String cites) {
        this.cites = cites;
    }
    public String getNombrecomercial() {
        return nombrecomercial;
    }
    public void setNombrecomercial(String nombrecomercial) {
        this.nombrecomercial = nombrecomercial;
    }
    public Integer getDmc() {
        return dmc;
    }
    public void setDmc(Integer dmc) {
        this.dmc = dmc;
    }
    public Integer getIdFuenteOficial() {
        return idFuenteOficial;
    }
    public void setIdFuenteOficial(Integer idFuenteOficial) {
        this.idFuenteOficial = idFuenteOficial;
    }
    public String getNombreNativo() {
        return nombreNativo;
    }
    public void setNombreNativo(String nombreNativo) {
        this.nombreNativo = nombreNativo;
    }
    public String getCategoriaAmenaza() {
        return categoriaAmenaza;
    }
    public void setCategoriaAmenaza(String categoriaAmenaza) {
        this.categoriaAmenaza = categoriaAmenaza;
    }

}
