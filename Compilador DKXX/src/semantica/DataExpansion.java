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
    private int next;
    private String idCamp;
    
    public DataExpansion(String id, int nivel, Descripcion desc){
        this.nivel = nivel;
        this.id = id;
        this.descripcion = desc;
        this.next = 0;
        this.idCamp = null;
    }
    
    public DataExpansion(String id, String idCamp, int nivel, Descripcion desc, int next){
        this.nivel = nivel;
        this.id = id;
        this.idCamp = idCamp;
        this.descripcion = desc;
        this.next = next;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCamp() {
        return idCamp;
    }

    public void setIdCamp(String idCamp) {
        this.idCamp = idCamp;
    }
    
    public Descripcion getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Descripcion descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
