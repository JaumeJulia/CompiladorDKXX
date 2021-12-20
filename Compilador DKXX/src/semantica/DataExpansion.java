/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantica;

/**
 *
 * @author pujol
 */
public class DataExpansion {
    private int nivel;
    private String id;
    private Descripcion descripcion;
    
    public DataExpansion(String id, int nivel, Descripcion desc){
        this.nivel = nivel;
        this.id = id;
        this.descripcion = desc;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Descripcion getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Descripcion descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
