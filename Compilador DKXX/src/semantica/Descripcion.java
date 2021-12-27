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
        dtipus,
        dproc,
        darg_in,
        darg,
        dindex
    }

    public enum nom_atributo {
        nv,
        tipo,
        valor,
        dt,
        np,
        modo,
        nombre
    }

    public enum valor_atributo {
        integer,
        bool,
        tprog,
        tparam,
        targ
    }

    private tipo_descripcion tipo_desc;
    private HashMap<nom_atributo, valor_atributo> lista_atributo;

    public Descripcion(String d) {
        this.tipo_desc = tipo_descripcion.valueOf(d);
        this.lista_atributo = new HashMap<nom_atributo, valor_atributo>();
    }

    private void add_atributo(String nom_atrib, String tipo_atrib) {
        lista_atributo.put(nom_atributo.valueOf(nom_atrib), valor_atributo.valueOf(tipo_atrib));
    }

    public String getTipoAtributo(String nom_atrib) {
        return lista_atributo.get(nom_atributo.valueOf(nom_atrib)).toString();
    }

    public String getTipoDescripcion() {
        return tipo_desc.toString();
    }

    public void setTipoDescripcion(String descripcion) {
        this.tipo_desc = tipo_descripcion.valueOf(descripcion);
    }
}
