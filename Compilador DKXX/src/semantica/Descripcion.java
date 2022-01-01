/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantica;

import java.util.HashMap;

/**
 *
 * @author pujol
 */
public class Descripcion {
    public enum tipo_descripcion {
        dnull,
        dvar,
        dconst,
        dtipo, //dudoso
        dprog,
        dparam,
        darg
    }
    
    public enum tipo_atributo {
        INT,
        BOOLEAN
    }

    private tipo_descripcion td;
    private int idx;
    private tipo_atributo tipo;
    private String id;
    private Object valor;

    //nulls
    public Descripcion(tipo_descripcion d) {
        this.td = d;
    }
    
    //argumento o variable o procedimiento
    public Descripcion(tipo_descripcion d, int nv, String id, String tipo){
        this.idx = nv; //autoincrement
        this.tipo = tipo_atributo.valueOf(tipo);
        this.td = d;
        this.id = id;
    }
    
    //parametros
    public Descripcion(tipo_descripcion d, String id, String tipo){
        this.id = id;
        this.tipo = tipo_atributo.valueOf(tipo);
        this.td = d;
    }
    
    //constantes
    public Descripcion(tipo_descripcion d, Object valor, String tipo){
        this.valor = valor;
        this.td = d;
        this.tipo = tipo_atributo.valueOf(tipo);
    }

    public tipo_descripcion getTd() {
        return td;
    }
    
    public int getIdx(){
        return idx;
    }

    public tipo_atributo getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }

    public Object getValor() {
        return valor;
    }
    
}
