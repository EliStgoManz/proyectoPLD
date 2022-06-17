/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidad;

/**
 *
 * @author israel.garcia
 */
public class RepresentantePLD {
    Integer id_siseg;
    String nombre;

    public RepresentantePLD() {
    }

    public RepresentantePLD(Integer id_siseg, String nombre) {
        this.id_siseg = id_siseg;
        this.nombre = nombre;
    }

    public Integer getId_siseg() {
        return id_siseg;
    }

    public void setId_siseg(Integer id_siseg) {
        this.id_siseg = id_siseg;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
