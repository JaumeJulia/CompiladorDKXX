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
public class DataDescripcion {
    
    private Descripcion desc;
    private int nivel;
    private int first;
    
    public DataDescripcion(Descripcion d, int nivel){
        this.desc = d;
        this.nivel = nivel;
    }
    
    public DataDescripcion(Descripcion d, int nivel, int first){
        this.desc = d;
        this.nivel = nivel;
        this.first = first;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }
    
    public Descripcion getDescripcion(){
        return this.desc;
    }
}
