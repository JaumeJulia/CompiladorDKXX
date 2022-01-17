package Semantico;

import ArbolSintactico.Tipo;
/**
 *
 * @author Felix
 */
public class Simbolo {
    
    private String id;
    private Tipo tipo;
    private int nivel;
    private TipoSub tipoSub;
    private int row;
    private int col;

    public Simbolo(String id, Tipo tipo, int nivel, TipoSub tipoSub, int row, int col) {
        this.id = id;
        this.tipo = tipo;
        this.nivel = nivel;
        this.tipoSub = tipoSub;
        this.row = row;
        this.col = col;
    }
    
    public boolean equals(Simbolo s){
        return this.id.equals(s.getId()) && this.nivel == s.getNivel();
    }
    
    public int[] getLoc(){
        int[] i = {row, col};
        return i;
    }
    
    @Override
    public String toString(){
        return  tipoSub.toString() + ": " + id + ", tipo: " + tipo + ", nivel: " + nivel;
    }

    public String getId() {
        return id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public TipoSub getTipoSub() {
        return tipoSub;
    }
}
