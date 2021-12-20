/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantica;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pujol
 */
public class Descripcion {
    
    public enum tipo_descripcion{
        dnull,
        dvar,
        dconst,
        dtipus,
        dproc,
        darg_in,
        darg,
        dindex
    }
    
    private tipo_descripcion tipo_desc;
    private List<AtributoDescripcion> lista_atributo;
    
    public Descripcion(String d){
        this.tipo_desc = tipo_descripcion.valueOf(d);
        this.lista_atributo = new ArrayList<AtributoDescripcion>();
    }
    
    public void add_atributo(AtributoDescripcion atrib){
        lista_atributo.add(atrib);
    }
    
    public String getTipoDescripcion() {
        return tipo_desc.toString();
    }

    public void setTipoDescripcion(String descripcion) {
        this.tipo_desc = tipo_descripcion.valueOf(descripcion);
    }
}
